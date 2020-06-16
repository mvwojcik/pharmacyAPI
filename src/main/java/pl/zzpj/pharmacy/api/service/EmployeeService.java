package pl.zzpj.pharmacy.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zzpj.pharmacy.api.exception.EmployeeException;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;
import pl.zzpj.pharmacy.api.objectDTO.mapper.EmployeeMapper;
import pl.zzpj.pharmacy.api.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeDTO getEmployee(long id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper::toEmployeeDTO)
                .orElseThrow(() -> new EmployeeException("Pracownik o podanym id nie istnieje"));
    }

    public void removeEmployee(long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new EmployeeException("Pracownik o podanym id nie istnieje");
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO addEmployee(EmployeeDTO employee) {
        if(!employeeRepository.existsByLogin(employee.getLogin())){
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            return EmployeeMapper.toEmployeeDTO(employeeRepository.save(EmployeeMapper.toEmployee(employee)));
        }else{
            throw new EmployeeException("Pracownik o podanym loginie już istnieje");
        }
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.findById(employee.getId())
                .map(e -> employeeRepository.save(
                    EmployeeMapper.toEmployee(employee)))
                .map(EmployeeMapper::toEmployeeDTO)
                .orElseThrow(() -> new EmployeeException("Klient o podanym id nie istnieje"));
    }
}
