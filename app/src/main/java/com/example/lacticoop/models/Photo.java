package com.example.lacticoop.models;

public class Photo {

    private Long id;
    private String photoId;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;

    public Photo(){}

    public Photo(String photoId, String owner, String secret, String server, int farm, String title, int ispublic, int isfriend, int isfamily) {
        this.id = id;
        this.photoId = photoId;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
    }

    public Long getId() {
        return id;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }
}
