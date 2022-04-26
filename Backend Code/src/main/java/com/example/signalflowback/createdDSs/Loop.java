package com.example.signalflowback.createdDSs;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Loop {
    ArrayList<String> loopNodes;
    double gain;

    public Loop(Deque<Node> nodeStack, double gain) {
        Deque<Node> copyOfStack = new LinkedList<>(nodeStack);
        this.loopNodes = new ArrayList<>();
        while (!copyOfStack.isEmpty()) this.loopNodes.add(copyOfStack.removeLast().getName());
        this.gain = gain;
    }

    public Loop(ArrayList<String> loopNodes, double gain) {
        this.loopNodes = loopNodes;
        this.gain = gain;
    }

    public ArrayList<String> getLoopNodes() {
        return loopNodes;
    }

    public void setLoopNodes(ArrayList<String> loopNodes) {
        this.loopNodes = loopNodes;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }
}

