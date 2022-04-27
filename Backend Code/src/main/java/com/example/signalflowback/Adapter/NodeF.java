package com.example.signalflowback.Adapter;

import java.util.ArrayList;

public class NodeF {
    String name;
    ArrayList<EdgeF> edgeArrayList;

    public NodeF(String name, ArrayList<EdgeF> edgeArrayList) {
        this.name = name;
        this.edgeArrayList = edgeArrayList;
    }

    public NodeF(String name) {
        this.name = name;
        this.edgeArrayList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EdgeF> getEdgeArrayList() {
        return edgeArrayList;
    }

    public void setEdgeArrayList(ArrayList<EdgeF> edgeArrayList) {
        this.edgeArrayList = edgeArrayList;
    }
}
