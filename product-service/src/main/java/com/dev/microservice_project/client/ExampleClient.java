package com.dev.microservice_project.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "exampleClient", url = "https://jsonplaceholder.typicode.com")
public interface ExampleClient {
    @GetMapping("/posts")
    List<Map<String, Object>> getPosts();
}