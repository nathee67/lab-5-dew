package com.example.nathee018;

import java.util.Date;

public abstract class Note {
    public Date getCreatedDate;
    protected String title;
    protected String createdDate;

    public Note(String title, String createdDate) {
        this.title = title;
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public abstract String getSummary();
}
