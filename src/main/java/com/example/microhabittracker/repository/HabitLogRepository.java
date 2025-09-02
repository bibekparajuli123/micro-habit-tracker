package com.example.microhabittracker.repository;

import com.example.microhabittracker.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {

    List<HabitLog> findByHabitId(Long habitId);

    List<HabitLog> findByHabitIdAndLogDate(Long habitId, LocalDate logDate);

    List<HabitLog> findByHabitIdAndLogDateBetween(Long habitId, LocalDate startDate, LocalDate endDate);
}
