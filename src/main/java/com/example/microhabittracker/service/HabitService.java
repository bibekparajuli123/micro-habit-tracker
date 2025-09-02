package com.example.microhabittracker.service;

import com.example.microhabittracker.model.Habit;
import com.example.microhabittracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    @Autowired
    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Optional<Habit> getHabitById(Long id) {
        return habitRepository.findById(id);
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, Habit updatedHabit) {
        return habitRepository.findById(id).map(habit -> {
            habit.setName(updatedHabit.getName());
            habit.setDescription(updatedHabit.getDescription());
            return habitRepository.save(habit);
        }).orElseThrow(() -> new RuntimeException("Habit not found with id " + id));
    }

    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }
}
