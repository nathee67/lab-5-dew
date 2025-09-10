package com.example.nathee018;

import java.util.List;

public class CheckListNote extends Note {
    private List<String> items;


    public String getSummary() {
        String strItems = "";
        int noOfList = items.size();
        for(int i=0 ; i< noOfList ; i++){
            strItems = strItems+items.get(i);
        }

        return title+":"+items.get(0)+"("+createdDate+")";
    }
}