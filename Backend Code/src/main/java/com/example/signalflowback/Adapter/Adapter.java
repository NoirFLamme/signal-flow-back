package com.example.signalflowback.Adapter;

import com.example.signalflowback.createdDSs.Edge;
import com.example.signalflowback.createdDSs.Node;

import java.util.ArrayList;

public class Adapter {

    public ArrayList<Node> getGraph(ArrayList<NodeF> graphF) {
        ArrayList<Node> graph = new ArrayList<>();
        for (NodeF node: graphF) {
            Node n = new Node(node.getName());
            graph.add(n);
        }

        for (NodeF node: graphF) {
            Node fromNode = graph.get(getNodeIndex(node.getName(), graph));
            for (EdgeF edge: node.getEdgeArrayList()) {
                Node toNode = graph.get(getNodeIndex(edge.getToNode(), graph));
                Edge e = new Edge(edge.getGain(), toNode);
                fromNode.addEdge(e);
            }
        }
        return graph;
    }

    private int getNodeIndex(String a, ArrayList<Node> graph)
    {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).getName().equals(a)) return i;
        }
        return -1;
    }
}
