package com.example.spartan_117.servicesomee;

import com.example.spartan_117.servicesomee.parsers.RequestPackage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpManagerSecond {

    static Process process = new Process();
    static List<TimeOperation>  timeOperations = new ArrayList<>();
    static List<Operation> operations = new ArrayList<>();
    static TimeOperation timeOperation = new TimeOperation();
    static Operation operation = new Operation();
    //static JSONObject object;

    public static void seed()
    {
        timeOperation.setBegin(23);
        timeOperation.setFinish(40);
        timeOperations.add(timeOperation);
        operation.setName("Otra");
        operation.setTimeOperations(timeOperations);

        List<Process> processes = new ArrayList<>();

        process.setName("Android");
        process.setOperations(operations);
        processes.add(process);

      //  JSONObject object2 = new JSONObject(process);
    }

    public static String getData(RequestPackage aPackage)
    {
        HttpURLConnection connection = null;
        BufferedReader reader =  null;
        String uri = aPackage.getUri();

        if (aPackage.getMethod().equals("GET"))
        {
            uri += "?" + aPackage.getEncodedParams();
        }

        try
        {

           //JSONObject object = new JSONObject(process);

            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(aPackage.getMethod());
            connection.setRequestProperty("Content-type","application/json");

            JSONObject jsonObject = new JSONObject(aPackage.getParams());
           // JSONObject jsonObject = new JSONObject(aPackage.getOther());


            String s =  "{\"Name\":\"Chaleco\",\"Operations\":[{\"Name\":\"Bota\",\"TimeOperations\":[{\"Begin\":32,\"Finish\":39,\"Total\":0}]}]}";

            String params = "params="+s;

            if (aPackage.getMethod().equals("POST"))
            {
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
             //   writer.write(aPackage.getEncodedParams());
                writer.write(s);
                writer.flush();
            }

            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line + "\n");
            }

            return builder.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Process {

        private String name;
        private List<Operation> operations;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Operation> getOperations() {
            return operations;
        }

        public void setOperations(List<Operation> operations) {
            this.operations = operations;
        }
    }

        public static class Operation {

            private String Name;
            private List<TimeOperation> timeOperations;

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public List<TimeOperation> getTimeOperations() {
                return timeOperations;
            }

            public void setTimeOperations(List<TimeOperation> timeOperations) {
                this.timeOperations = timeOperations;
            }
        }

            public static class TimeOperation {

                private int begin;
                private int finish;

                public int getBegin() {
                    return begin;
                }

                public void setBegin(int begin) {
                    this.begin = begin;
                }

                public int getFinish() {
                    return finish;
                }

                public void setFinish(int finish) {
                    this.finish = finish;
                }
            }
}
