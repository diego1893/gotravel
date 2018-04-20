package com.monsordi.gotravel.shop;

import java.io.Serializable;

/**
 * Created by Diego on 02/04/18.
 */

public class Travel implements Serializable {

    private String title;
    private String description;
    private float price;
    private int[] images;
    private String visitedPlace;

    public Travel(String title, String description, float price, int[] images, String visitedPlace) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = images;
        this.visitedPlace = visitedPlace;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int[] getImages() {
        return images;
    }

    public String getVisitedPlace() {
        return visitedPlace;
    }
}
