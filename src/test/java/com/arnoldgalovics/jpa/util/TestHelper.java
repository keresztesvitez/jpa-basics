package com.arnoldgalovics.jpa.util;

import ch.qos.logback.classic.Level;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import java.util.*;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Component
public class TestHelper {
    private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void cleanUp() {
        executeWithoutSqlLogging(() -> {
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
            Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
            entities.forEach(this::cleanUpEntity);
            entityManager.clear();
        });
    }

    private void cleanUpEntity(EntityType<?> entityType) {
        String name = entityType.getName();
        entityManager.createQuery(format("DELETE FROM %s", name)).executeUpdate();
    }

    @Transactional
    public void dumpTables() {
        executeWithoutSqlLogging(() -> {
            Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
            entities.stream().sorted(comparing(EntityType::getName)).map(EntityType::getJavaType).map(this::getTableName).forEach(this::dumpTable);
        });
    }

    @SuppressWarnings("unchecked")
    public void dumpTable(String tableName) {
        Collection<String> attributeNames = getAttributeNames(tableName);
        List<Object[]> result = entityManager.createNativeQuery("SELECT " + getSelection(attributeNames) + " FROM " + tableName).getResultList();
        StringBuilder sb = new StringBuilder(getTableRow(attributeNames));
        sb.append(System.lineSeparator());
        for (Object[] row : result) {
            sb.append(getTableRow(asList(row)));
            sb.append(System.lineSeparator());
        }
        logger.info("Data for {}: {}{}", tableName, System.lineSeparator(), sb.toString());
    }

    private String getTableRow(Collection<? extends Object> values) {
        return StringUtils.join(values, " | ");
    }

    private String getSelection(Collection<String> attributeNames) {
        return StringUtils.join(attributeNames, ", ");
    }

    private String getTableName(Class<?> entityClass) {
        SessionFactory sessionFactory = ((Session) entityManager.getDelegate()).getSessionFactory();
        AbstractEntityPersister persister = (AbstractEntityPersister) sessionFactory.getClassMetadata(entityClass);
        return persister.getTableName().toLowerCase();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> getAttributeNames(String tableName) {
        List<Object[]> result = entityManager.createNativeQuery("SHOW COLUMNS FROM " + tableName).getResultList();
        return result.stream().map(objs -> (String) objs[0]).map(String::toLowerCase).collect(toList());
    }

    private void executeWithoutSqlLogging(Runnable r) {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener");
        Level originalLevel = logger.getLevel();
        logger.setLevel(Level.ERROR);
        r.run();
        logger.setLevel(originalLevel);
    }
}
