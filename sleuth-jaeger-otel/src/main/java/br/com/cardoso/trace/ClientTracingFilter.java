package br.com.cardoso.trace;

import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.http.HttpClientHandler;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

@Priority(Priorities.HEADER_DECORATOR)
public class ClientTracingFilter implements ClientRequestFilter, ClientResponseFilter {

    public static final String SPAN_PROPERTY_NAME = "jax-rs-client.span";
    public static final String SPAN_ERROR = "jax-rs-client.span.error";

    final CurrentTraceContext currentTraceContext;
    final HttpClientHandler handler;

    public ClientTracingFilter(CurrentTraceContext currentTraceContext, HttpClientHandler handler) {
        this.currentTraceContext = currentTraceContext;
        this.handler = handler;
    }

    @Override
    public void filter(ClientRequestContext requestContext) {
        JerseyHttpRequestWrapper request = new JerseyHttpRequestWrapper(requestContext);
        Span span = handler.handleSend(request);
        CurrentTraceContext.Scope scope = null;
        try {
            scope = currentTraceContext.newScope(span.context());
        } catch (Throwable e) {
            requestContext.setProperty(SPAN_ERROR, e);
            throw e;
        } finally {
            if (scope != null) {
                scope.close();
            }
            requestContext.setProperty(SPAN_PROPERTY_NAME, span);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) {
        JerseyHttpRequestWrapper request = new JerseyHttpRequestWrapper(requestContext);
        Object errorObject = requestContext.getProperty(SPAN_ERROR);
        Object spanObject = requestContext.getProperty(SPAN_PROPERTY_NAME);
        Throwable error = null;
        if (errorObject instanceof Throwable) {
            error = (Throwable) errorObject;
        }
        if (spanObject instanceof Span) {
            handler.handleReceive(new JerseyHttpResponseWrapper(request, responseContext, error), (Span) spanObject);
        }
    }
}
