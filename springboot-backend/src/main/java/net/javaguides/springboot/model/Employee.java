package net.javaguides.springboot.model;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")

@NamedStoredProcedureQuery(
        name = "employee.details",
        procedureName = "getEmployeeDetailsById",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "emp_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "emp_firstname", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "emp_lastname", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "emp_emailid", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "emp_jobposition", type = String.class)

        }

)

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "First_name")
    private String firstName;
    @Column(name = "Last_name")
    private String lastName;
    @Column(name = "email_id")
    @Formula("getEmployeeEmailByName(first_name, last_name)")
    private String emailId;

    @Column(name = "job_position")
    private String jobPosition;

    public void setId(long id) {
        if (this.id == 0) {
            // Only set the id for new entities during POST requests
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            if (request.getMethod().equals("POST")) {
                this.id = id;
            } else {
                // For GET requests, do nothing
                return;
            }
        } else {
            // Throw an exception or log an error message
            throw new IllegalStateException("Cannot change ID of existing entity.");
        }
    }

}
