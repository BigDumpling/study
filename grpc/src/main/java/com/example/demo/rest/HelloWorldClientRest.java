package com.example.demo.rest;

import com.example.demo.rpc.GreeterGrpc;
import com.example.demo.rpc.HelloProto;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/rpc/hello")
@RestController
public class HelloWorldClientRest {

    @Autowired
    @Qualifier("helloWorldChannel")
    private ManagedChannel helloWorldChannel;

    @GetMapping("/sayHello")
    public String sayHello() {
        log.info("into /api/rpc/hello/sayHello");
        HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
        builder.setName("ligq");
        builder.setAge(23);
        builder.setSex(1);

        HelloProto.HelloRequest request = builder.build();
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(helloWorldChannel);
        HelloProto.HelloReply reply = blockingStub.sayHello(request);
        log.info("grpc sayHello reply = {}", reply.getMsg());
        return reply.getMsg();
    }
}
