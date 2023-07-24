package com.example.apidemo.controllers;

import com.example.apidemo.models.Intern;
import com.example.apidemo.models.ResponseObject;
import com.example.apidemo.repositories.InternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/intern")
public class InternController {
    @Autowired
    private InternRepository internRepository;

    @GetMapping("")
    List<Intern> getAllIntern() {
        return internRepository.findAll();
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateIntern(@RequestBody Intern newIntern, @PathVariable Long id) {
        Optional<Intern> optionalIntern = internRepository.findById(id);
        if (optionalIntern.isPresent()) {
            Intern existingIntern = optionalIntern.get();
            existingIntern.setName(newIntern.getName());
            existingIntern.setEmail(newIntern.getEmail());
            existingIntern.setPosition(newIntern.getPosition());
            existingIntern.setMentor_id(newIntern.getMentor_id());
            Intern updatedIntern = internRepository.save(existingIntern);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "update infor successfully", updatedIntern)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("error", "Intern not found with id: " + id, null)
            );
        }
    }

}
