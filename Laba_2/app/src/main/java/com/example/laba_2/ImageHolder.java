package com.example.laba_2;


import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageHolder {
    private static ImageHolder instance;
    public Map<String, Bitmap> images = new HashMap<>();
    ListFragment adapter;
    public static ImageHolder createInstance(List<Item> items, ListFragment adapter) {
        if(instance == null) {
            instance = new ImageHolder(items, adapter);
        }
        return instance;
    }

    public static ImageHolder getInstance() {
        return instance;
    }

    private ImageHolder(List<Item> items,ListFragment  adapter) {
        this.adapter = adapter;
        int[] colors = new int[300*300];
        Arrays.fill(colors, 0, 300*300, Color.GRAY);
        for(Item item : items) {
            images.put(item.getGraphic(), Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.ARGB_8888));
        }
    }
    public Bitmap getImage(String graphics) {
        return images.get(graphics);
    }

}
