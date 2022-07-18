package br.com.cardoso.trace;

import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.http.HttpClientHandler;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class ClientTracingFeature implements Feature {

    final CurrentTraceContext currentTraceContext;
    final HttpClientHandler handler;

    public ClientTracingFeature(CurrentTraceContext currentTraceContext, HttpClientHandler handler) {
        this.currentTraceContext = currentTraceContext;
        this.handler = handler;
    }

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new ClientTracingFilter(currentTraceContext, handler));
        return true;
    }
}
