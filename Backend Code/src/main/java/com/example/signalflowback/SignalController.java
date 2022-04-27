package com.example.signalflowback;


import com.example.signalflowback.Adapter.Adapter;
import com.example.signalflowback.Adapter.NTLoopsCombinationF;
import com.example.signalflowback.Adapter.NodeF;
import com.example.signalflowback.createdDSs.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SignalController {
    MasonSolver service;



//    @PostMapping("/add/node")
//    public void addNode(@RequestBody String nodeName) {
//        graph.add(new Node(nodeName));
//        System.out.println("Added node with name: " + nodeName);
//    }
//
//    @PostMapping("/add/edge")
//    public void addEdge(@RequestBody Edge edge, @RequestParam String fromNodeName) {
////        System.out.println(edge.getToNode());
////        System.out.println(edge.getGain());
//        Node toNodeRef = graph.get(getNodeIndex(edge.getToNode().getName(),graph));
//        Node fromNodeRef = graph.get(getNodeIndex(fromNodeName,graph));
//        edge.setToNode(toNodeRef);
//        fromNodeRef.addEdge(edge);
//        System.out.println("Added edge from:"+ fromNodeRef.getName() + " To:" + toNodeRef.getName());
//    }



//    @GetMapping("/init")
//    public void initService(@RequestParam String inputNode, @RequestParam String outputNode) {
//        this.service = new MasonSolver(this.graph, inputNode, outputNode);
//        System.out.println("done1");
//    }

    @PostMapping("/initialize/{st}/{ed}")
    public void initializeService(@RequestBody ArrayList<NodeF> graphF,
                                  @PathVariable String st,
                                  @PathVariable String ed )
    {
        Adapter adapter = new Adapter();
        ArrayList<Node> graph = adapter.getGraph(graphF);
        this.service = new MasonSolver(graph, st, ed);
        System.out.println("loops: ");
        printLoopList(this.service.loops);
        System.out.println("Tf: " + this.service.tf);

        System.out.println("done");
    }

    private static void printLoopList(ArrayList<Loop> loops) {
        for (Loop l : loops) {
            System.out.println(l.getLoopNodes().toString() + " " + l.getGain());
        }
    }

    @GetMapping("/mason/loops")
    public ArrayList<Loop> getLoops()
    {
        return this.service.returnLoops();
    }

    @GetMapping("/mason/ntloops")
    public ArrayList<LinkedList<NTLoopsCombinationF>> getNT()
    {
        Adapter adapter = new Adapter();
        return adapter.frontComboLarge(this.service.returnNTloops());
    }


    @GetMapping("/mason/paths")
    public ArrayList<ForwardPaths> getPaths()
    {
        return this.service.returnFPs();
    }

    @GetMapping("/mason/tf")
    public double getTF()
    {
        return this.service.returnTF();
    }

    @GetMapping("/mason/overallDelta")
    public double getOverallDelta() {
        return this.service.returnOverallDelta();
    }
}
