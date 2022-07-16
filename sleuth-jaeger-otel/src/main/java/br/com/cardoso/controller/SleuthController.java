package br.com.cardoso.controller;

import br.com.cardoso.service.SleuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SleuthController {

    private final SleuthService sleuthService;

    public SleuthController(SleuthService sleuthService) {
        this.sleuthService = sleuthService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello " + sleuthService.hello();
    }

    @GetMapping("/helloJersey")
    public String helloJersey() {
        return "Hello " + sleuthService.helloJersey();
    }

    @GetMapping("/world")
    public String world() {
        return "World";
    }
}
