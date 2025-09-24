package com.example.nathee018;

import java.util.ArrayList;
import java.util.List;

public class CheckListNote extends Note {
    private List<String> items;

    public CheckListNote(String title, String createdDate, List<String> items) {
        super(title, createdDate);
        this.items = (items != null) ? items : new ArrayList<>();
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String getSummary() {
        if (items == null || items.isEmpty()) {
            return title + " (no items) (" + createdDate + ")";
        }
        return title + ": " + items.get(0) + " (" + createdDate + ")";
    }
}
