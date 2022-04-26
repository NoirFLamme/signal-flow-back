package com.example.signalflowback.Adapter;

public class EdgeF {
    final int gain;
    private String toNode;

    public EdgeF(int gain, String toNode) {
        this.gain = gain;
        this.toNode = toNode;
    }

    public int getGain() {
        return gain;
    }

    public String getToNode() {
        return toNode;
    }

    public void setToNode(String toNode) { this.toNode = toNode; }
}
