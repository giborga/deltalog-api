// clean imports command+option+O
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

//public static void ConvertStringToPattern() { // 2011-10-02 18:48:05
//    String text = "201607090130"; // 0/4 + 4/6 + 6/8 + 8/10 + 10/11 + 00
//    String textConverted = text.substring(0,4)
//            + '-'
//            + text.substring(4,6)
//            + '-'
//            + text.substring(6,8)
//            + ' '
//            + text.substring(8,10)
//            + ':'
//            + text.substring(10,12)
//            + ':'
//            + "00";
//    System.out.println(textConverted); //2016-07-09 01:30:00
//}
//    public static Date convertStringToDate(String sampleDatabaseString) {
//        DateTimeFormatter databaseStringFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//        LocalDateTime ltd = LocalDateTime.parse(sampleDatabaseString, databaseStringFormatter);
//        ZonedDateTime zdt = ltd.atZone(ZoneId.systemDefault());
//        System.out.println(Date.from(zdt.toInstant()));
//        return Date.from(zdt.toInstant());
//    }



//    public static LocalDateTime currentDateTime() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        System.out.println(LocalDateTime.now());
//        return LocalDateTime.now();
//        }

//public static LocalDateTime localDateTimeWithoutSeconds() {
//
//    String date = "202211211828";
//    //  ISO Local Date and Time '2011-12-03T10:15:30'
//    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//    LocalDateTime dateTimeParked = LocalDateTime.parse(date, formatter);
//    return dateTimeParked; //2016-07-09T01:30
//}