package net.estinet.gFeatures.ClioteSky;

import com.google.protobuf.ByteString;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import net.estinet.gFeatures.FeatureState;
import net.estinet.gFeatures.gFeatures;
import net.md_5.bungee.api.ProxyServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ClioteSky {

    public static boolean enabled, tls, checktls;
    static String name, password, address, port, category;

    private static ClioteSky clioteSky;

    public static ClioteSky getInstance() {
        return clioteSky;
    }

    /*
     * Called on enable of plugin.
     */

    public static void initClioteSky() {
        ProxyServer.getInstance().getLogger().info("Starting ClioteSky...");
        loadConfig();
        clioteSky = new ClioteSky(address, Integer.parseInt(port));
        clioteSky.start();
        clioteSky.startEventLoop();
        ProxyServer.getInstance().getLogger().info("ClioteSky has successfully been enabled!");
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
            ClioteSky.password = prop.getProperty("ClioteSky.Password");
            ClioteSky.port = prop.getProperty("ClioteSky.Port");

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
     * ClioteSky Object
     */

    public ManagedChannel channel;
    private ClioteSkyServiceGrpc.ClioteSkyServiceBlockingStub blockingStub;
    private ClioteSkyServiceGrpc.ClioteSkyServiceStub asyncStub;
    public boolean continueEventLoop = true;
    private String authToken;
    private List<ClioteHook> clioteHookList = new ArrayList<>();

    public ClioteSky(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public ClioteSky(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = ClioteSkyServiceGrpc.newBlockingStub(channel);
        asyncStub = ClioteSkyServiceGrpc.newStub(channel);
    }

    public void start() {
        ClioteSkyRPC.AuthRequest req = ClioteSkyRPC.AuthRequest.newBuilder().setUser(name).setPassword(password).build();
        try {
            authToken = blockingStub.auth(req).getToken();
        } catch (StatusRuntimeException e) {
            ProxyServer.getInstance().getLogger().severe("RPC failed: " + e.getStatus());
        }
        channel.notifyWhenStateChanged(ConnectivityState.READY, () -> {
            ProxyServer.getInstance().getLogger().warning("RPC state changed: " + channel.getState(true));
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
                Iterator<ClioteSkyRPC.ClioteMessage> iterator = null;
                try {
                    iterator = blockingStub.request(ClioteSkyRPC.Token.newBuilder().setToken(authToken).build());
                    while (iterator.hasNext()) {
                        speedup = true;
                        speedupCount = 0;
                        ClioteSkyRPC.ClioteMessage m = iterator.next();
                        for (ClioteHook hook : clioteHookList) {
                            //check if cliotehook has matching identifier, and call
                            if (hook.identifier.equals(m.getIdentifier()) && gFeatures.getFeature(hook.gFeatureName).getState() == FeatureState.ENABLE) {
                                ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> hook.run(m.getData().toByteArray(), m.getSender()));
                            }
                        }
                    }
                } catch (StatusRuntimeException e) {
                    ProxyServer.getInstance().getLogger().severe("RPC failed: " + e.getStatus());
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

    public void addHook(ClioteHook hook) {
        clioteHookList.add(hook);
    }
}
