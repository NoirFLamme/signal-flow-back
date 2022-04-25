package com.example.signalflowback;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Loop {
    ArrayList<String> loopNodes;
    double gain;

    public Loop(Deque<Node> nodeStack, double gain) {
        Deque<Node> copyOfStack = new LinkedList<>(nodeStack);
        this.loopNodes = new ArrayList<>();
        while (!copyOfStack.isEmpty()) this.loopNodes.add(copyOfStack.removeLast().name);
        this.gain = gain;
    }

    public Loop(ArrayList<String> loopNodes, double gain) {
        this.loopNodes = loopNodes;
        this.gain = gain;
    }
}

