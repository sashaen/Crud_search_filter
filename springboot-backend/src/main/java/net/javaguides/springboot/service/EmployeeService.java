package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
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

    public Map<String, Object> fetchEmployeeDetailsByIdProcedure( Integer id){
        return employeeRepository.fetchEmployeeDetailsByIdProcedure(id);
    }

    public String fetchEmployeeCountProcedure(){
        return employeeRepository.fetchEmployeeCountProcedure();
    }

    public  String fetchEmployeeEmailIdProcedure(@PathVariable Integer id){
        return employeeRepository.fetchEmployeeEmailIdProcedure(id);
    }

//    public String fetchEmployeeEmail(@RequestParam String firstName, @RequestParam String lastName) {
//        return employeeRepository.fetchEmployeeEmail(firstName, lastName);
//    }

    @Transactional
    public Integer fetchOneJobCount( @PathVariable String job_position){
        return employeeRepository.fetchOneJobCount(job_position);
    }

    public String fetchEmployeeFullname (@PathVariable Integer id){
        return employeeRepository.fetchEmployeeFullname( id);
    }

    public String fetchEmailDomain (@PathVariable Integer id){
        return employeeRepository.fetchEmailDomain(id);
    }

}
