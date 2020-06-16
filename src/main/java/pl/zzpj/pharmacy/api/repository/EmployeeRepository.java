package pl.zzpj.pharmacy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.pharmacy.api.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Boolean existsByLogin(String login);

    Optional<Employee> findByLogin(String login);
}
