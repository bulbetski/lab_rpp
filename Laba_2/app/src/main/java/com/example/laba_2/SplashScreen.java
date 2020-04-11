package com.example.laba_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HttpHandler handler = new HttpHandler();
        handler.execute("https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json");

        setContentView(R.layout.splash_screen);
//        startActivity(new Intent(this, ListActivity.class));
    }

    public class HttpHandler extends AsyncTask<String , Void, Void > {

        @Override
        protected Void doInBackground(String[] objects) {
            String url = objects[0];
            final OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String res = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                List<Item> items = mapper.readValue(res, new TypeReference<List<Item>>(){});
                ItemHandler.createInstance(items);
                Intent intent = new Intent(SplashScreen.this, ListActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e ) {e.printStackTrace();}
            return  null;
        }
    }
}
