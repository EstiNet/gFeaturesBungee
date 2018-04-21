package net.estinet.gFeatures.ClioteSky;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import net.estinet.gFeatures.gFeatures;
import net.md_5.bungee.api.ProxyServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClioteSky {

    public static boolean enabled, tls, checktls;
    static String name, password, address, port, category;

    /*
     * Static methods
     */

    public static void initClioteSky() {
        loadConfig();
        gFeatures.clioteSky = new ClioteSky(address, Integer.parseInt(port));
        gFeatures.getClioteSky().start();
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
     * ClioteSky Object
     */

    private ManagedChannel channel;
    private ClioteSkyServiceGrpc.ClioteSkyServiceBlockingStub blockingStub;
    private ClioteSkyServiceGrpc.ClioteSkyServiceStub asyncStub;
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
    }
    public void send(byte[] data, String identifier, String recipient) {
        try {
            blockingStub.send(ClioteSkyRPC.ClioteSend.newBuilder().setData(ByteString.copyFrom(data)).setIdentifier(identifier).setRecipient(recipient).setToken(this.authToken).build());
        } catch(StatusRuntimeException e) {
            ProxyServer.getInstance().getLogger().severe("RPC failed: " + e.getStatus());
        }
    }
    public void sendAsync(byte[] data, String identifier, String recipient) {
        ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> {
            send(data, identifier, recipient);
        });
    }
    public void addHook(ClioteHook hook) {
        clioteHookList.add(hook);
    }
}
