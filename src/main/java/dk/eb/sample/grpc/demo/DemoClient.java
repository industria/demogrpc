package dk.eb.sample.grpc.demo;

import dk.eb.sample.grpc.demo.DemoThingsGrpc.DemoThingsBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class DemoClient {

    private final DemoThingsBlockingStub blockingStub;

    public DemoClient(Channel channel) {
        this.blockingStub = DemoThingsGrpc.newBlockingStub(channel);
    }

    public Thing thing(int id) {
        Some request = Some.newBuilder().setId(id).build();
        return blockingStub.getSomeThing(request);
    }
    
    public static void main(String[] args) throws Exception {
        String target = System.getProperty("hostport", "localhost:8777");
        System.out.println("Target: " + target);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            DemoClient client = new DemoClient(channel);

            Thing thing = client.thing(0);
            
            System.out.println("Thing: " + thing.getId() + " with value: " + thing.getValue());
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

}
