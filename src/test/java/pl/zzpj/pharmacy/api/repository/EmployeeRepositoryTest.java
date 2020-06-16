package pl.zzpj.pharmacy.api.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.zzpj.pharmacy.api.model.Employee;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employees;

    @Test
    public void addEmployee() {
        Employee employee = Employee.builder()
                .firstName("Piotrek")
                .lastName("Ruc")
                .login("login")
                .password("password")
                .build();
        employees.save(employee);

        List<Employee> result = employees.findByFirstNameAndLastName("Piotrek", "Ruc");
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.stream().anyMatch(r ->
                r.getLogin().equals("login") && r.getPassword().equals("password"))
        );
    }
}
