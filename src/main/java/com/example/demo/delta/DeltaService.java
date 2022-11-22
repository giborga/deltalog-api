package com.example.demo.delta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public List<Delta> retrieveDeltasFromLog(String startStringDate, String endStringDate) { // сюда попадает формат 201110021848

        Timestamp startDate = convertStringToSQL(startStringDate); //"201607090130"
        Timestamp endDate = convertStringToSQL(endStringDate);
        // add business logic here
        List<Delta> deltasList = deltaRepository.findDeltasByDate(startDate, endDate); //
        List<Delta> deltasListSorted = deltasList.stream() // [Delta{id=3, operation='REMOVE', path='path/to/file/text1.txt', time='2022-11-22 13:09:38.029'}, Delta{id=2, operation='REMOVE', path='path/to/file/text2.txt', time='2022-11-22 13:09:35.283'}, Delta{id=1, operation='REMOVE', path='path/to/file/text3.txt', time='2022-11-22 13:09:32.315'}, Delta{id=4, operation='REMOVE', path='path/to/file/text3.txt', time='2022-11-22 13:09:41.588'}]
                .sorted(Comparator.comparing(Delta::getPath))
                .collect(Collectors.toList());
        ArrayList<Delta> result = new ArrayList<>(); // list of elements after filtering (after mutual anullement)
        ArrayList<Delta> temporaryResult = new ArrayList<>(); // all elements with same path, if there are contr-element - mutual anullement, if no contr-element - add new element / add all elements to result / delete all from temporary
        for (Delta delta : deltasListSorted) {

            if (!temporaryResult.isEmpty() && !temporaryResult.get(0).getPath().equals(delta.getPath())) { //if list is not empty
                result.addAll(temporaryResult);
                temporaryResult = new ArrayList<>();
            }
            else {temporaryResult.SmartAdd(delta);} // smart add to Temporary result

        }
        return deltaRepository.findDeltasByDate(startDate, endDate);
    }

    public SmartAdd (List<> list, delta) {

    }


    public static Timestamp convertStringToSQL(String text) { // "201607090130"
        String textConverted = ConvertStringToPattern(text);
        Timestamp sqlTimestamp = Timestamp.valueOf(textConverted);
        return sqlTimestamp; //"2011-10-02 18:48:00"
    }

    public static String ConvertStringToPattern(String text) { // "201607090130"
        String textConverted = text.substring(0, 4)
                + '-'
                + text.substring(4, 6)
                + '-'
                + text.substring(6, 8)
                + ' '
                + text.substring(8, 10)
                + ':'
                + text.substring(10, 12)
                + ':'
                + "00";
        return textConverted; //2016-07-09 01:30:00
    }

}

//    public static Date convertStringToDate(String sampleDatabaseString) {
//        DateTimeFormatter databaseStringFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//        LocalDateTime ltd = LocalDateTime.parse(sampleDatabaseString, databaseStringFormatter);
//        ZonedDateTime zdt = ltd.atZone(ZoneId.systemDefault());
//        System.out.println(Date.from(zdt.toInstant()));
//        return Date.from(zdt.toInstant());
//    }


//        Optional<Delta> deltaOptional = deltaRepository.findDelta(delta.getPath());
//        if (deltaOptional.isPresent()) {
//            Long newDeltaId = deltaOptional.get().getId();
//            deltaRepository.deleteById(newDeltaId);
//            System.out.println(newDeltaId);
//        }
//        else {