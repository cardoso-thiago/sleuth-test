package br.com.cardoso.controller;

import br.com.cardoso.service.SleuthService;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SleuthController {

    private final SleuthService sleuthService;

    public SleuthController(SleuthService sleuthService) {
        this.sleuthService = sleuthService;
    }

    @GetMapping("/hello")
    public String hello() {
        String correlationId = UUID .randomUUID().toString();
        System.out.println("Gerou o correlationId " + correlationId);
        MDC.put("correlationId", correlationId);
        return "Hello " + sleuthService.hello();
    }

    @GetMapping("/helloJersey")
    public String helloJersey() {
        String correlationId = UUID.randomUUID().toString();
        System.out.println("Gerou o correlationId " + correlationId);
        MDC.put("correlationId", correlationId);
        return "Hello " + sleuthService.helloJersey();
    }

    @GetMapping("/helloJerseyAgent")
    public String helloJerseyAgent() {
        String correlationId = UUID.randomUUID().toString();
        System.out.println("Gerou o correlationId " + correlationId);
        MDC.put("correlationId", correlationId);
        return "Hello " + sleuthService.helloJerseyWithoutSpringTracing();
    }

    @GetMapping("/world")
    public String world() {
        return "World";
    }
}
