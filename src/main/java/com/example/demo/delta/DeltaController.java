package com.example.demo.delta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping

public class DeltaController {

    private final DeltaService deltaService;

    @Autowired
    public DeltaController(DeltaService deltaService) {
        this.deltaService = deltaService;
    }

    @GetMapping(path="/deltas")
    public List<Delta> getDelta() {
        return deltaService.getDeltaFromLog();
    }

    @PostMapping(path="/deltas")
    public void addDelta(@RequestBody Delta delta) {
        deltaService.addNewDeltaToLog(delta);
    }

    @PostMapping(path="/delta-logs")
    public Map<String, List<String>> retrieveDeltas(@RequestBody Interval interval) {
        return deltaService.retrieveDeltasFromLog(interval.getStartDate(), interval.getEndDate());
    }

}


