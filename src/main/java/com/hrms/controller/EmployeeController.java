package com.hrms.controller;

import com.hrms.exception.ResourceNotFoundException;
import com.hrms.model.Employee;
import com.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository empRepository;

    @Autowired
    public EmployeeController(EmployeeRepository empRepository) {
        this.empRepository = empRepository;
    }

    // to add employee
    @PostMapping("/employees")
    public void addEmployee(@RequestBody Employee employee) {
     empRepository.save(employee);
    }

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return empRepository.findAll();
    }

    //get employee by id
    // @RequestMapping(value = "/employees/{id}")
    //public Optional<Employee> getEmpById(@PathVariable Long id) {
    //    return empRepository.findById(id);
    // }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = empRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }
    
    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = empRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setMiddleName(employeeDetails.getMiddleName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setSalary(employeeDetails.getSalary());

        Employee updatedEmployee = empRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

}
