package com.example.demo.delta;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data //get rid of boilerplate code
@Entity // hibernate
@Table // map class to a table in database
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor

public class Delta {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String operation;
    private String path;
    private Timestamp time = new Timestamp(System.currentTimeMillis());

    public Delta() {
    }

    public Delta(String operation,
                 String path) {
        this.operation = operation;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Delta{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", path='" + path + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}