package com.example.demo.service;

import com.example.demo.rpc.GreeterGrpc;
import com.example.demo.rpc.HelloProto;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HelloWorldServer {

    private Server server;

    private void start() throws IOException {
        int port = 9002;
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();
        log.info("grpc service start, listening on port: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                System.out.println("shutDown the server start");
                HelloWorldServer.this.shutDown();
                System.out.println("shutDown the server end");
            }
        });
    }

    public void shutDown() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloReply> responseObserver) {
            HelloProto.HelloReply reply = HelloProto.HelloReply.newBuilder().setMsg("hello " + request.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
