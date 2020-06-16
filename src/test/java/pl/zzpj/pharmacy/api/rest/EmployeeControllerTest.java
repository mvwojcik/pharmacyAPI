package pl.zzpj.pharmacy.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;
import pl.zzpj.pharmacy.api.service.EmployeeService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

    MockMvc mockMvc;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    EmployeeDTO employeeDTO;
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employeeController = new EmployeeController(employeeService);
        employeeDTO = EntityHelper.prepareEmployeeDTO(EntityHelper.prepareEmployee1());
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getEmployee() throws Exception {
        Mockito.when(employeeService.getEmployee(anyLong())).thenReturn(employeeDTO);

        mockMvc.perform(get("/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void removeEmployee() throws Exception {
        mockMvc.perform(delete("/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllEmployees() throws Exception {
        Mockito.when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employeeDTO));
        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addEmployee() throws Exception {
        Mockito.when(employeeService.addEmployee(any())).thenReturn(employeeDTO);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployee() throws Exception {
        Mockito.when(employeeService.updateEmployee(any())).thenReturn(employeeDTO);
        mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());
    }


}
