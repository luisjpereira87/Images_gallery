package com.example.lacticoop.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

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

public class DownloadImageService {

    String url = "";

    public ImageView get(String t, ImageView bmImage) throws InterruptedException, ExecutionException
    {
        this.url = t;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Bitmap> callable = new Callable<Bitmap>() {
            @Override
            public Bitmap call() {
                Bitmap bmp = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return bmp;
            }
        };
        Future<Bitmap> future = executor.submit(callable);
        bmImage.setImageBitmap(future.get());
        executor.shutdown();

        return bmImage;
    }
}
