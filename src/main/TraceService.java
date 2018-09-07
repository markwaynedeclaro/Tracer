package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TraceService {


    public static void main(String[] args) {

        TraceService traceService = new TraceService();
        traceService.trace();

    }


    public void trace() {

        try {
            Tracer tracer = new Tracer();
            File file = new File("C:\\log-data.json");
            List<String> rawTraces = tracer.readTraceFile(file);
            rawTraces.sort(String::compareToIgnoreCase);

            List<Trace> traces = new ArrayList<Trace>();
            Trace trace;

            for (int i=0; i<rawTraces.size(); i++) {
                trace = tracer.parseTrace(rawTraces.get(i));
                traces = tracer.convertTraces(traces, trace);
            }

            tracer.printTrace(traces, 1);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }


}


