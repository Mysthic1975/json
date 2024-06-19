package com.example.json.controller;

import com.example.json.entity.MyEntity;
import com.example.json.service.MyEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final MyEntityService service;

    public MyRestController(MyEntityService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<MyEntity> createEntity(@RequestBody MyEntity entity) {
        try {
            return new ResponseEntity<>(service.createEntity(entity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MyEntity> getEntity(@PathVariable Long id) {
        try {
            MyEntity entity = service.getEntity(id);
            if (entity != null) {
                return new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<Iterable<MyEntity>> getAllEntities() {
        try {
            return new ResponseEntity<>(service.getAllEntities(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
