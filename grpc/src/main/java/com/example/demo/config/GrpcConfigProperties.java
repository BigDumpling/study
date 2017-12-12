package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "grpc")
public class GrpcConfigProperties {
    private String address;
    private Integer port;
}
