package com.skcc.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

@Configuration
public class OtelConfig {

    @Value("${otel.service.name}")
    String otelServiceName;
    @Value("${otel.traces.endpoint}")
    String otelTracesEndpoint;
    @Value("${otel.jaeger.endpoint}")
    String otelJaegerEndpoint;

    // @Bean
    // public OpenTelemetry openTelemetry() {
    // Resource resource = Resource.getDefault()
    // .merge(Resource.create(
    // Attributes.of(ResourceAttributes.SERVICE_NAME, otelServiceName)));

    // // SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
    // // .addSpanProcessor(BatchSpanProcessor
    // // .builder(OtlpGrpcSpanExporter.builder().setEndpoint(otelTracesEndpoint)
    // // .build())
    // // .build())
    // // .setResource(resource)
    // // .build();

    // SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
    // .addSpanProcessor(SimpleSpanProcessor.create(getJaegerExporter()))
    // .setResource(resource)
    // .build();

    // // SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
    // //
    // .registerMetricReader(PeriodicMetricReader.builder(OtlpGrpcMetricExporter.builder().build()).build())
    // // .setResource(resource)
    // // .build();

    // return OpenTelemetrySdk.builder()
    // .setTracerProvider(sdkTracerProvider)
    // // .setMeterProvider(sdkMeterProvider)
    // .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
    // .buildAndRegisterGlobal();
    // }

    @Bean
    public OpenTelemetrySdk getOpenTelemetry() {
        Resource serviceNameResource = Resource
                .create(Attributes.of(ResourceAttributes.SERVICE_NAME, otelServiceName));
        return OpenTelemetrySdk.builder()
                .setTracerProvider(
                        SdkTracerProvider.builder()
                                .addSpanProcessor(SimpleSpanProcessor
                                        .create(getJaegerExporter()))
                                .setResource(Resource.getDefault()
                                        .merge(serviceNameResource))
                                .build())
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal();
    }

    @Bean
    public JaegerGrpcSpanExporter getJaegerExporter() {
        return JaegerGrpcSpanExporter.builder()
                .setEndpoint(otelJaegerEndpoint)
                .setTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public Tracer getTracer() {
        return getOpenTelemetry().getTracer(otelServiceName);
    }
}
