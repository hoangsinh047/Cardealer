package com.hdsinh.cardealer.repository.Customer;

import com.hdsinh.cardealer.entities.Customer;
import com.hdsinh.cardealer.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class CustomerRepositoryImpl implements CustomerRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Customer> loadAll(String search, Integer start, Integer total) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    c.ID id, ");
        sql.append("    c.NAME name, ");
        sql.append("    c.CODE code, ");
        sql.append("    c.PHONE phone, ");
        sql.append("    c.EMAIL email, ");
        sql.append("    c.CONTENT content, ");
        sql.append("    c.CREATED_DATE createdDate ");
        sql.append("FROM CUSTOMERS c ");
        sql.append("WHERE 1=1 ");

        if (search != null && !search.isEmpty()) {
            sql.append(" and e.NAME like '%").append(search.trim()).append("%'");
        }

        if (total != null && total > 0) {
            sql.append(" LIMIT ").append(total);
        }

        if (start != null && start >= 0) {
            sql.append(" OFFSET ").append(start);
        }


        // Khai báo và gán giá trị cho res
        Query query = entityManager.unwrap(Session.class).createNativeQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("code", StandardBasicTypes.STRING)
                .addScalar("phone", StandardBasicTypes.INTEGER)
                .addScalar("email", StandardBasicTypes.STRING)
                .addScalar("content", StandardBasicTypes.STRING)
                .addScalar("createdDate", StandardBasicTypes.LOCAL_DATE_TIME)
                .setResultTransformer(Transformers.aliasToBean(Customer.class));

        return query.getResultList();
    }

    @Override
    public Long countAll(String search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM CUSTOMERS c WHERE 1=1");

        if (!StringUtils.isEmpty(search)) {
            sql.append(" AND c.NAME LIKE :search");
        }

        jakarta.persistence.Query query = entityManager.createNativeQuery(sql.toString());

        if (!StringUtils.isEmpty(search)) {
            query.setParameter("search", "%" + search + "%");
        }

        Object result = query.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).longValue();
        }
        return 0L;
    }
}
