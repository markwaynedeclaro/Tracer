package main;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tracer {

    public List<String> readTraceFile(File file) {
        List<String> traces = new ArrayList<String>();

        BufferedReader br;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {

                if(line.trim().equals("{")) {
                    sb = new StringBuilder();
                } else if(line.trim().contains("}")) {
                    traces.add(sb.toString());
                } else {
                    sb.append(line.trim());
                }

            }
            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        return traces;
    }

    public Trace parseTrace(String line) {

        Trace trace = new Trace();

        Matcher matchTime = Pattern.compile("\"time\": \"([^\"]+)").matcher(line);
        Matcher matchApp = Pattern.compile("\"app\": \"([^\"]+)").matcher(line);
        Matcher matchMsg = Pattern.compile("\"msg\": \"([^\"]+)").matcher(line);
        Matcher matchComponent = Pattern.compile("\"component\": \"([^\"]+)").matcher(line);
        Matcher matchSpanId = Pattern.compile("\"span_id\": \"([^\"]+)").matcher(line);
        Matcher matchParentSpanId = Pattern.compile("\"parent_span_id\": \"([^\"]+)").matcher(line);
        Matcher matchLevel = Pattern.compile("\"level\": \"([^\"]+)").matcher(line);
        Matcher matchEnv = Pattern.compile("\"env\": \"([^\"]+)").matcher(line);
        Matcher matchTraceId = Pattern.compile("\"trace_id\": \"([^\"]+)").matcher(line);

        trace.setTime(matchTime.find() ? matchTime.group(1) : "");
        trace.setApp(matchApp.find() ? matchApp.group(1) : "");
        trace.setMsg(matchMsg.find() ? matchMsg.group(1) : "");
        trace.setComponent(matchComponent.find() ? matchComponent.group(1) : "");
        trace.setSpan_id(matchSpanId.find() ? matchSpanId.group(1) : "");
        trace.setParentSpanId(matchParentSpanId.find() ? matchParentSpanId.group(1) : "");
        trace.setLevel(matchLevel.find() ? matchLevel.group(1) : "");
        trace.setEnv(matchEnv.find() ? matchEnv.group(1) : "");
        trace.setTraceId(matchTraceId.find() ? matchTraceId.group(1) : "");
        trace.setSubTraces(new ArrayList<Trace>());

        return trace;
    }

    public List<Trace> convertTraces(List<Trace> traces, Trace trace) {
        if(trace.getParentSpanId().length() > 0) {
            for (int x=traces.size()-1; x>=0; x--) {
                if (traces.get(x).getSpan_id().equals(trace.getParentSpanId())) {
                    traces.get(x).getSubTraces().add(trace);
                    break;
                } else {
                    if (traces.get(x).getSubTraces().size() > 0) {
                        traces.get(x).setSubTraces(convertTraces(traces.get(x).getSubTraces(), trace));
                    }
                }
            }
        } else {
            traces.add(trace);
        }
        return traces;
    }


    public void printTrace(List<Trace> traces, int level) {
        StringBuilder sb;
        for (Trace trace : traces) {
            sb = new StringBuilder();
            for (int i=0; i<level-1; i++) {
                sb.append("\t");
            }
            sb.append("- ").append(trace.getTime()+" ").append(trace.getApp()+" ").append(trace.getComponent()+" ").append(trace.getMsg());
            System.out.println(sb.toString());
            if (trace.getSubTraces().size() > 0)
                printTrace(trace.getSubTraces(), level+1);
        }
    }


}
