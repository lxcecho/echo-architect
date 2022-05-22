package com.xc.joy.dao;

import com.xc.joy.model.Employee;
import com.xc.joy.model.Employees;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lxcecho 909231497@qq.com
 * @since 22.05.2022
 */
@Repository
public interface EmployeeDAO extends CrudRepository<Employee, Long> {

    Employees getAllEmployees();

    Boolean addEmployee(Employee employee);

}
