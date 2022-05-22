package com.xc.joy.controller;

import com.xc.joy.dao.EmployeeDAO;
import com.xc.joy.model.Employee;
import com.xc.joy.model.Employees;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @author lxcecho 909231497@qq.com
 * @since 22.05.2022
 */
@RestController
@RequestMapping(path = "/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDao;

    @GetMapping(path="/", produces = "application/json")
    public Employees getEmployees()
    {
        return employeeDao.getAllEmployees();
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {

        // add resource
        employeeDao.addEmployee(employee);

        // Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        // Send location in response
        return ResponseEntity.created(location).build();
    }

}
