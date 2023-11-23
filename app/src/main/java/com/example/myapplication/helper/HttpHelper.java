package com.example.myapplication.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    private String id;
    private String pw;

    private final String HOST = "http://193.123.236.54:80";


    private static HttpHelper instance = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static HttpHelper getInstance()
    {
        if(instance == null)
        {
            instance = new HttpHelper();
        }

        return instance;
    }

    public String login( String id, String pw){
        Map<String, String> map = new HashMap<>();
        map.put("user_id", id);
        map.put("user_pw", pw);

        //map.put("user_id", "admin");
        //map.put("user_pw", "passw0rd7!");
        JsonObject jobj = post("/api/user/login", null, map);
        if(jobj != null) {
            JsonObject t;
            try {
                t = jobj.get("data").getAsJsonObject();
                this.id = id;
                this.pw = pw;
                return t.get("token").toString().replace("\"","");
            } catch (Exception e) {

            }
        }
        return null;
    }

    public JsonObject post(String suffixUrl, Map<String, String> params){

        if( id == null && pw == null)
            return null;

        return post(suffixUrl, login( id, pw), params);
    }

    public JsonObject get(String suffixUrl){

        if( id == null && pw == null)
            return null;

        JsonObject result = null;
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String token = login( id, pw);
        Request request;

        if(token == null)
            return result;

        request = new Request.Builder()
                .url(HOST+suffixUrl)
                .addHeader("Authorization", token)
                .get()
                .build();




        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            //Headers responseHeaders = response.headers();
            //for (int i = 0; i < responseHeaders.size(); i++) {
            //String name = responseHeaders.name(i);
            //String value = responseHeaders.value(i);
            //}

            String e = response.body().string();
            result = new Gson().fromJson(e, JsonObject.class);

        }catch(Exception e){
            System.out.println(e);

        }

        return result;
    }

    private JsonObject post(String suffixUrl, String token, Map<String, String> params){
        JsonObject result = null;
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(params);

        Request request;

        if(token == null) {
            request = new Request.Builder()
                    .url(HOST+suffixUrl)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();
        }else{
            request = new Request.Builder()
                    .url(HOST+suffixUrl)
                    .addHeader("Authorization", token)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

        }


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            //Headers responseHeaders = response.headers();
            //for (int i = 0; i < responseHeaders.size(); i++) {
            //String name = responseHeaders.name(i);
            //String value = responseHeaders.value(i);
            //}

            String e = response.body().string();
            result = new Gson().fromJson(e, JsonObject.class);

        }catch(Exception e){
            System.out.println(e);

        }

        return result;
    }


}
