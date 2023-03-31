package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService ;

    // build create employee rest api and request body converts json into java obj and search and filter functionality
    @GetMapping
    public List<Employee> list( @RequestParam(value = "firstName", required = false)String firstName,
                                @RequestParam(value = "lastName", required = false)String lastName,
                                @RequestParam(value = "emailId", required = false)String emailId,
                                @RequestParam(value = "jobPosition", required = false)String jobPosition,
                                @RequestParam(value = "search", required = false) String searchstring,
                                @RequestHeader("x-query") String xQueryHeader){
        Specification<Employee> specification = EmployeeService.getService(firstName, lastName, emailId, jobPosition, searchstring);

        return employeeRepository.findAll(specification);
    }
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){

        return employeeRepository.save(employee);
    }

    //build get employee by rest api
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById( @PathVariable Long id){
       Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
       return ResponseEntity.ok(employee);
    }

    //build update employee rest api
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee( @PathVariable Long id, @RequestBody Employee employeeDetails){
       Employee updateEmployee = employeeRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Employee not exist with id: " + id));
       updateEmployee.setFirstName(employeeDetails.getFirstName());
       updateEmployee.setLastName(employeeDetails.getLastName());
       updateEmployee.setEmailId(employeeDetails.getEmailId());
       updateEmployee.setJobPosition(employeeDetails.getJobPosition());

       employeeRepository.save(updateEmployee);

       return ResponseEntity.ok(updateEmployee);
    }

    // build delete employee rest api
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee( @PathVariable Long id){
       Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id:" + id));

       employeeRepository.delete(employee);

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping("/detailsById/{id}")
    public Map<String,Object> fetchEmployeeDetailsByIdProcedure( @PathVariable Integer id){
        return employeeService.fetchEmployeeDetailsByIdProcedure(id);
    }

    @RequestMapping("/empCount")
    public String fetchEmployeeCountProcedure(){
        return employeeService.fetchEmployeeCountProcedure();
    }

    @RequestMapping("/emailId/{id}")
    public String fetchEmployeeEmailIdProcedure(@PathVariable Integer id){
        return employeeService.fetchEmployeeEmailIdProcedure(id);
    }

    @RequestMapping("/one-job-count/{job_position}")
    public Integer fetchOneJobCount(@PathVariable String jobPosition){
        System.out.println(jobPosition);
        return employeeService.fetchOneJobCount( jobPosition);
    }

    @RequestMapping("/status/{id}")
    public String fetchEmployeeFullname( @PathVariable Integer id){
        return employeeService.fetchEmployeeFullname(id);
    }


    @RequestMapping("/domain/{id}")
    public String fetchEmailDomain (@PathVariable Integer id){
        return employeeService.fetchEmailDomain(id);
    }
//    @RequestMapping(value = "/get-email", method = RequestMethod.GET)
//    public String getEmployeeEmail(@RequestParam String firstName, @RequestParam String lastName) {
//        String email = employeeRepository.fetchEmployeeEmail(firstName, lastName);
//        return email;
//    }
}