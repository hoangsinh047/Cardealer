package com.hdsinh.cardealer.repository.Manufacturer;

import com.hdsinh.cardealer.entities.Manufacturer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import java.util.List;

public class ManufacturerRepositoryImpl implements ManufacturerRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Manufacturer> loadAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    m.ID id, ");
        sql.append("    m.NAME name ");
        sql.append("FROM MANUFACTURER m ");
        sql.append("WHERE 1=1 ");

        // Khai báo và gán giá trị cho res
        Query query = entityManager.unwrap(Session.class).createNativeQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(Manufacturer.class));

        return query.getResultList();
    }
}
