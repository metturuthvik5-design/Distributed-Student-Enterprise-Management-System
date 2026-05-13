package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.entity.Employee;

import com.example.employeemanagementsystem.repository.EmployeeRepository;

import com.example.employeemanagementsystem.service.ExcelService;

import com.example.employeemanagementsystem.service.PdfService;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.core.io.InputStreamResource;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;

@Controller
public class HomeController {

    private final EmployeeRepository employeeRepository;

    private final ExcelService excelService;

    private final PdfService pdfService;

    public HomeController(EmployeeRepository employeeRepository,
                          ExcelService excelService,
                          PdfService pdfService) {

        this.employeeRepository = employeeRepository;

        this.excelService = excelService;

        this.pdfService = pdfService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/home")
    public String homePage() {

        return "index";
    }

    @ResponseBody
    @GetMapping("/employees")

    public Page<Employee> getEmployees(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy) {

        Pageable pageable = PageRequest.of(

                page,

                size,

                Sort.by(sortBy)
        );

        return employeeRepository.findAll(pageable);
    }

    @ResponseBody
    @PostMapping("/employees")

    public Employee addEmployee(
            @RequestBody Employee employee) {

        return employeeRepository.save(employee);
    }

    @ResponseBody
    @PutMapping("/employees/{id}")

    public Employee updateEmployee(

            @PathVariable Long id,

            @RequestBody Employee updatedEmployee) {

        Employee employee =
                employeeRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"
                                ));

        employee.setName(updatedEmployee.getName());

        employee.setDepartment(
                updatedEmployee.getDepartment()
        );

        employee.setSalary(
                updatedEmployee.getSalary()
        );

        return employeeRepository.save(employee);
    }

    @ResponseBody
    @DeleteMapping("/employees/{id}")

    public String deleteEmployee(
            @PathVariable Long id) {

        employeeRepository.deleteById(id);

        return "Employee Deleted Successfully!";
    }

    @ResponseBody
    @GetMapping("/employees/export")

    public ResponseEntity<InputStreamResource>
    exportEmployees() {

        var employees =
                employeeRepository.findAll();

        var excelFile =
                excelService.exportEmployees(employees);

        InputStreamResource resource =
                new InputStreamResource(excelFile);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,

                        "attachment; filename=employees.xlsx"
                )

                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )

                .body(resource);
    }

    @ResponseBody
    @GetMapping("/employees/pdf")

    public ResponseEntity<InputStreamResource>
    exportPdf() {

        var employees =
                employeeRepository.findAll();

        var pdfFile =
                pdfService.exportEmployees(employees);

        InputStreamResource resource =
                new InputStreamResource(pdfFile);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,

                        "attachment; filename=employees.pdf"
                )

                .contentType(
                        MediaType.APPLICATION_PDF
                )

                .body(resource);
    }
}