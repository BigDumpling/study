package com.example.demo.rest;

import com.example.demo.rpc.GreeterGrpc;
import com.example.demo.rpc.HelloProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HelloWorldClient {

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build());
    }

    HelloWorldClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutDown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.MILLISECONDS);
    }

    public void greet(String name) {
        log.info("Will try to greet " + name + " ...");
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder().setName(name).build();
        HelloProto.HelloReply reply;
        try {
            reply = blockingStub.sayHello(request);
        } catch (Exception e) {
            log.error("grpc error:{}", e.getMessage());
            return;
        }

        log.info("grpc end");
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient("localhost", 8081);
        try {
            String name = "ligq";
            client.greet(name);
        } finally {
            client.shutDown();
        }
    }
}
