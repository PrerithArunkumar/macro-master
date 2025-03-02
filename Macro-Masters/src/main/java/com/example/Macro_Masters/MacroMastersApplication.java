package com.example.Macro_Masters;

import okhttp3.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class MacroMastersApplication {

    private static final String API_KEY = "aa099984412444e395b4ebc087091516"; // Replace with your Spoonacular API Key
    private static final String API_URL = "https://api.spoonacular.com/food/images/classify";

    public static void main(String[] args) {
        SpringApplication.run(MacroMastersApplication.class, args);

        File imageFile = new File("Macro-Masters/src/main/images/cookies.jpg"); // Replace with your image file path
        try {
            String response = uploadImage(imageFile);
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String uploadImage(File file) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        Request request = new Request.Builder()
                .url(API_URL + "?apiKey=" + API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "No response";
        }
    }
}