package com.matrixdeveloper.tajika.model;

public class SPBbusinessPhotosVideosModel {
    private String id;
    private String imageUrl;
    private int typeServerLocal;

    public SPBbusinessPhotosVideosModel(String id, String imageUrl, int typeServerLocal) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.typeServerLocal = typeServerLocal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTypeServerLocal() {
        return typeServerLocal;
    }

    public void setTypeServerLocal(int typeServerLocal) {
        this.typeServerLocal = typeServerLocal;
    }
}
