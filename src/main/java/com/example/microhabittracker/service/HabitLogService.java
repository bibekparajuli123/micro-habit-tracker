package com.example.microhabittracker.service;

import com.example.microhabittracker.model.Habit;
import com.example.microhabittracker.model.HabitLog;
import com.example.microhabittracker.repository.HabitLogRepository;
import com.example.microhabittracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitLogService {
    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;

    @Autowired
    public HabitLogService(HabitLogRepository habitLogRepository, HabitRepository habitRepository) {
        this.habitLogRepository = habitLogRepository;
        this.habitRepository = habitRepository;
    }

    public List<HabitLog> getLogsByHabitId(Long habitId) {
        return habitLogRepository.findByHabitId(habitId);
    }

    public Optional<HabitLog> getLogById(Long id) {
        return habitLogRepository.findById(id);
    }

    public HabitLog createHabitLog(Long habitId, HabitLog habitLog) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found with id " + habitId));
        habitLog.setHabit(habit);
        return habitLogRepository.save(habitLog);
    }

    public HabitLog updateHabitLog(Long id, HabitLog updatedLog) {
        return habitLogRepository.findById(id).map(log -> {
            log.setLogDate(updatedLog.getLogDate());
            log.setStatus(updatedLog.isStatus());
            log.setNotes(updatedLog.getNotes());
            return habitLogRepository.save(log);
        }).orElseThrow(() -> new RuntimeException("HabitLog not found with id " + id));
    }

    public void deleteHabitLog(Long id) {
        habitLogRepository.deleteById(id);
    }
}
