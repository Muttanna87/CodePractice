package com.rest.labcorp.labcorp.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Employee {
    private Integer employeeId;
    private String name;
    private EmployeeType employeeType;

    private Double noOfDaysWorked;
    private Double vacationDaysAccumulated;

    public Employee(int employeeId,
                    String name,
                    EmployeeType employeeType,
                    Double noOfDaysWorked,
                    Double vacationDaysAccumulated) {
        this.employeeId = employeeId;
        this.employeeType = employeeType;
        this.noOfDaysWorked = noOfDaysWorked;
        this.vacationDaysAccumulated = vacationDaysAccumulated;
        this.name = name;
    }
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public Double getNoOfDaysWorked() {
        return noOfDaysWorked;
    }

    public void setNoOfDaysWorked(Double noOfDaysWorked) {
        this.noOfDaysWorked = noOfDaysWorked;
    }

    public Double getVacationDaysAccumulated() {
        return vacationDaysAccumulated;
    }

    public void setVacationDaysAccumulated(Double vacationDaysAccumulated) {
        this.vacationDaysAccumulated = vacationDaysAccumulated;
    }


}
