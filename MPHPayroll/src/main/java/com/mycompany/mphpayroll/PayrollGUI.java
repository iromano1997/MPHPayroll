/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphpayroll;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Sid
 */

public class PayrollGUI {
    private Map<String, Employee> employees;
    private AttendanceRecord attendanceRecord;
    private Menu payrollMenu; // Instance of the Menu class

    public PayrollGUI() {
        // Load data and create Menu instance in the constructor
        loadData();
        initializeUI();
    }

    private void loadData() {
        try {
            employees = DataLoader.loadEmployees("C:\\Users\\Sid\\Desktop\\Java Projects\\MPHPayroll\\src\\motorph_employee_data_complete.csv"); // Replace with your actual path
            attendanceRecord = new AttendanceRecord();
            DataLoader.loadAttendance(attendanceRecord, "C:\\Users\\Sid\\Desktop\\Java Projects\\MPHPayroll\\src\\attendance_record.csv"); // Replace with your actual path
            payrollMenu = new Menu(employees, attendanceRecord); // Create the Menu instance
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Exit if data loading fails
        }
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Welcome to MotorPH Payroll Hub!");
        frame.setLayout(null); // Using null layout for simplicity, consider Layout Managers

        JLabel label0 = new JLabel("The Filipino's Choice!");
        label0.setBounds(125, 20, 200, 40);

        JLabel label1 = new JLabel("Choose from the menu below: ");
        label1.setBounds(40, 60, 200, 40);

        JButton button1 = new JButton("1. Display Employee Information");
        button1.setBounds(55, 110, 260, 30);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNumber = JOptionPane.showInputDialog(frame, "Enter Employee Number:");
                if (empNumber != null && employees.containsKey(empNumber)) {
                    Employee emp = employees.get(empNumber);
                    JOptionPane.showMessageDialog(frame,
                            "Employee Number: " + emp.getEmployeeNumber() + "\n" +
                            "Full Name: " + emp.getFullName() + "\n" +
                            "Birthday: " + emp.getBirthday() + "\n" +
                            "Basic Salary: PHP " + String.format("%.2f", emp.getBasicSalary()) + "\n" +
                            "Hourly Rate: PHP " + String.format("%.2f", emp.getHourlyRate()),
                            "Employee Information", JOptionPane.INFORMATION_MESSAGE);
                } else if (empNumber != null) {
                    JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton button2 = new JButton("2. Compute Hours Worked");
        button2.setBounds(55, 150, 260, 30);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNumber = JOptionPane.showInputDialog(frame, "Enter Employee Number:");
                if (empNumber != null && employees.containsKey(empNumber)) {
                    JPanel panel = new JPanel(new GridLayout(0, 2));
                    JTextField startDateField = new JTextField(10);
                    JTextField endDateField = new JTextField(10);

                    panel.add(new JLabel("Start Date (MM/dd/yyyy):"));
                    panel.add(startDateField);
                    panel.add(new JLabel("End Date (MM/dd/yyyy):"));
                    panel.add(endDateField);

                    int result = JOptionPane.showConfirmDialog(frame, panel,
                            "Enter Date Range", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String startDateStr = startDateField.getText();
                        String endDateStr = endDateField.getText();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        try {
                            Date startDate = dateFormat.parse(startDateStr);
                            Date endDate = dateFormat.parse(endDateStr);
                            Map<String, String[]> attendanceData = attendanceRecord.getAttendanceInRange(empNumber, startDate, endDate);
                            long totalMinutes = 0;
                            StringBuilder report = new StringBuilder("Hours Worked Report:\n");
                            for (Map.Entry<String, String[]> entry : attendanceData.entrySet()) {
                                long minutes = payrollMenu.calculateTimeDifferenceMinutes(entry.getValue()[0], entry.getValue()[1]);
                                totalMinutes += minutes;
                                report.append(String.format("Date: %s, Hours: %s%n", entry.getKey(), payrollMenu.formatTimeDifference(minutes)));
                            }
                            report.append(String.format("Total Hours: %s%n", payrollMenu.formatTimeDifference(totalMinutes)));
                            JOptionPane.showMessageDialog(frame, report.toString(), "Hours Worked", JOptionPane.INFORMATION_MESSAGE);

                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (empNumber != null) {
                    JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton button3 = new JButton("3. Compute Gross Salary");
        button3.setBounds(55, 190, 260, 30);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNumber = JOptionPane.showInputDialog(frame, "Enter Employee Number:");
                if (empNumber != null && employees.containsKey(empNumber)) {
                    JPanel panel = new JPanel(new GridLayout(0, 2));
                    JTextField startDateField = new JTextField(10);
                    JTextField endDateField = new JTextField(10);

                    panel.add(new JLabel("Start Date (MM/dd/yyyy):"));
                    panel.add(startDateField);
                    panel.add(new JLabel("End Date (MM/dd/yyyy):"));
                    panel.add(endDateField);

                    int result = JOptionPane.showConfirmDialog(frame, panel,
                            "Enter Date Range", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String startDateStr = startDateField.getText();
                        String endDateStr = endDateField.getText();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        try {
                            Date startDate = dateFormat.parse(startDateStr);
                            Date endDate = dateFormat.parse(endDateStr);
                            Employee employee = employees.get(empNumber);
                            Map<String, String[]> attendanceData = attendanceRecord.getAttendanceInRange(empNumber, startDate, endDate);
                            long totalMinutes = 0;
                            for (Map.Entry<String, String[]> entry : attendanceData.entrySet()) {
                                totalMinutes += payrollMenu.calculateTimeDifferenceMinutes(entry.getValue()[0], entry.getValue()[1]);
                            }
                            double totalHours = totalMinutes / 60.0;
                            double grossSalary = totalHours * employee.getHourlyRate();
                            JOptionPane.showMessageDialog(frame,
                                    String.format("Gross salary for %s: PHP %.2f", employee.getFullName(), grossSalary),
                                    "Gross Salary", JOptionPane.INFORMATION_MESSAGE);

                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (empNumber != null) {
                    JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton button4 = new JButton("4. Compute Net Salary");
        button4.setBounds(55, 230, 260, 30);
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNumber = JOptionPane.showInputDialog(frame, "Enter Employee Number:");
                if (empNumber != null && employees.containsKey(empNumber)) {
                    Employee employee = employees.get(empNumber);
                    double basicSalary = employee.getBasicSalary();
                    double totalDeductions = payrollMenu.getPayrollCalculator().calculateTotalDeductions(basicSalary);
                    double taxableIncome = basicSalary - totalDeductions;
                    double withholdingTax = PayrollCalculator.calculateWithholdingTax(taxableIncome);
                    double netSalary = taxableIncome - withholdingTax;

                    JOptionPane.showMessageDialog(frame,
                            "Net Salary Calculation for " + employee.getFullName() + ":\n" +
                            "Basic Salary: PHP " + String.format("%.2f", basicSalary) + "\n" +
                            "Total Deductions: PHP " + String.format("%.2f", totalDeductions) + "\n" +
                            "Taxable Income: PHP " + String.format("%.2f", taxableIncome) + "\n" +
                            "Withholding Tax: PHP " + String.format("%.2f", withholdingTax) + "\n" +
                            "Net Salary: PHP " + String.format("%.2f", netSalary),
                            "Net Salary", JOptionPane.INFORMATION_MESSAGE);
                } else if (empNumber != null) {
                    JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

            JButton button5 = new JButton("5. Generate Payslip");
        button5.setBounds(55, 270, 260, 30);
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empNumber = JOptionPane.showInputDialog(frame, "Enter Employee Number:");
                if (empNumber != null && employees.containsKey(empNumber)) {
                    Employee emp = employees.get(empNumber);
                    Employee employee = employees.get(empNumber);
                    double basicSalary = employee.getBasicSalary();
                    double totalDeductions = payrollMenu.getPayrollCalculator().calculateTotalDeductions(basicSalary);
                    double taxableIncome = basicSalary - totalDeductions;
                    double withholdingTax = PayrollCalculator.calculateWithholdingTax(taxableIncome);
                    double netSalary = taxableIncome - withholdingTax;

                    JOptionPane.showMessageDialog(frame,
                    "<html>Payslip Information for " + employee.getFullName() + ":<br>" +
                    "<br><br> \n \nEmployee Number: " + emp.getEmployeeNumber() + "<br>" +
                    "Full Name: " + emp.getFullName() + "<br>" +
                    "Birthday: " + emp.getBirthday() + "<br>" +
                    "<br><br> \n \nNet Salary Computation for " + employee.getFullName() + ":<br>" +
                    "<br><br> \n \nBasic Salary: PHP " + String.format("%.2f", basicSalary) + "<br>" +
                    "Hourly Rate: PHP " + String.format("%.2f", emp.getHourlyRate()) + "<br>" +
                    "<br><br> \nDeductions: " + 
                    "<br><br> \n(SSS,HDMF,PhilHealth): PHP " + String.format("%.2f", totalDeductions) + "<br>" +
                    "<br><br> \nTaxable Income: PHP " + String.format("%.2f", taxableIncome) + "<br>" +
                    "Withholding Tax: PHP " + String.format("%.2f", withholdingTax) + "<br>" +
                    "<br><br> \n \nNet Salary: PHP " + String.format("%.2f", netSalary) + "</html>" + "\n",
                    "Payslip", JOptionPane.INFORMATION_MESSAGE);
                } else if (empNumber != null) {
                    JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton button6 = new JButton("6. Exit");
        button6.setBounds(55, 310, 260, 30);
        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(label0);
        frame.add(label1);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(button6);

        frame.setSize(380, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayrollGUI::new);
    }
}