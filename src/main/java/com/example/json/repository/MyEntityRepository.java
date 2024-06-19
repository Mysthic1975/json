package com.example.json.repository;

import com.example.json.entity.MyEntity;
import org.springframework.data.repository.CrudRepository;

public interface MyEntityRepository extends CrudRepository<MyEntity, Long> {
}
