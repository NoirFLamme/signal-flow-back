package com.example.signalflowback.createdDSs;

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

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public Set<Loop> getNTLoops() {
        return NTLoops;
    }

    public void setNTLoops(Set<Loop> NTLoops) {
        this.NTLoops = NTLoops;
    }

    public Set<String> getNodesAfterJoining() {
        return nodesAfterJoining;
    }

    public void setNodesAfterJoining(Set<String> nodesAfterJoining) {
        this.nodesAfterJoining = nodesAfterJoining;
    }
}
