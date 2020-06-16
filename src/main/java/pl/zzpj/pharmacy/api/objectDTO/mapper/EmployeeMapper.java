package pl.zzpj.pharmacy.api.objectDTO.mapper;

import pl.zzpj.pharmacy.api.model.Employee;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;

public class EmployeeMapper {

    public static Employee toEmployee(EmployeeDTO employeeDTO) {
        return Employee.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .login(employeeDTO.getLogin())
                .password(employeeDTO.getPassword())
                .build();
    }


    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .build();
    }
}
