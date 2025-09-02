package com.example.microhabittracker.controller;

import com.example.microhabittracker.model.HabitLog;
import com.example.microhabittracker.service.HabitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits/{habitId}/logs")
public class HabitLogController {
    private final HabitLogService habitLogService;
    @Autowired
    public HabitLogController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;
    }

    @GetMapping
    public List<HabitLog> getHabitLogs(@PathVariable long habitId) {
        return habitLogService.getLogsByHabitId(habitId);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<HabitLog> getLogById(@PathVariable Long habitId, @PathVariable Long logId) {
        return habitLogService.getLogById(logId)
                .filter(log -> log.getHabit().getId().equals(habitId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createLog(@PathVariable long habitId, @RequestBody HabitLog habitLog) {
        try {
            HabitLog createdLog = habitLogService.createHabitLog(habitId, habitLog);
            return ResponseEntity.ok(createdLog);
        } catch (IllegalArgumentException e) {
            // e.g., habit not found
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // unexpected errors
            return ResponseEntity.status(500).body("Internal server error");
        }
    }


    @PutMapping("/{logId}")
    public ResponseEntity<HabitLog> updateLog(@PathVariable Long habitId, @PathVariable Long logId, @RequestBody HabitLog habitLog) {
        try {
            HabitLog updatedLog = habitLogService.updateHabitLog(logId, habitLog);
            if (!updatedLog.getHabit().getId().equals(habitId)) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(updatedLog);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long habitId, @PathVariable Long logId) {
        habitLogService.deleteHabitLog(logId);
        return ResponseEntity.noContent().build();
    }
}
