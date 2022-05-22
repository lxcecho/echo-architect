package com.xc.joy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxcecho 909231497@qq.com
 * @since 22.05.2022
 */
public class Employees {
    private List<Employee> employeeList;

    public List<Employee> getEmployeeList() {
        if(employeeList == null) {
            employeeList = new ArrayList<>();
        }
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
