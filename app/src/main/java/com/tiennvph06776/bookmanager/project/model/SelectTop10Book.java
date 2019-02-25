package com.tiennvph06776.bookmanager.project.model;

public class SelectTop10Book {
    String id;
    int amount;

    public SelectTop10Book(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public SelectTop10Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
