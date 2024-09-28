package com.saloed.cloud.cloudapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@RestController
public class HelloController {

    public static final String CLOUD_APP_B = "cloud-app-b";

    @Value("${message:Hello, default!}")
    private String message;

    @GetMapping("/hello")
    public String sayHello() {
        return message;
    }

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public HelloController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/fetch-data")
    public String fetchDataFromServiceB() {
//        String serviceUrl = discoveryClient.getInstances(CLOUD_APP_B)
//            .stream()
//            .findFirst()
//            .map(si -> si.getUri().toString())
//            .orElseThrow(() -> new RuntimeException("Service % not found!".formatted(CLOUD_APP_B)));

        String data = restTemplate.getForObject("http://" + CLOUD_APP_B + "/data", String.class);
        return "Received from " + CLOUD_APP_B + ": " + data;
    }
}
