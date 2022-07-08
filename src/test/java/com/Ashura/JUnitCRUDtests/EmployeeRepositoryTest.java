package com.Ashura.JUnitCRUDtests;

import com.Ashura.JUnitCRUDtests.model.Employee;
import com.Ashura.JUnitCRUDtests.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {



    @Autowired
    private EmployeeRepository employeeRepository;

    // JUnit test for saveEmployee
    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest(){

        Employee employee = Employee.builder()
                .firstName("Ashura")
                .lastName("von Jaeger")
                .email("ashura@test.com")
                .build();

        employeeRepository.save(employee);

        //tests
        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getEmployeeByIdTest(){
        Employee employee = employeeRepository.findById(1L).get();

        //tests
        Assertions.assertThat(employee.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getEListOfEmployeesTest(){
        List<Employee> employees = employeeRepository.findAll();

        //tests
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest(){
        Employee employee = employeeRepository.findById(1L).get();
        employee.setEmail("update@test.com");
        Employee updatedEmployee =  employeeRepository.save(employee);

        //tests
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("update@test.com");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest(){
        Employee employee = employeeRepository.findById(1L).get();

        employeeRepository.delete(employee);

        Employee employee1 = null;

        Optional<Employee> testEmployee = employeeRepository.findByEmail("update@test.com");

        if (testEmployee.isPresent()) {
            employee1 = testEmployee.get();
        }

        //tests
        Assertions.assertThat(employee1).isNull();
    }
}
