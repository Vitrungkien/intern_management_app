package com.example.apidemo.repositories;

import com.example.apidemo.models.Intern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternRepository extends JpaRepository<Intern, Long> {
    List<Intern> FindByName(String name);
}
