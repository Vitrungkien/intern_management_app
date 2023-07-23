package com.example.apidemo.controllers;

import com.example.apidemo.models.Intern;
import com.example.apidemo.repositories.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/intern")
public class InternController {
    @Autowired
    private InternRepository internRepository;

    @GetMapping("")
    List<Intern> getAllIntern() {
        return internRepository.findAll();
    }
}
