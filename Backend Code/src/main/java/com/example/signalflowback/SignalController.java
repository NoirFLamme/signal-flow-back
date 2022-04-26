package com.example.signalflowback;


import com.example.signalflowback.createdDSs.ForwardPaths;
import com.example.signalflowback.createdDSs.Loop;
import com.example.signalflowback.createdDSs.NTLoopsCombination;
import com.example.signalflowback.createdDSs.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SignalController {
    MasonSolver service;

    @PostMapping("/initialize")
    public void initializeService(@RequestBody ArrayList<Node> graph, @RequestBody String start,
                                  @RequestBody String end)
    {
        this.service = new MasonSolver(graph, start, end);
    }

    @GetMapping("/mason/loops")
    public ArrayList<Loop> getLoops()
    {
        return this.service.returnLoops();
    }

    @GetMapping("/mason/ntloops")
    public ArrayList<LinkedList<NTLoopsCombination>> getNT()
    {
        return this.service.returnNTloops();
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
}
