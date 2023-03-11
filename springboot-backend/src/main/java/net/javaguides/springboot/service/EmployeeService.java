package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public static Specification<Employee> getService(String firstName, String lastName, String emailId, String jobPosition, String searchString){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), firstName + "%"));
            }

            if (lastName != null) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), lastName + "%"));
            }

            if (emailId != null) {
                predicates.add(criteriaBuilder.like(root.get("emailId"), "%" + emailId + "%"));
            }

            if (jobPosition != null) {
                predicates.add(criteriaBuilder.like(root.get("jobPosition"), "%" + jobPosition + "%"));
            }

            if(searchString !=null){
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + searchString + "%"));
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + searchString + "%"));
                predicates.add(criteriaBuilder.like(root.get("emailId"), "%" + searchString + "%"));
                predicates.add(criteriaBuilder.like(root.get("jobPosition"), "%" + searchString + "%"));
                // use the OR operator to combine all predicates
                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
    }
}
