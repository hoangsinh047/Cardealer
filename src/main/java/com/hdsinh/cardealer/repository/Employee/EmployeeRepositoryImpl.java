package com.hdsinh.cardealer.repository.Employee;

import com.hdsinh.cardealer.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Employee> loadAll(String search, Integer start, Integer total) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    e.ID id, ");
        sql.append("    e.NAME name, ");
        sql.append("    e.CODE code, ");
        sql.append("    e.BIRTH birth, ");
        sql.append("    e.SEX sex, ");
        sql.append("    e.PHONE phone, ");
        sql.append("    e.EMAIL email, ");
        sql.append("    e.DEGREE degree, ");
        sql.append("    e.POSITION position, ");
        sql.append("    e.ADDRESS address, ");
        sql.append("    e.CREATED_DATE createdDate ");
        sql.append("FROM EMPLOYEE e ");
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
                .addScalar("birth", StandardBasicTypes.DATE)
                .addScalar("sex", StandardBasicTypes.STRING)
                .addScalar("phone", StandardBasicTypes.INTEGER)
                .addScalar("email", StandardBasicTypes.STRING)
                .addScalar("degree", StandardBasicTypes.STRING)
                .addScalar("position", StandardBasicTypes.STRING)
                .addScalar("address", StandardBasicTypes.STRING)
                .addScalar("createdDate", StandardBasicTypes.LOCAL_DATE_TIME)
                .setResultTransformer(Transformers.aliasToBean(Employee.class));

        return query.getResultList();
    }

    @Override
    public Long countAll(String search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM EMPLOYEE e WHERE 1=1");

        if (!StringUtils.isEmpty(search)) {
            sql.append(" AND e.NAME LIKE :search");
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
