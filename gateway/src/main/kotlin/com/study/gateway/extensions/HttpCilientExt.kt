package com.study.gateway.extensions

import io.ktor.client.request.*
import org.springframework.cloud.sleuth.TraceContext

fun HttpRequestBuilder.addTracing(context : TraceContext?) {
    header("X-B3-TraceId", context?.traceId())
    header("X-B3-ParentSpanId", context?.parentId())
    header("X-B3-SpanId", context?.spanId())
    header("X-B3-Sampled", context?.sampled())
}