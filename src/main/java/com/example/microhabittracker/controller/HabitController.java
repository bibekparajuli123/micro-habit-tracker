package com.example.microhabittracker.controller;

import com.example.microhabittracker.model.Habit;
import com.example.microhabittracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;
    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }
    //Get List of all Habits
    @GetMapping
    public List<Habit> getAllHabits() {
        return habitService.getAllHabits();
    }

    //Get a Specific Habit by Id
    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabitById(@PathVariable long id) {
        return habitService.getHabitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Create a new Habit
    @PostMapping
    public Habit  createHabit(@RequestBody Habit habit) {
        return habitService.createHabit(habit);
    }

    //Update the existing Habit
    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabitById(@PathVariable long id, @RequestBody Habit habit) {
        try{
            Habit updatedHabit = habitService.updateHabit(id, habit);
            return ResponseEntity.ok(updatedHabit);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //To Delete Specific Habit using Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Habit> deleteHabitById(@PathVariable long id) {
            habitService.deleteHabit(id);
            return ResponseEntity.notFound().build();
    }
}
