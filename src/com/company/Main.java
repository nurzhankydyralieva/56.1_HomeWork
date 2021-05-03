package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {
    private static HttpURLConnection connection;

    public static void main(String[] args) {
        Gson gson = new Gson();
        BufferedReader reader;
        String line;
        StringBuffer responseConnect = new StringBuffer();
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseConnect.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseConnect.append(line);
                }
                reader.close();
            }
            System.out.println("--------------------Json--------------------------------------");
            System.out.println(responseConnect.toString());
            String jsonStr = String.valueOf(responseConnect);
            List<UserSimple> userSimpleList;
            userSimpleList = gson.fromJson(jsonStr, new TypeToken<List<UserSimple>>() {
            }.getType());
            System.out.println("--------------------Java classes ------------------------------");
            for (UserSimple i : userSimpleList) {
                System.out.println(i + "\n");
            }
            System.out.println("--------------------Parsed -------------------------------------");
            parse(responseConnect.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
    public static String parse(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0; i < albums.length(); i++) {
            JSONObject album = albums.getJSONObject(i);
            int userId = album.getInt("userId");
            int id = album.getInt("id");
            String title = album.getString("title");
            String body = album.getString("body");
            System.out.println("User id = " + userId + "\nid = " + id + "\nTitle: " + title + "\nBody: " + body + "\n");
        }
        return null;
    }
}
