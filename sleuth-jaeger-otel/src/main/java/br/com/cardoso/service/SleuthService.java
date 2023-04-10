package br.com.cardoso.service;

import br.com.cardoso.trace.ClientTracingFeature;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.http.HttpClientHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Service
public class SleuthService {

    private final RestTemplate restTemplate;
    final CurrentTraceContext currentTraceContext;
    final HttpClientHandler handler;

    public SleuthService(RestTemplate restTemplate, CurrentTraceContext currentTraceContext, HttpClientHandler handler) {
        this.restTemplate = restTemplate;
        this.currentTraceContext = currentTraceContext;
        this.handler = handler;
    }

    public String hello() {
        String path = "http://localhost:8080/world";
        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);
        return response.getBody();
    }

    public String helloJersey() {
        String path = "http://localhost:8080/world";
        Client client = ClientBuilder.newBuilder().build();
        WebTarget webTarget = client.register(new ClientTracingFeature(currentTraceContext, handler)).target(path);
        Response response = webTarget.request().get();
        return response.readEntity(String.class);
    }

    public String helloJerseyWithoutSpringTracing() {
        String path = "http://localhost:8080/world";
        Client client = ClientBuilder.newBuilder().build();
        WebTarget webTarget = client.target(path);
        Response response = webTarget.request().get();
        return response.readEntity(String.class);
    }
}
