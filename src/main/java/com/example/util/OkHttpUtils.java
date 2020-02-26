package com.example.util;

import com.example.pojo.github.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OkHttpUtils {

    /*
     * Get a URL
     * 返回一个json
     */
    public static String getHttp(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)//https://api.github.com/user?access_token=" + accessToken
                .build();
        try (
                Response response = client.newCall(request).execute()) {
            String responseStr = response.body().string();
            return responseStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *Post to a Server¶
     */
    public static String postHttp(String bodyJson,String url) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, bodyJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (
                Response response = client.newCall(request).execute()) {
            String responseStr = response.body().string();
            System.out.println(responseStr);//access_token=c231d1e359bed82f6f98b2a2d86eb916478b56d1&scope=user&token_type=bearer
            return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
