/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphpayroll;

/**
 *
 * @author Sid
 */
abstract class Deduction {
    private String name;   // Name of the deduction
    
    // Constructor to initialize deduction name
    public Deduction(String name) {
        this.name = name;   // Set deduction name
    }

    // Abstract method to calculate deduction amount
    public abstract double calculate(double amount);

    // Getter for deduction name
    public String getName() {
        return name;   // Return name of deduction
    }

    // Setter for deduction name
    public void setName(String name) {
        this.name = name;   // Set the name of the deduction
    }
}

