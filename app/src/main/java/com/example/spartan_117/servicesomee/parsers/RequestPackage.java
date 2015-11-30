package com.example.spartan_117.servicesomee.parsers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestPackage {

    private String uri;
    private String method = "GET";
    private Map<String, String> params = new HashMap<>();
    private Map<String, Map<String,String>> other = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParam(String key, String value)
    {
        params.put(key,value);
    }

    public void setOther(String name, Map<String,String> params)
    {
        other.put(name,params);
    }

    public String getEncodedParams()
    {
        StringBuilder builder = new StringBuilder();

        for (String key: params.keySet())
        {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (builder.length() > 0)
            {
                builder.append("&");
            }

            builder.append(key + "=" + value);
        }
        return builder.toString();
    }

    public Map<String, Map<String, String>> getOther() {
        return other;
    }


}
