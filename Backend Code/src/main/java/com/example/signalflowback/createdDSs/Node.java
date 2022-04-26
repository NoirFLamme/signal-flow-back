package com.example.signalflowback.createdDSs;

import java.util.ArrayList;

public class Node {
     String name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEdgeArrayList(ArrayList<Edge> edgeArrayList) {
        this.edgeArrayList = edgeArrayList;
    }

    public int getID(){ return Integer.parseInt(this.name)-1;}

    public Node getItWithoutEdges(){
        return new Node(this.name,new ArrayList<>());
    }
}
