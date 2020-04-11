package com.example.laba_2;


import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    private static ItemHandler instance;
    List<Item> items;
    private  ItemHandler(List<Item> items) {
        this.items = new ArrayList<Item>();
        for(Item item : items) {
            if(item.getName() != null) {
                this.items.add(item);
            }
        }

    }
    public static ItemHandler createInstance(List<Item> items) {
        if(instance == null) {
            instance = new ItemHandler(items);
        }
        return instance;
    }
    public static ItemHandler getInstance() {
        return instance;
    }
    public List<Item> getItems() {
        return items;
    }

}
