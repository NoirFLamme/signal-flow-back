package com.example.signalflowback;

import java.util.ArrayList;
import java.util.Set;

public class NTLoopsCombination {
    double gain;
    Set<Loop> NTLoops;
    Set<String> nodesAfterJoining;

    public NTLoopsCombination(double gain, Set<Loop> NTLoops, Set<String> nodesAfterJoining) {
        this.gain = gain;
        this.NTLoops = NTLoops;
        this.nodesAfterJoining = nodesAfterJoining;
    }
}
