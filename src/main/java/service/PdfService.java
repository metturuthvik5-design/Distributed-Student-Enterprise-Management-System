package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Employee;

import com.itextpdf.text.Document;

import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.List;

@Service
public class PdfService {

    public ByteArrayInputStream exportEmployees(
            List<Employee> employees) {

        Document document = new Document();

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(
                    new Paragraph(
                            "Employee Management Report"
                    )
            );

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);

            table.addCell("ID");

            table.addCell("Name");

            table.addCell("Department");

            table.addCell("Salary");

            for (Employee employee : employees) {

                table.addCell(
                        String.valueOf(employee.getId())
                );

                table.addCell(employee.getName());

                table.addCell(employee.getDepartment());

                table.addCell(
                        String.valueOf(employee.getSalary())
                );
            }

            document.add(table);

            document.close();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to export PDF"
            );
        }

        return new ByteArrayInputStream(
                out.toByteArray()
        );
    }
}
