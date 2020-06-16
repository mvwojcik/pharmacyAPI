package pl.zzpj.pharmacy.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.zzpj.pharmacy.api.exception.EmployeeException;
import pl.zzpj.pharmacy.api.model.Employee;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDetails;
import pl.zzpj.pharmacy.api.repository.EmployeeRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Employee> user = this.employeeRepository.findByLogin(login);
        return user.map(EmployeeDetails::new).orElseThrow(
            () -> new EmployeeException("Not found employee with username: " + login));
    }

}
