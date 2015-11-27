package com.example.spartan_117.servicesomee;


import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    public static String getData(String uri)
    {
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            return  stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public static String getData(String uri, String userName, String passWord)
    {
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        byte[] loginBytes = (userName +":"+passWord).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Authorization", loginBuilder.toString());

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            return  stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try {
                int status = connection.getResponseCode();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            return null;
        }
        finally {
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

}

