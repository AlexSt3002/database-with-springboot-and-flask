package com.example.demo.repository;

import com.example.demo.model.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicRepository extends JpaRepository<Medic, Long> {
    List<Medic> findBySpecializareContainingIgnoreCase(String specializare);
}