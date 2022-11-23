package com.example.demo.delta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DeltaService {

    private final DeltaRepository deltaRepository; // needs repository to fetch from db

    @Autowired
    public DeltaService(DeltaRepository deltaRepository) { //constructor
        this.deltaRepository = deltaRepository;
    }

    // methods
    public List<Delta> getDeltaFromLog() {
        return deltaRepository.findAll();
    }

    public void addNewDeltaToLog(Delta delta) {
        deltaRepository.save(delta);
    }

    public Map<String, List<String>> retrieveDeltasFromLog(String startStringDate, String endStringDate) { // 201110021848

        Timestamp startDate = convertStringToSQLTimestamp(startStringDate); // "201607090130"
        Timestamp endDate = convertStringToSQLTimestamp(endStringDate);

        List<String> deltasAdd = deltaRepository.findDeltasByDateAdd(startDate, endDate);
        List<String> deltasRemove = deltaRepository.findDeltasByDateRemove(startDate, endDate);
        Map<String, List<String>> allDeltas = new HashMap<>();
        allDeltas.put("addedFiles", deltasAdd);
        allDeltas.put("deletedFiles", deltasRemove);

        for (int i = 0; i < deltasAdd.size(); i++) { // if remove elements -> pay attention -> java.util.ConcurrentModificationException: null
            String path = deltasAdd.get(i);
            if (deltasRemove.contains(path)) {
                deltasAdd.remove(path);
                deltasRemove.remove(path);
                i--;
            }
        }

        return allDeltas;

    }

    public static Timestamp convertStringToSQLTimestamp(String stringDateTime) { // "201607090130"

        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
            Date date = df.parse(stringDateTime);
            return new Timestamp(date.getTime());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return null;
    }
}