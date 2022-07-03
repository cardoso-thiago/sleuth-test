package br.com.cardoso.configuration;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Configuration
public class SleuthOtelConfiguration {

    @Bean
    Resource otelResource(Environment env, ObjectProvider<List<Supplier<Resource>>> resourceProviders) {
        //Customizando o nome do service name
        String applicationName = "application-name-test";
        Resource resource = defaultResource(applicationName);
        List<Supplier<Resource>> resourceCustomizers = resourceProviders.getIfAvailable(ArrayList::new);
        for (Supplier<Resource> provider : resourceCustomizers) {
            resource = resource.merge(provider.get());
        }
        return resource;
    }

    private Resource defaultResource(String applicationName) {
        return Resource.getDefault()
                .merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, applicationName)));
    }
}
