package com.example.laba_5;

public class Item {
    private String url;
    private int liked;

    public Item(String url) {
        this.url = url;
        this.liked = 0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
