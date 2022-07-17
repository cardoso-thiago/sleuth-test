package br.com.cardoso.configuration;

import io.opentelemetry.sdk.trace.samplers.Sampler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.http.HttpServerHandler;
import org.springframework.cloud.sleuth.instrument.web.servlet.TracingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SleuthConfiguration {

    //Configura o tracing para o RestTemplate através da classe TracingRestTemplateCustomizer
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.alwaysOn();
    }
}