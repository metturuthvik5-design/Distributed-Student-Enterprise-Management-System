package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Employee;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.List;

@Service
public class ExcelService {

    public ByteArrayInputStream exportEmployees(
            List<Employee> employees) {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet =
                    workbook.createSheet("Employees");

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");

            headerRow.createCell(1).setCellValue("Name");

            headerRow.createCell(2).setCellValue("Department");

            headerRow.createCell(3).setCellValue("Salary");

            int rowNum = 1;

            for (Employee employee : employees) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(employee.getId());

                row.createCell(1)
                        .setCellValue(employee.getName());

                row.createCell(2)
                        .setCellValue(employee.getDepartment());

                row.createCell(3)
                        .setCellValue(employee.getSalary());
            }

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            workbook.write(out);

            return new ByteArrayInputStream(
                    out.toByteArray()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to export Excel file"
            );
        }
    }
}
