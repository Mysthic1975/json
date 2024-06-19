package com.example.json.controller;

import com.example.json.entity.MyEntity;
import com.example.json.repository.MyEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MyController {

    private final MyEntityRepository repository;

    public MyController(MyEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("entities", repository.findAll());
        return "index";
    }

    @PostMapping("/create")
    public String createEntity(@RequestParam String name) {
        MyEntity entity = new MyEntity();
        entity.setName(name);
        repository.save(entity);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editEntity(@PathVariable Long id, Model model) {
        Optional<MyEntity> entity = repository.findById(id);
        entity.ifPresent(myEntity -> model.addAttribute("entity", myEntity));
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateEntity(@PathVariable Long id, @RequestParam String name) {
        Optional<MyEntity> entity = repository.findById(id);
        entity.ifPresent(myEntity -> {
            myEntity.setName(name);
            repository.save(myEntity);
        });
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
