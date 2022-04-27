package com.example.signalflowback.Adapter;

import com.example.signalflowback.createdDSs.Edge;
import com.example.signalflowback.createdDSs.Loop;
import com.example.signalflowback.createdDSs.NTLoopsCombination;
import com.example.signalflowback.createdDSs.Node;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public NTLoopsCombinationF frontComboSmall(NTLoopsCombination comb){
        StringBuilder frontLoops = new StringBuilder();
        for(Loop l : comb.getNTLoops()){
            for (int i = 0;i<l.getLoopNodes().size();i++){
                frontLoops.append(l.getLoopNodes().get(i));
                if(i!=l.getLoopNodes().size()-1) frontLoops.append("-");
            }
            frontLoops.append(",");
        }
        frontLoops.deleteCharAt(frontLoops.length()-1);
        return new NTLoopsCombinationF(frontLoops.toString(),comb.getGain());
    }

    public ArrayList<LinkedList<NTLoopsCombinationF>> frontComboLarge(ArrayList<LinkedList<NTLoopsCombination>> ntcArray){
        ArrayList<LinkedList<NTLoopsCombinationF>> result = new ArrayList<>();
        for (int i=0;i<ntcArray.size();i++){
            result.add(new LinkedList<>());
            for (int j=0;j<ntcArray.get(i).size();j++){
                result.get(i).add(frontComboSmall(ntcArray.get(i).get(j)));
            }
        }
        return result;
    }
}
