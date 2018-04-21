package net.estinet.gFeatures.ClioteSky;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClioteSky {

    ManagedChannel channel;

    public ClioteSky(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }
    public ClioteSky(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = ClioteSkyRPC.newBlockingStub(channel);
        asyncStub = ClioteSkyRPC.newStub(channel);
        
    }
    public static void start() {
        blockingStub = RouteGuideGrpc.newBlockingStub(channel);
        asyncStub = RouteGuideGrpc.newStub(channel);
    }
}
