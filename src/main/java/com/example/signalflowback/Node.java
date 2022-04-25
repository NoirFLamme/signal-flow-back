package com.example.signalflowback;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {
     final String name;
     ArrayList<Edge> edgeArrayList;



    public Node(String name, ArrayList<Edge> edgeArrayList) {
        this.name = name;
        this.edgeArrayList = edgeArrayList;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getEdgeArrayList() {
        return edgeArrayList;
    }

    public void setEdgeArrayList(ArrayList<Edge> edgeArrayList) {
        this.edgeArrayList = edgeArrayList;
    }
}
