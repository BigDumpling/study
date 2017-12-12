package com.example.demo.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = GrpcConfigProperties.class)
public class GrpcConfig {

    @Autowired
    private GrpcConfigProperties grpcConfigProperties;

    @Bean(name = "helloWorldChannel")
    public ManagedChannel helloWorldChannel(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcConfigProperties.getAddress(), grpcConfigProperties.getPort()).usePlaintext(true).build();
        return channel;
    }
}
