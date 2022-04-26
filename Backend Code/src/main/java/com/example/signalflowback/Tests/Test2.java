package com.example.signalflowback.Tests;

import com.example.signalflowback.MasonHelpingClasses.ForwardFinder;
import com.example.signalflowback.MasonHelpingClasses.LoopsFinder;
import com.example.signalflowback.createdDSs.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Test 2");
        ArrayList<Edge> empty = new ArrayList<>();
        Node n1 = new Node("1", empty);
        Node n2 = new Node("2", empty);
        Node n3 = new Node("3", empty);
        Node n4 = new Node("4", empty);
        Node n5 = new Node("5", empty);
        Node n6 = new Node("6", empty);
        Node n7 = new Node("7", empty);
        Node n8 = new Node("8", empty);
        Node n9 = new Node("9", empty);
        Edge e12 = new Edge(15, n2);
        Edge e18 = new Edge(3, n8);
        Edge e23 = new Edge(8, n3);
        Edge e27 = new Edge(5, n7);
        Edge e29 = new Edge(12, n9);
        Edge e31 = new Edge(5, n1);
        Edge e32 = new Edge(-7, n2);
        Edge e34 = new Edge(7, n4);
        Edge e36 = new Edge(8, n6);
        Edge e45 = new Edge(-2, n5);
        Edge e52 = new Edge(11, n2);
        Edge e64 = new Edge(-6, n4);
        Edge e89 = new Edge(1, n9);
        Edge e98 = new Edge(-2, n8);
        ArrayList<Edge> el1 = new ArrayList<>();
        el1.add(e12);
        el1.add(e18);
        n1.setEdgeArrayList(el1);
        ArrayList<Edge> el2 = new ArrayList<>();
        el2.add(e23);
        el2.add(e27);
        el2.add(e29);
        n2.setEdgeArrayList(el2);
        ArrayList<Edge> el3 = new ArrayList<>();
        el3.add(e31);
        el3.add(e32);
        el3.add(e34);
        el3.add(e36);
        n3.setEdgeArrayList(el3);
        ArrayList<Edge> el4 = new ArrayList<>();
        el4.add(e45);
        n4.setEdgeArrayList(el4);
        ArrayList<Edge> el5 = new ArrayList<>();
        el5.add(e52);
        n5.setEdgeArrayList(el5);
        ArrayList<Edge> el6 = new ArrayList<>();
        el6.add(e64);
        n6.setEdgeArrayList(el6);
        ArrayList<Edge> el8 = new ArrayList<>();
        el8.add(e89);
        n8.setEdgeArrayList(el8);
        ArrayList<Edge> el9 = new ArrayList<>();
        el9.add(e98);
        n9.setEdgeArrayList(el9);

        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8, n9));
        LoopsFinder myclass = new LoopsFinder(nodes);
        ForwardFinder yes = new ForwardFinder();
        ArrayList<ForwardPaths> paths = yes.getAllPaths(nodes, "1", "7");
        ArrayList<Loop> loops = myclass.findAllLoops();
        System.out.println("Loops:");
        printLoopList(loops);
        System.out.println("Non Touching Loops:");
        ArrayList<LinkedList<NTLoopsCombination>> nt = myclass.findNTLs(loops);
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
