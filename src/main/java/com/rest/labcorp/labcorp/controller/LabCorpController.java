package com.rest.labcorp.labcorp.controller;


import com.rest.labcorp.labcorp.DO.Employee;
import com.rest.labcorp.labcorp.DO.EmployeeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/*controler has 2 urls
URL 1 : /work/{EmployeeID}/{noOfDaysWorked}  -> Example : work/1/250
URL 2 : /takeVacation/{EmployeeID}/{noOfVacationDays} -> Example : takeVacation/2/10

USING POSTMAN :
 http://localhost:8080/work/1/250 : updates employee 1, with no of worked days as 250 and vacation accumulated as 10.
 http://localhost:8080/takeVacation/2/5 : updates employee 2, with no of worked days as 255 and vacation accumulated as 5.
*/
@RestController
public class LabCorpController {
    private static Logger logger = LoggerFactory.getLogger(LabCorpController.class);
    @Value("${employee.min.numberOfDaysToWork}") // Injecting the values from application.properties
    private Integer minNumberOfDaysToWork;
    @Value("${employee.max.numberOfDaysToWork}")
    private Integer maxNumberOfDaysToWork;

    @Value("${employee.hourly.vacationDays}")
    private Double hourlyEmpVacationDays;
    @Value("${employee.salaried.vacationDays}")
    private Double salariedEmpVacationDays;
    @Value("${employee.manager.vacationDays}")
    private Double ManagerEmpVacationDays;

    private List<Employee> employeeList = Arrays.asList(
            new Employee(1, "Ramesh", EmployeeType.HOURLY, 0.0, 0.0),
            new Employee(2, "Gukesh", EmployeeType.SALARIED, 0.0, 0.0),
            new Employee(3, "Atharv", EmployeeType.MANAGER, 0.0, 0.0),
            new Employee(4, "Rajesh", EmployeeType.HOURLY, 0.0, 0.0),
            new Employee(5, "Siddharth", EmployeeType.SALARIED, 0.0, 0.0),
            new Employee(6, "Arun", EmployeeType.MANAGER, 0.0, 0.0),
            new Employee(7, "Aishwarya", EmployeeType.HOURLY, 0.0, 0.0),
            new Employee(8, "Seema", EmployeeType.SALARIED, 0.0, 0.0),
            new Employee(9, "Chinnu", EmployeeType.MANAGER, 0.0, 0.0),
            new Employee(10, "Pranit", EmployeeType.HOURLY, 0.0, 0.0)
    );

    @GetMapping("/work/{empId}/{noOfDaysWorked}")
    public Object work(@PathVariable("empId") Integer empId,
                       @PathVariable("noOfDaysWorked") Double noOfDaysWorked) {
        logger.info("------start of work method-----");
        boolean flag = false;
        Double noOfVacationDays = maxNumberOfDaysToWork - noOfDaysWorked;
        for (Employee emp : employeeList) {
            if (emp.getEmployeeId() == empId) {
                flag = true;
                if (noOfDaysWorked < minNumberOfDaysToWork || noOfDaysWorked > maxNumberOfDaysToWork) {
                    return ResponseEntity.badRequest().body("Error : noOfDaysWorked value should range from " + minNumberOfDaysToWork +
                            " to " + maxNumberOfDaysToWork);
                }
                emp.setVacationDaysAccumulated(noOfVacationDays);
                emp.setNoOfDaysWorked(noOfDaysWorked);
                System.out.println("Employee " + emp.getEmployeeId() + "vacation days accumulated :" + noOfVacationDays);
                System.out.println("Employee " + emp.getEmployeeId() + "no of days worked :" + noOfDaysWorked);
                return ResponseEntity.ok(employeeList);
            }
        }
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/takeVacation/{empId}/{noOfVacationDays}")
    public Object takeVacation(@PathVariable("empId") Integer empId,
                               @PathVariable("noOfVacationDays") Double noOfVacationDays) {
        boolean flag = false;
        Double vacationDays = 0.0;
        for (Employee emp : employeeList) {
            if (emp.getEmployeeId() == empId) {
                flag = true;
                if(noOfVacationDays < 0){
                    return ResponseEntity.badRequest().body("Error : vacation " +
                            "days should not be negative " + hourlyEmpVacationDays);
                }
                if (emp.getEmployeeType().equals(EmployeeType.HOURLY)) {
                    if (noOfVacationDays > hourlyEmpVacationDays || noOfVacationDays < 0) {
                        return ResponseEntity.badRequest().body("Error : For Employee " + empId + " max available vacation " +
                                "days are " + hourlyEmpVacationDays);
                    }
                } else if (emp.getEmployeeType().equals(EmployeeType.SALARIED)) {
                    if (noOfVacationDays > salariedEmpVacationDays || noOfVacationDays < 0) {
                        return ResponseEntity.badRequest().body("Error : For Employee " + empId + " max available vacation " +
                                "days are " + salariedEmpVacationDays);
                    }
                } else {
                    if (noOfVacationDays > ManagerEmpVacationDays || noOfVacationDays < 0) {
                        return ResponseEntity.badRequest().body("Error : For Employee " + empId + " max available vacation " +
                                "days are " + ManagerEmpVacationDays);
                    }
                }
                Double noOfDaysWorked = maxNumberOfDaysToWork - noOfVacationDays;
                emp.setVacationDaysAccumulated(noOfVacationDays);
                emp.setNoOfDaysWorked(noOfDaysWorked);
                System.out.println("Employee " + emp.getEmployeeId() + "vacation days accumulated :" + noOfVacationDays);
                System.out.println("Employee " + emp.getEmployeeId() + "no of days worked :" + noOfDaysWorked);
                return ResponseEntity.ok(employeeList);
            }
        }
        if(flag)
            return ResponseEntity.ok(employeeList);
        else
            return ResponseEntity.badRequest().body("Error : Given EmployeeId not present");
    }
}
