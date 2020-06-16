package pl.zzpj.pharmacy.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.model.Employee;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;
import pl.zzpj.pharmacy.api.repository.EmployeeRepository;
import pl.zzpj.pharmacy.api.security.Jwt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    Jwt jwtUtils;

    @Mock
    UserDetailsService userDetailsService;

    @InjectMocks
    EmployeeService employeeService;

    Employee employee;
    Employee employee2;
    EmployeeDTO employeeDTO;


    @BeforeEach
    void setup() {
        employeeService = new EmployeeService(employeeRepository, passwordEncoder,
                authenticationManager, jwtUtils, userDetailsService);
        employee = EntityHelper.prepareEmployee1();
        employee2 = EntityHelper.prepareEmployee2();
        employeeDTO = EntityHelper.prepareEmployeeDTO(employee);
    }


    @Test
    public void getEmployee() {
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        EmployeeDTO dto = employeeService.getEmployee(1L);
        Assert.assertEquals(dto.getId(), employee.getId());
        Assert.assertEquals(dto.getLogin(), employee.getLogin());
        Assert.assertEquals(dto.getFirstName(), employee.getFirstName());
        Assert.assertEquals(dto.getLastName(), employee.getLastName());

        Mockito.verify(employeeRepository).findById(anyLong());


    }

    @Test
    public void getEmployeeWhenNoEmployee() {
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(pl.zzpj.pharmacy.api.exception.EmployeeException.class, () -> employeeService.getEmployee(1L));
    }

    @Test
    public void removeEmployee() {
        Mockito.when(employeeRepository.existsById(1L))
                .thenReturn(true);
        assertDoesNotThrow(() -> employeeService.removeEmployee(1L));
    }

    @Test
    public void removeEmployeeWhenNoEmployee() {
        Mockito.when(employeeRepository.existsById(1L))
                .thenReturn(false);
        assertThrows(pl.zzpj.pharmacy.api.exception.EmployeeException.class, () -> employeeService.removeEmployee(1L));
    }

    @Test
    public void getAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, employee2));
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();
        Assert.assertEquals(2, employeeDTOS.size());

        Mockito.verify(employeeRepository).findAll();
    }

    @Test
    public void addEmployee(){
        Mockito.when(employeeRepository.existsByLogin(anyString())).thenReturn(false);
        Mockito.when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeDTO dto = employeeService.addEmployee(employeeDTO);

        Assert.assertEquals(dto.getId(), employeeDTO.getId());
        Assert.assertEquals(dto.getLogin(), employeeDTO.getLogin());
        Assert.assertEquals(dto.getFirstName(), employeeDTO.getFirstName());
        Assert.assertEquals(dto.getLastName(), employeeDTO.getLastName());
        Mockito.verify(employeeRepository).existsByLogin(anyString());
        Mockito.verify(employeeRepository).save(any());

    }

    @Test
    public void addEmployeeWhenLoginExists(){
        Mockito.when(employeeRepository.existsByLogin(anyString())).thenReturn(true);
        assertThrows(pl.zzpj.pharmacy.api.exception.EmployeeException.class,
                () -> employeeService.addEmployee(employeeDTO));
    }

    @Test
    public void updateEmployee(){
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeDTO dto = employeeService.updateEmployee(employeeDTO);

        Assert.assertEquals(dto.getId(), employeeDTO.getId());
        Assert.assertEquals(dto.getLogin(), employeeDTO.getLogin());
        Assert.assertEquals(dto.getFirstName(), employeeDTO.getFirstName());
        Assert.assertEquals(dto.getLastName(), employeeDTO.getLastName());
        Mockito.verify(employeeRepository).findById(anyLong());
        Mockito.verify(employeeRepository).save(any());
    }



    @Test
    public void updateEmployeeWhenNoEmployee(){

        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(pl.zzpj.pharmacy.api.exception.EmployeeException.class,
                () -> employeeService.updateEmployee(employeeDTO));
    }
}
