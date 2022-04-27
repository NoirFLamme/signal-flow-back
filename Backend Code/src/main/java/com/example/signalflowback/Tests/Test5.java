package com.example.signalflowback.Tests;

import com.example.signalflowback.Adapter.Adapter;
import com.example.signalflowback.Adapter.NTLoopsCombinationF;
import com.example.signalflowback.MasonHelpingClasses.ForwardFinder;
import com.example.signalflowback.MasonHelpingClasses.LoopsFinder;
import com.example.signalflowback.createdDSs.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Test5 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Test 5");
        ArrayList<Edge> empty = new ArrayList<>();
        Node n1 = new Node("1", empty);
        Edge e11 = new Edge(3,n1);
        ArrayList<Edge> el1 = new ArrayList<>();
        el1.add(e11);
        n1.setEdgeArrayList(el1);

        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n1));
        LoopsFinder myclass = new LoopsFinder(nodes);
        ForwardFinder yes = new ForwardFinder();
        ArrayList<ForwardPaths> paths = yes.getAllPaths(nodes, "1", "1");
        ArrayList<Loop> loops = myclass.findAllLoops();
        System.out.println("Loops:");
        printLoopList(loops);
        System.out.println("Non Touching Loops:");
        ArrayList<LinkedList<NTLoopsCombination>> nt = myclass.findNTLs(loops);
        Adapter adamptMe = new Adapter();
        ArrayList<LinkedList<NTLoopsCombinationF>> ntF =  adamptMe.frontComboLarge(nt);
        printNT(nt);
        System.out.println(myclass.getOverallDelta(loops, nt));
        myclass.getPathDelta(loops, nt, paths);
        System.out.println(myclass.getTF(myclass.getOverallDelta(loops, nt), paths));
    }

    private static void printLoopList(ArrayList<Loop> loops) {
        for (Loop l : loops) {
            System.out.println(l.getLoopNodes().toString() + " " + l.getGain());
        }
    }

    private static void printPaths(ArrayList<ForwardPaths> paths) {
        for (ForwardPaths p : paths) {
            System.out.println(p.getPath().toString() + " " + p.getGain());
        }
    }

    private static void printNT(ArrayList<LinkedList<NTLoopsCombination>> nts) {
        for (LinkedList<NTLoopsCombination> ll : nts) {
            for (NTLoopsCombination l : ll){
                System.out.println(l.getNodesAfterJoining().toString() + " " + l.getGain());
            }
        }
    }

}
