package com.example.signalflowback.createdDSs;

public class Edge {
    final int gain;
    private Node toNode;


    public Edge(int gain, Node toNode) {
        this.gain = gain;
        this.toNode = toNode;
    }

    public int getGain() {
        return gain;
    }

    public Node getToNode() {
        return toNode;
    }

    public void setToNode(Node toNode) {
        this.toNode = toNode;
    }
}


//forward paths
//all loops
//all non-touching loops combinations
