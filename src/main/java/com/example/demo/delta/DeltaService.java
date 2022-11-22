package com.example.demo.delta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Delta> retrieveDeltasFromLog(String startStringDate, String endStringDate) { // 201110021848

        Timestamp startDate = convertStringToSQLTimestamp(startStringDate); // "201607090130"
        Timestamp endDate = convertStringToSQLTimestamp(endStringDate);
        List<Delta> deltasList = deltaRepository.findDeltasByDate(startDate, endDate); // all selected deltas
        List<Delta> deltasListSorted = deltasList.stream() // [Delta{id=3, operation='REMOVE', path='path/to/file/text1.txt', time='2022-11-22 13:09:38.029'}, Delta{id=2, operation='REMOVE', path='path/to/file/text2.txt', time='2022-11-22 13:09:35.283'}, Delta{id=1, operation='REMOVE', path='path/to/file/text2.txt', time='2022-11-22 13:09:32.315'}, Delta{id=4, operation='ADD', path='path/to/file/text2.txt', time='2022-11-22 13:09:41.588'}]
                .sorted(Comparator.comparing(Delta::getPath)) // sort stream and convert back to list
                .collect(Collectors.toList());
        ArrayList<Delta> result = new ArrayList<>(); // the resulting list of elements after filtering (after mutual annulment)
        ArrayList<Delta> temporaryResult = new ArrayList<>(); // all elements with same path add from for-loop, if there are contr-element - mutual annulment, if no contr-element - add new element to result / delete all from temporary
        for (Delta delta : deltasListSorted) { // text 3

            if (!temporaryResult.isEmpty() && !temporaryResult.get(0).getPath().equals(delta.getPath())) { // if list is not empty and path of new delta differs form path of delta in temporary result
                result.addAll(temporaryResult); // add temp result to result
                temporaryResult = new ArrayList<>(); // clean temp result
                temporaryResult.add(delta); // add new delta with new path to temp result
            }
            else {smartAdd(temporaryResult, delta);} // smart add to Temporary result

        }
        result.addAll(temporaryResult); // add the last one from ordered list
        return result; // add business logic
    }

        public void smartAdd (List<Delta> list, Delta delta) {
        if (list.isEmpty() || list.get(0).getOperation().equals(delta.getOperation())) { // if temp list is emppty or operations for same path are same
            list.add(delta); // add both same operations to temporary list
        } else {
            list.remove(0); // if operations are different - remove the first one because it's annulled
        }
    }

    public void formatResult (List<Delta> list, Delta delta) {}

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