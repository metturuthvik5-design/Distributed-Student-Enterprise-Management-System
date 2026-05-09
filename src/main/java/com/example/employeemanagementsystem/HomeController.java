package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final EmployeeRepository employeeRepository;

    public HomeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // HOME PAGE
    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    // GET ALL EMPLOYEES
    @ResponseBody
    @GetMapping("/employees")
    public List<Employee> getEmployees() {

        return employeeRepository.findAll();
    }

    // GET EMPLOYEE BY ID
    @ResponseBody
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
    }

    // ADD EMPLOYEE
    @ResponseBody
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {

        return employeeRepository.save(employee);
    }

    // UPDATE EMPLOYEE
    @ResponseBody
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee updatedEmployee) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));

        employee.setName(updatedEmployee.getName());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());

        return employeeRepository.save(employee);
    }

    // DELETE EMPLOYEE
    @ResponseBody
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeRepository.deleteById(id);

        return "Employee Deleted Successfully!";
    }
}