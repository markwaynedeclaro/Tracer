package main;

import java.util.List;

public class Trace {

    String time;
    String app;
    String msg;
    String span_id;
    String parentSpanId;
    String component;
    String level;
    String env;
    String traceId;
    List<Trace> subTraces;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSpan_id() {
        return span_id;
    }

    public void setSpan_id(String span_id) {
        this.span_id = span_id;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public List<Trace> getSubTraces() {
        return subTraces;
    }

    public void setSubTraces(List<Trace> subTraces) {
        this.subTraces = subTraces;
    }
}
