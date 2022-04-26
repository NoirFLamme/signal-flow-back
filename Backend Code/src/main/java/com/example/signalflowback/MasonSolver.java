package com.example.signalflowback;

import com.example.signalflowback.createdDSs.ForwardPaths;
import com.example.signalflowback.createdDSs.Loop;
import com.example.signalflowback.createdDSs.NTLoopsCombination;
import com.example.signalflowback.createdDSs.Node;
import com.example.signalflowback.MasonHelpingClasses.ForwardFinder;
import com.example.signalflowback.MasonHelpingClasses.LoopsFinder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

public class MasonSolver {

    LoopsFinder evaluator;
    ForwardFinder pathFinder;
    ArrayList<ForwardPaths> paths;
    ArrayList<Loop> loops;
    ArrayList<LinkedList<NTLoopsCombination>> ntLoops;
    double overallDelta;

    public MasonSolver(ArrayList<Node> graph, String start, String end)
    {
        this.evaluator = new LoopsFinder(graph);
        this.pathFinder = new ForwardFinder();
        this.loops = this.evaluator.findAllLoops();
        this.ntLoops = this.evaluator.findNTLs(this.loops);
        this.paths = this.pathFinder.getAllPaths(graph, start, end);
        this.evaluator.getPathDelta(this.loops, this.ntLoops,this.paths);
        this.overallDelta = this.evaluator.getOverallDelta(this.loops, this.ntLoops);
    }

    public ArrayList<Loop> returnLoops()
    {
        return this.loops;
    }

    public ArrayList<ForwardPaths> returnFPs()
    {
        return this.paths;
    }

    public ArrayList<LinkedList<NTLoopsCombination>> returnNTloops()
    {
        return ntLoops;
    }

    public double returnOverallDelta()
    {
        return this.overallDelta;
    }
    
}
