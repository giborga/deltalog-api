package com.example.demo.delta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeltaRepository
        extends JpaRepository<Delta, Long> {
    // custom method to find delta by path and operation type
    @Query("SELECT delta FROM Delta delta WHERE delta.path = ?1")
    Optional<Delta> findDelta(String path);

    @Query("SELECT delta.path " +
            "FROM Delta delta " +
            "WHERE delta.time > ?1 " +
            "AND delta.time < ?2 " +
            "AND delta.operation = 'ADD'")
    List<String> findDeltasByDateAdd(Timestamp startTime, Timestamp endTime);

    @Query("SELECT delta.path " +
            "FROM Delta delta " +
            "WHERE delta.time > ?1 " +
            "AND delta.time < ?2 " +
            "AND delta.operation = 'REMOVE'")
    List<String> findDeltasByDateRemove(Timestamp startTime, Timestamp endTime);
}