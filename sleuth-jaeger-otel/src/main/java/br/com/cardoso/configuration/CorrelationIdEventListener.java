package br.com.cardoso.configuration;

import io.opentelemetry.api.trace.Span;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.SpanCustomizer;
import org.springframework.cloud.sleuth.otel.bridge.OtelSpanCustomizer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class CorrelationIdEventListener implements ApplicationListener<ApplicationEvent> {

    private final SpanCustomizer spanCustomizer;

    public CorrelationIdEventListener(SpanCustomizer spanCustomizer) {
        this.spanCustomizer = spanCustomizer;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(Span.current().getSpanContext().getTraceId());
        spanCustomizer.tag("correlationId", MDC.get("correlationId"));
    }
}