/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphpayroll;

/**
 *
 * @author Sid
 */
public class Employee {
    String employeeNumber;   // Employee ID
    String fullName;         // Full name
    String birthday;         // Date of birth
    double basicSalary;      // Monthly salary
    double hourlyRate;       // Hourly wage

    // Constructor to initialize employee
    public Employee(String employeeNumber, String fullName, String birthday,
                    double basicSalary, double hourlyRate) {
        this.employeeNumber = employeeNumber;   // Set ID
        this.fullName = fullName;               // Set name
        this.birthday = birthday;               // Set birthday
        this.basicSalary = basicSalary;       // Set salary
        this.hourlyRate = hourlyRate;         // Set hourly rate
    }

    // Getter methods
    public String getEmployeeNumber() { return employeeNumber; }
    public String getFullName() { return fullName; }
    public String getBirthday() { return birthday; }
    public double getBasicSalary() { return basicSalary; }
    public double getHourlyRate() { return hourlyRate; }

    // Setter methods
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    } 
}
