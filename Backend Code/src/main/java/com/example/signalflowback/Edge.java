package com.example.signalflowback;

public class Edge {
    final int gain;
    final Node toNode;


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
}


//forward paths
//all loops
//all non-touching loops combinations
