package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {

    private final EmployeeRepository employeeRepository;

    public HomeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Employee Management System Running Successfully!";
    }

    // GET ALL EMPLOYEES
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    // ADD EMPLOYEE
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    // UPDATE EMPLOYEE
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee updatedEmployee) {

        Employee employee = employeeRepository.findById(id).orElseThrow();

        employee.setName(updatedEmployee.getName());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());

        return employeeRepository.save(employee);
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeRepository.deleteById(id);

        return "Employee Deleted Successfully!";
    }
}