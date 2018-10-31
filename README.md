
# Tracer <>

A program that will ingest log records to determine the overall health of the system. This test is based on OpenTracing. Refer to the OpenTracing docs (http://opentracing.io/documentation) for an overview of tracing.

-  This program reads in the transaction log records named log-data.json file to determine the number of failed transactions and the causes of the failure. 
-  A transaction can span multiple systems; a single trace_id is used for the entire transaction, while a new span_id is used for each logical operation that is performed. 
-  The log data is not in order as it is aggregated from multiple sources thus the program sorts it. 
-  Output is printed to stdout (standard output) 
-  Output the failure cause trace in the following format: - <time> <app> <component> <msg>. Child spans is nested.
-  Only standard libraries of JDK 8 are used.  
  
  
#### Example Output
  - 2018-08-26T17:35:39+10:00 svc-app-1 auth.user.Login() starting login for bd5f12d5-130a-4ed3-bf21-0eff55c1a2af    
    - 2018-08-27T15:55:33+10:00 svc-app-2 auth.user.AuthCheck() checking auth creds for bd5f12d5-130a-4ed3-bf21-0eff55c1a2af    
    - 2018-08-28T00:44:18+10:00 svc-app-2 auth.user.AuthCheck() extracted subject from JWT token    
    - 2018-08-28T15:03:50+10:00 svc-app-2 auth.user.AuthCheck() verification of subject in JWT token failed: past expiry date 
  - 2018-08-28T15:03:50+15:00 svc-app-1 auth.user.Login() auth check provider returned auth failure for bd5f12d5-130a-4ed3-bf21-0eff55c1a2af 
  - 2018-08-29T11:53:35+10:00 svc-app-1 auth.user.Login() login failed for bd5f12d5-130a-4ed3-bf21-0eff55c1a2af
  
  
#### Design Choices
1. A Trace object consist of the following attributes:
   - time
   - app
   - msg
   - span id
   - parent span id
   - component
   - level
   - env
   - trace id
   - list of child Traces
2. The program reads the log file then initially stores each trace record into single string forming an ArrayList of String. 
3. Each raw Trace strings are converted to a Trace object. If it has a parent span id, it searches the new Trace List for its parent then latches the new object to that.
4. The List of Trace objects are then printed. Necessary tab is inserted according to the Trace object's level.


#### Assumption
- The JSON input file is well created.

  
