package com.skcc.filter;

import com.skcc.utils.Utils;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TransactionFilter implements Filter {

    private final OpenTelemetry openTelemetry;
    // private long incomingMessageCount;

    TextMapGetter<HttpServletRequest> getter = new TextMapGetter<>() {
        @Override
        public Iterable<String> keys(HttpServletRequest httpServletRequest) {
            return Utils.iterable(httpServletRequest.getHeaderNames());
        }

        @Override
        public String get(HttpServletRequest httpServletRequest, String s) {
            assert httpServletRequest != null;
            return httpServletRequest.getHeader(s);
        }
    };

    @Autowired
    public TransactionFilter(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // incomingMessageCount++;
        HttpServletRequest req = (HttpServletRequest) request;
        Context extractedContext = openTelemetry.getPropagators().getTextMapPropagator()
                .extract(Context.current(), req, getter);
        try (Scope scope = extractedContext.makeCurrent()) {
            chain.doFilter(request, response);
        }
    }
}