/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphpayroll;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sid
 */

public class PayrollCalculator {
    // SSS-related fields and logic
    private double sssRate = 0.045; // Default SSS rate

    public double getSssRate() {
        return sssRate;
    }

    public void setSssRate(double sssRate) {
        this.sssRate = sssRate;
    }

    public double calculateSSS(double basicSalary) {
        if (basicSalary < 3250) return 135.0;
        if (basicSalary >= 24750) return 1125.0;
        double steps = Math.floor((basicSalary - 3250) / 500);
        return 135.0 + (steps + 1) * 22.50;
    }

    // PhilHealth-related fields and logic
    private double philHealthRate = 0.015; // Default PhilHealth rate

    public double getPhilHealthRate() {
        return philHealthRate;
    }

    public void setPhilHealthRate(double philHealthRate) {
        this.philHealthRate = philHealthRate;
    }

    public double calculatePhilHealth(double basicSalary) {
        if (basicSalary <= 10000) return 150.0;
        if (basicSalary < 60000) return basicSalary * this.philHealthRate;
        return 900.0;
    }

    // Pag-IBIG-related fields and logic

    public double calculatePagIBIG(double basicSalary) {
        if (basicSalary >= 1000 && basicSalary <= 1500)
            return basicSalary * 0.01;
        if (basicSalary > 1500)
            return basicSalary * 0.02;
        return 0.0;
    }

    // Calculate total deductions (now calling the individual calculation methods)
    public double calculateTotalDeductions(double basicSalary) {
        return calculateSSS(basicSalary) + calculatePhilHealth(basicSalary) + calculatePagIBIG(basicSalary);
    }

    // Calculate withholding tax (remains the same)
    public static double calculateWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832) return 0.0;
        if (taxableIncome <= 33333) return (taxableIncome - 20833) * 0.20;
        if (taxableIncome <= 66667) return 2500 + (taxableIncome - 33333) * 0.25;
        if (taxableIncome <= 166667) return 10833 + (taxableIncome - 66667) * 0.30;
        if (taxableIncome <= 666667) return 40833.33 + (taxableIncome - 166667) * 0.32;
        return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
}
