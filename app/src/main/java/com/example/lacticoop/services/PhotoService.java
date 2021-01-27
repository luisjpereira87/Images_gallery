package com.example.lacticoop.services;

import com.example.lacticoop.models.Photo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PhotoService {

    private static final String  URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=f9cc014fa76b098f9e82f1c288379ea1&tags=kitten&page=1&format=json&nojsoncallback=1";

    public List<Photo> getAll() throws InterruptedException, ExecutionException
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Photo>> callable = new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader reader = null;
                try {

                    httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream stream = httpURLConnection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line = "";
                    String data = "";
                    while ((line = reader.readLine()) != null) {
                        data = data + line;
                    }

                    JSONObject object = new JSONObject(data).getJSONObject("photos");
                    JSONArray jsonArray = object.optJSONArray("photo");
                    List<Photo> photoList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        photoList.add(new Photo(
                                (String)jsonObject.get("id"),
                                (String)jsonObject.get("owner"),
                                (String)jsonObject.get("secret"),
                                (String)jsonObject.get("server"),
                                (int)jsonObject.get("farm"),
                                (String)jsonObject.get("title"),
                                (int)jsonObject.get("ispublic"),
                                (int)jsonObject.get("isfriend"),
                                (int)jsonObject.get("isfamily")
                        ));
                    }
                    return photoList;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
                return null;
            }
        };
        Future<List<Photo>> future = executor.submit(callable);
        executor.shutdown();
        return future.get();
    }
}
