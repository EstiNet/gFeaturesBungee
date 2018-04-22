package net.estinet.gFeatures.ClioteSky;

import com.google.protobuf.ByteString;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.FeatureState;
import net.estinet.gFeatures.gFeatures;
import net.md_5.bungee.api.ProxyServer;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ClioteSky {
    public static boolean enabled, tls, checktls, reconnector = false;

    private static String name, password, address, port, category;

    private static List<ClioteHook> clioteHookList = new ArrayList<>();

    private static ClioteSky clioteSky;

    public static ClioteSky getInstance() {
        return clioteSky;
    }

    public static void addHook(ClioteHook hook) {
        clioteHookList.add(hook);
    }

    /*
     * Called on enable of plugin.
     */

    public static void initClioteSky() {
        ProxyServer.getInstance().getLogger().info("Starting ClioteSky...");
        loadConfig();
        if (enabled) {
            clioteSky = new ClioteSky(address, Integer.parseInt(port));
            clioteSky.start();
            clioteSky.startEventLoop();
            ProxyServer.getInstance().getLogger().info("ClioteSky has been enabled!");
        }
    }

    private static void loadConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("plugins/gFeatures/Config.yml");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            ClioteSky.name = prop.getProperty("ClioteSky.Name");
            ClioteSky.category = prop.getProperty("ClioteSky.Category");
            ClioteSky.address = prop.getProperty("ClioteSky.Address");
            ClioteSky.enabled = Boolean.parseBoolean(prop.getProperty("ClioteSky.Enable"));
            ClioteSky.port = prop.getProperty("ClioteSky.Port");

            File f = new File("plugins/gFeatures/masterkey.key");
            if (f.exists()) {
                ClioteSky.password = new String(Files.readAllBytes(f.toPath()));
            } else {
                ProxyServer.getInstance().getLogger().info("No masterkey.key file found! Please add the key file.");
                f.createNewFile();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * String helpers
     */

    public static List<String> parseBytesToStringList(byte[] data) {
        try {
            return Arrays.asList(new String(data, "UTF-8").split(" "));//fix warning
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] stringToBytes(String str) {
        //lol
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Called once when the grpc server goes offline
     */

    public static void startReconnector() {
        try {
            while (!clioteSky.continueEventLoop) {
                Thread.sleep(3000);
                clioteSky = new ClioteSky(address, Integer.parseInt(port));
                clioteSky.start();
                clioteSky.startEventLoop();
                Thread.sleep(1000);
            }
            reconnector = false;
            ProxyServer.getInstance().getLogger().info("[ClioteSky] Connection re-established!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /*
     * ClioteSky Object
     */

    public ManagedChannel channel;
    private ClioteSkyServiceGrpc.ClioteSkyServiceBlockingStub blockingStub;
    private ClioteSkyServiceGrpc.ClioteSkyServiceStub asyncStub;
    public boolean continueEventLoop = true;

    private String authToken;

    public ClioteSky(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public ClioteSky(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = ClioteSkyServiceGrpc.newBlockingStub(channel);
        asyncStub = ClioteSkyServiceGrpc.newStub(channel);
    }

    public void start() {
        ClioteSkyRPC.AuthRequest req = ClioteSkyRPC.AuthRequest.newBuilder().setUser(name).setPassword(password).setCategory(category).build();
        try {
            boolean nameTaken = blockingStub.checkNameTaken(net.estinet.gFeatures.ClioteSky.ClioteSkyRPC.String.newBuilder().setStr(name).build()).getB();
            if (nameTaken) {
                ProxyServer.getInstance().getLogger().warning("ClioteSky name has already been taken. Be careful!");
            }
            authToken = blockingStub.auth(req).getToken();
        } catch (StatusRuntimeException e) {
            ProxyServer.getInstance().getLogger().severe("RPC failed: " + e.getStatus());
        }
        channel.notifyWhenStateChanged(ConnectivityState.READY, () -> {
            ProxyServer.getInstance().getLogger().warning("RPC state changed: " + channel.getState(true));
            if (channel.getState(true) != ConnectivityState.CONNECTING && channel.getState(true) != ConnectivityState.READY) {
                continueEventLoop = false;
                if (!reconnector) {
                    reconnector = true;
                    //new Thread(ClioteSky::startReconnector).start();
                }
            }
        });
    }

    /*
     * async event loop to check if there are new messages
     */

    public void startEventLoop() {
        continueEventLoop = true;
        Runnable run = () -> {
            boolean speedup = false; //check for messages faster if a message was received
            int speedupCount = 0;

            while (continueEventLoop) {
                Iterator<ClioteSkyRPC.ClioteMessage> iterator;
                try {
                    iterator = blockingStub.request(ClioteSkyRPC.Token.newBuilder().setToken(authToken).build());

                    while (iterator.hasNext()) {
                        speedup = true;
                        speedupCount = 0;
                        ClioteSkyRPC.ClioteMessage m = iterator.next();

                        Debug.print("[ClioteSky] Received " + m.getIdentifier() + " identifier from "  + m.getSender() + ". Contents: " + m.getData());

                        for (ClioteHook hook : clioteHookList) {
                            //check if cliotehook has matching identifier, and call
                            if (hook.identifier.equals(m.getIdentifier()) && gFeatures.getFeature(hook.gFeatureName).getState() == FeatureState.ENABLE) {
                                ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> hook.run(m.getData().toByteArray(), m.getSender()));
                            }
                        }
                    }

                } catch (StatusRuntimeException e) {
                    ProxyServer.getInstance().getLogger().severe("RPC failed: " + e.getStatus());
                    if (e.getStatus().getDescription().equals("invalid authentication token")) {
                        start();
                    }
                }

                if (speedupCount < 20) {
                    speedupCount++;
                } else {
                    speedup = false;
                }

                try {
                    if (speedup) {
                        Thread.sleep(200);
                    } else {
                        Thread.sleep(800);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(run).start();
    }

    /*
     * Send bytes to cliote synchronously.
     */

    public void send(byte[] data, String identifier, String recipient) {
        try {
            blockingStub.send(ClioteSkyRPC.ClioteSend.newBuilder().setData(ByteString.copyFrom(data)).setIdentifier(identifier).setRecipient(recipient).setToken(this.authToken).build());
        } catch (StatusRuntimeException e) {
            ProxyServer.getInstance().getLogger().severe("RPC failed: " + e.getStatus());
        }
    }

    /*
     * Send bytes to cliote asynchronously.
     */

    public void sendAsync(byte[] data, String identifier, String recipient) {
        ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> send(data, identifier, recipient));
    }
}
