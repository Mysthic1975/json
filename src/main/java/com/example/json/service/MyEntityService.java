package com.example.json.service;

import com.example.json.entity.MyEntity;
import com.example.json.repository.MyEntityRepository;
import com.example.json.security.AESUtil;
import org.springframework.stereotype.Service;

@Service
public class MyEntityService {

    private final MyEntityRepository repository;
    private final AESUtil aesUtil;

    public MyEntityService(MyEntityRepository repository, AESUtil aesUtil) {
        this.repository = repository;
        this.aesUtil = aesUtil;
    }

    public MyEntity createEntity(MyEntity entity) throws Exception {
        String encryptedName = aesUtil.encrypt(entity.getName());
        entity.setName(encryptedName);
        return repository.save(entity);
    }

    public MyEntity getEntity(Long id) throws Exception {
        MyEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            String decryptedName = aesUtil.decrypt(entity.getName());
            entity.setName(decryptedName);
        }
        return entity;
    }

    public Iterable<MyEntity> getAllEntities() throws Exception {
        Iterable<MyEntity> entities = repository.findAll();
        for (MyEntity entity : entities) {
            String decryptedName = aesUtil.decrypt(entity.getName());
            entity.setName(decryptedName);
        }
        return entities;
    }
}
