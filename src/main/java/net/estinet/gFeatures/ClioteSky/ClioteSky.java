package net.estinet.gFeatures.ClioteSky;

import com.google.protobuf.ByteString;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.gFeatures;

import javax.net.ssl.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ClioteSky {
    public static boolean enabled, checkTLS;

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
        gFeatures.getInstance().getLogger().info("Starting ClioteSky...");

        loadConfig();
        if (enabled) {
            clioteSky = new ClioteSky(address, Integer.parseInt(port));
            clioteSky.start();
            clioteSky.startEventLoop();
            gFeatures.getInstance().getLogger().info("[ClioteSky] enabled!");
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
            ClioteSky.checkTLS = Boolean.parseBoolean(prop.getProperty("ClioteSky.CheckTLS"));

            File f = new File("plugins/gFeatures/masterkey.key"); //get master key password
            if (f.exists()) {
                ClioteSky.password = new String(Files.readAllBytes(f.toPath()));
            } else {
                gFeatures.getInstance().getLogger().info("No masterkey.key file found! Please add the key file.");
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
            return new LinkedList<>(Arrays.asList(new String(data, "UTF-8").split(" ")));//fix warning
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
    private boolean offline = false, slowCheck = true;

    private String authToken = "";

    public ClioteSky(String host, int port) {
        //this(ManagedChannelBuilder.forAddress(host, port));
        initConnection(host, port);
    }

    private void initConnection(String host, int port) {
        if (!checkTLS) {
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            try {
                channel = NettyChannelBuilder.forAddress(host, port).useTransportSecurity().sslContext(GrpcSslContexts.forClient().sslProvider(SslProvider.JDK).trustManager(InsecureTrustManagerFactory.INSTANCE).build()).build();
            } catch (SSLException e) {
                e.printStackTrace();
            }
        } else {
            channel = NettyChannelBuilder.forAddress(host, port).useTransportSecurity().build();
        }
        blockingStub = ClioteSkyServiceGrpc.newBlockingStub(channel);
        asyncStub = ClioteSkyServiceGrpc.newStub(channel);
    }

    /*
     * Authenticates with the server and obtains a new auth token
     */

    public void start() {
        offline = false;
        ClioteSkyRPC.AuthRequest req = ClioteSkyRPC.AuthRequest.newBuilder().setUser(name).setPassword(password).setCategory(category).build();
        try {
            boolean nameTaken = blockingStub.checkNameTaken(net.estinet.gFeatures.ClioteSky.ClioteSkyRPC.String.newBuilder().setStr(name).build()).getB();
            if (nameTaken) {
                gFeatures.getInstance().getLogger().warn("ClioteSky name has already been taken. Be careful!");
            }
            authToken = blockingStub.auth(req).getToken();
            gFeatures.getInstance().getLogger().info("[ClioteSky] Authenticated!");
        } catch (StatusRuntimeException e) {
            gFeatures.getInstance().getLogger().error("[ClioteSky] RPC failed: " + e.getStatus());
        }
        channel.notifyWhenStateChanged(ConnectivityState.READY, () -> {
            gFeatures.getInstance().getLogger().warn("[ClioteSky] RPC state changed: " + channel.getState(true));
        });
    }

    /*
     * Async event loop to check if there are new messages
     */

    public void startEventLoop() {
        continueEventLoop = true;
        Runnable run = () -> {
            boolean speedup = false; //check for messages faster if a message was received
            int speedupCount = 0;

            while (continueEventLoop) {
                if (blockingStub == null) {
                    initConnection(ClioteSky.address, Integer.parseInt(ClioteSky.port));
                }
                Iterator<ClioteSkyRPC.ClioteMessage> iterator;

                try {
                    iterator = blockingStub.request(ClioteSkyRPC.Token.newBuilder().setToken(authToken).build());

                    slowCheck = false; //the server is online

                    while (iterator.hasNext()) {
                        speedup = true;
                        speedupCount = 0;
                        ClioteSkyRPC.ClioteMessage m = iterator.next();

                        Debug.print("[ClioteSky] Received " + m.getIdentifier() + " identifier from " + m.getSender() + ". Contents: " + m.getData());

                        for (ClioteHook hook : clioteHookList) {
                            //check if cliotehook has matching identifier, and call
                            if (hook.identifier.equals(m.getIdentifier()) && gFeatures.getFeature(hook.gFeatureName).isEnabled()) {
                                gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> hook.run(m.getData().toByteArray(), m.getSender())).schedule();
                            }
                        }
                    }

                } catch (StatusRuntimeException e) {

                    boolean printError = true;

                    if (e.getStatus().getDescription().equals("io exception")) {
                        if (!offline) {
                            gFeatures.getInstance().getLogger().error("[ClioteSky] Can't establish connection to server!");
                        }
                        offline = true;
                        printError = gFeatures.debug;
                    }

                    if (printError)
                        gFeatures.getInstance().getLogger().warn("[ClioteSky] RPC failed!: " + e.getStatus());
                    if (e.getStatus().getDescription().equals("invalid authentication token")) {
                        start();
                    }
                } catch (NullPointerException e) { // if the initial connection couldn't be reached on server start
                    if (gFeatures.debug) {
                        gFeatures.getInstance().getLogger().error("Can't establish connection with server. Attempting again...");
                        e.printStackTrace();
                    }
                    initConnection(ClioteSky.address, Integer.parseInt(ClioteSky.port));
                }

                if (speedupCount < 20) {
                    speedupCount++;
                } else {
                    speedup = false;
                }

                try {
                    if(slowCheck) {
                        Thread.sleep(2000);
                    }
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
            Debug.print("[ClioteSky] sent " + identifier + " to " + recipient);
            blockingStub.send(ClioteSkyRPC.ClioteSend.newBuilder().setData(ByteString.copyFrom(data)).setIdentifier(identifier).setRecipient(recipient).setToken(this.authToken).build());
        } catch (StatusRuntimeException e) {
            gFeatures.getInstance().getLogger().error("[ClioteSky] RPC failed: " + e.getStatus());
        }
    }

    /*
     * Send bytes to cliote asynchronously.
     */

    public void sendAsync(byte[] data, String identifier, String recipient) {
        gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> send(data, identifier, recipient)).schedule();
    }
}
