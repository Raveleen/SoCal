package com.raveleen.services.map;

/**
 * Created by Святослав on 21.03.2017.
 */
public class MapInfo {
    private String url;
    private String rating;
    private MapGeometry geometry;

    public MapInfo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public MapGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(MapGeometry geometry) {
        this.geometry = geometry;
    }
}
