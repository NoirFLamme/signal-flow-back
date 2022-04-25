package com.example.signalflowback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Forward {


    ArrayList<String> pathAcc = new ArrayList<>();
    double gainAcc = 1;
    ArrayList<ForwardPaths> forwardPaths = new ArrayList<>();
    public void getAllPaths(ArrayList<Node> graph)
    {
        boolean[] isVisited = new boolean[graph.size()];

        pathAcc.add(graph.get(0).name);

        printAllPathsUtil(0, graph.size() - 1, isVisited, graph);

        for (ForwardPaths i: forwardPaths
             ) {
            System.out.println(i.path);
            System.out.println(i.gain);
        }
    }


    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited, ArrayList<Node> graph)
    {

        if (u.equals(d)) {
//            ArrayList<String> temp = pathAcc.clone();
            forwardPaths.add(new ForwardPaths((ArrayList<String>) pathAcc.clone(), gainAcc));
            // if match found then no need to traverse more till depth
            return;
        }

        // Mark the current node
        isVisited[u]= true;

        // Recur for all the vertices
        // adjacent to current vertex
        for (Edge i : graph.get(u).edgeArrayList) {
            if (!isVisited[graph.indexOf(i.toNode)]) {
                // store current node
                // in path[]
                pathAcc.add(i.toNode.name);
                gainAcc *= i.gain;
                printAllPathsUtil(graph.indexOf(i.toNode), d, isVisited, graph);

                // remove current node
                // in path[]
                gainAcc /= i.gain;
                pathAcc.remove(i.toNode.name);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }

    public static void main(String[] args) {
        ArrayList<Edge> empty = new ArrayList<>();
        Node n0 = new Node ("0",empty);
        Node n1 = new Node ("1",empty);
        Node n2 = new Node ("2",empty);
        Node n3 = new Node ("3",empty);
        Node n4 = new Node ("4",empty);
        Edge e01 = new Edge(7,n1);
        Edge e12 = new Edge(8,n2);
        Edge e24 = new Edge(2,n4);
        Edge e42 = new Edge(-5,n2);
        Edge e23 = new Edge(-1,n3);
        Edge e34 = new Edge(-3,n4);
        ArrayList<Edge> el0 = new ArrayList<>();
        el0.add(e01);
        n0.setEdgeArrayList(el0);
        ArrayList<Edge> el1 = new ArrayList<>();
        el1.add(e12);
        n1.setEdgeArrayList(el1);
        ArrayList<Edge> el2 = new ArrayList<>();
        el2.add(e24);
        el2.add(e23);
        n2.setEdgeArrayList(el2);
        ArrayList<Edge> el3 = new ArrayList<>();
        el3.add(e34);
        n3.setEdgeArrayList(el3);
        ArrayList<Edge> el4 = new ArrayList<>();
        el4.add(e42);
        n4.setEdgeArrayList(el4);
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n0,n1,n2,n3,n4));
        Forward yes = new Forward();

        yes.getAllPaths(nodes);
    }
}
