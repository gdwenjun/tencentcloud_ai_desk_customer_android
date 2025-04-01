package com.tencentcloud.tencentcloudcustomer.Utils;

import android.content.Context;


import com.tencent.imsdk.common.IMLog;
import com.tencentcloud.tencentcloudcustomer.TencentAiDeskCustomer;

import java.util.Collection;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.logs.LogRecordBuilder;
import io.opentelemetry.api.logs.Severity;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.data.LogRecordData;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor;
import io.opentelemetry.api.logs.Logger;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

public class TencentAiDeskCustomerReport {
    private static Tracer tracer;
    private static Logger logger;

    private static String TAG = "Customer";
    private static SdkLoggerProvider sdkLoggerProvider;
    private static SdkTracerProvider sdkTracerProvider;
    private static String userID;
    private static int sdkAppID;
    final static String Tenantid = "tccc";
//    final static String ReportUrl = "http://10.21.24.28:9999";
    final static String ReportUrl = "https://otlp.tccc.qcloud.com";
//    final static String ReportUrl = "http://otlp.tpstelemetry.woa.com";
    final static String ReportUrlOversea = "https://otlp.connect.tencentcloud.com";

    private static Resource tcccResource = Resource.builder()
            .put("service.name","android-Customer")
            .put("tps.tenant.id",Tenantid)
            .put("client.environment","Native")
            .put("client.module","CustomerClient")
            .put("client.platform","android")
            .put("client.version", TencentAiDeskCustomer._version)
            .build();

    public static  void init(Context context, int mSdkAppId, String mUserId){
        boolean isOversea = false;
        if ((mSdkAppId >= 20000000 && mSdkAppId < 30000000) || (mSdkAppId >= 1720000000 && 1730000000 > mSdkAppId)) {
            isOversea = true;
        }
        if (isOversea && sdkAppID != mSdkAppId) {
            initOverseaTracerNlog();
        }
        else if (tracer == null) {
            initTracerNlog();
        }
        userID = mUserId;
        sdkAppID = mSdkAppId;
    }

    private static void initOverseaTracerNlog() {
        innerTracerNlogByUrl(ReportUrlOversea);
    }

    private static void innerTracerNlogByUrl(String url) {
        tcccResource = tcccResource.merge(Resource.builder()
                .put("client.sdkAppId",sdkAppID)
                .put("client.userId",userID)
                .build());

        Resource otelResource = Resource.getDefault().merge(tcccResource);
        sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                .addSpanProcessor(
                        BatchSpanProcessor
                                .builder(
                                        OtlpHttpSpanExporter.builder()
//                                .             setProxy(ProxyOptions.create(InetSocketAddress.createUnresolved("10.21.24.28", 8899)))
                                                .setEndpoint(url+"/v1/traces")
                                                .addHeader("X-Tps-Tenantid",Tenantid)
                                                .build()
                                )
                                .build()
                )
                .setResource(otelResource)
                .build();

        sdkLoggerProvider = SdkLoggerProvider.builder()
                .addLogRecordProcessor(SimpleLogRecordProcessor.create(new LogRecordExporter() {
                    @Override
                    public CompletableResultCode export(Collection<LogRecordData> collection) {
                        // collection
                        return CompletableResultCode.ofSuccess();
                    }

                    @Override
                    public CompletableResultCode flush() {
                        return CompletableResultCode.ofSuccess();
                    }

                    @Override
                    public CompletableResultCode shutdown() {
                        return CompletableResultCode.ofSuccess();
                    }
                }))
                .addLogRecordProcessor(
                        BatchLogRecordProcessor
                                .builder(
                                        OtlpHttpLogRecordExporter
                                                .builder()
                                                //.setProxyOptions(ProxyOptions.create(InetSocketAddress.createUnresolved("10.21.24.28", 8899)))
                                                .setEndpoint(url+"/v1/logs")
                                                .addHeader("X-Tps-Tenantid",Tenantid)
                                                .build()
                                )
                                .build()
                )
                .setResource(otelResource)
                .build();

        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setLoggerProvider(sdkLoggerProvider)
                .setPropagators(
                        ContextPropagators
                                .create(
                                        W3CTraceContextPropagator.getInstance()
                                )
                )
                .buildAndRegisterGlobal();

        tracer = openTelemetry.getTracer("customer-tracer", "1.0.0");
        logger = sdkLoggerProvider.loggerBuilder("customer-log").build();
    }

    private static void initTracerNlog() {
        innerTracerNlogByUrl(ReportUrl);
    }

    public static void reportInfo(String content){
        innerReport(content, null, Severity.INFO);
    }

    public static void reportInfo(String content, String detail){
        innerReport(content, detail, Severity.INFO);
    }

    public static void reportWarn(String content,String detail){
        innerReport(content,detail, Severity.WARN);
    }

    public static void reportError(String content){
        innerReport(content, null,Severity.ERROR);
    }

    public static void reportError(String content,String detail){
        innerReport(content, detail,Severity.ERROR);
    }

    private static void innerReport(String content,String detail,Severity severity) {
        if (tracer == null) {
            initTracerNlog();
        }
        String line = content + (detail!=null? ", "+detail:"");
        LogRecordBuilder logRecordBuilder = logger.logRecordBuilder();
        logRecordBuilder.setSeverity(severity);
        logRecordBuilder.setBody(line);
        if (userID!= null && !userID.isEmpty()) {
            logRecordBuilder.setAllAttributes(Attributes
                    .builder()
                    .put("client.sdkAppId",sdkAppID)
                    .put("client.userId",userID)
                    .build());
        }
        logRecordBuilder.emit();
        if (severity == Severity.ERROR) {
            IMLog.e(TAG, line);
        } else {
            IMLog.v(TAG, line);
        }
        innerReportSpan(content,severity);
    }

    private static void innerReportSpan(String content,Severity severity) {
        if (tracer == null) {
            initTracerNlog();
        }
        Span span = tracer.spanBuilder(content).startSpan();
        span.setStatus(StatusCode.OK);
        if (severity == Severity.ERROR) {
            span.setStatus(StatusCode.ERROR);
        }
        span.setAllAttributes(Attributes
                .builder()
                .put("client.sdkAppId",sdkAppID)
                .put("client.userId",userID)
                .build());
        IMLog.v(TAG, content);
        span.end();
    }

    public static void reportBySpan(String content) {
        innerReportSpan(content, Severity.INFO);
    }
}