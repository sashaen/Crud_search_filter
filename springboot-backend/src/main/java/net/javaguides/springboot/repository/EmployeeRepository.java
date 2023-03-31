package net.javaguides.springboot.repository;
import net.javaguides.springboot.model.Employee;
import org.hibernate.Interceptor;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    // all crud database methods

    List<Employee> findAll(Specification<Employee> specification);
    @Procedure(name = "employee.details")
    public Map<String, Object> fetchEmployeeDetailsByIdProcedure(Integer emp_id);
    @Procedure(value = "get_employee_count")
    public String fetchEmployeeCountProcedure();
    @Procedure(value = "get_emailId_of_employee")
    public String fetchEmployeeEmailIdProcedure(Integer id);

    @Procedure(value = "getCountOfOneJob")
    public Integer fetchOneJobCount(String job_position);

    @Procedure(value = "getEmployeeFullNameByIdSP")
    public String fetchEmployeeFullname(Integer id);

    @Procedure (value = "getEmaildomain")
    public String fetchEmailDomain( Integer id);

//    @Formula( value = "getEmployeeEmailByName")
//    public String fetchEmployeeEmail(String firstname, String lastname);

}
