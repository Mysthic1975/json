package com.example.json.service;

import com.example.json.entity.MyEntity;
import com.example.json.security.AESUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyEntityService {

    private static final Logger logger = LoggerFactory.getLogger(MyEntityService.class);
    private final AESUtil aesUtil;

    @PersistenceContext
    private EntityManager entityManager;

    public MyEntityService(AESUtil aesUtil) {
        this.aesUtil = aesUtil;
    }

    @Transactional
    public MyEntity createEntity(MyEntity entity) {
        try {
            MyEntity entityToSave = new MyEntity();
            entityToSave.setId(entity.getId());
            String encryptedName = aesUtil.encrypt(entity.getName());
            entityToSave.setName(encryptedName);
            entityManager.persist(entityToSave);
            return entityToSave;
        } catch (Exception e) {
            logger.error("Fehler beim Verschlüsseln und Speichern der Entität: {}", e.getMessage());
            throw new RuntimeException("Fehler beim Erstellen der Entität", e);
        }
    }

    @Transactional(readOnly = true)
    public MyEntity getEntity(Long id) {
        try {
            MyEntity entity = entityManager.find(MyEntity.class, id);
            if (entity != null) {
                String decryptedName = aesUtil.decrypt(entity.getName());
                entity.setName(decryptedName);
            }
            return entity;
        } catch (Exception e) {
            logger.error("Fehler beim Entschlüsseln der Entität: {}", e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Entität", e);
        }
    }

    @Transactional(readOnly = true)
    public Iterable<MyEntity> getAllEntities() {
        try {
            Iterable<MyEntity> entities = entityManager.createQuery("SELECT e FROM MyEntity e", MyEntity.class).getResultList();
            for (MyEntity entity : entities) {
                String decryptedName = aesUtil.decrypt(entity.getName());
                entity.setName(decryptedName);
            }
            return entities;
        } catch (Exception e) {
            logger.error("Fehler beim Entschlüsseln der Entitäten: {}", e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Entitäten", e);
        }
    }
}

