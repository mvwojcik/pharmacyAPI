package pl.zzpj.pharmacy.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.objectDTO.MedicineDTO;
import pl.zzpj.pharmacy.api.service.MedicineService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class MedicineControllerTest {

    MockMvc mockMvc;

    @Mock
    MedicineService medicineService;

    @InjectMocks
    MedicineController medicineController;

    MedicineDTO medicineDTO;
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicineController).build();
        medicineController = new MedicineController(medicineService);
        medicineDTO = EntityHelper.prepareMedicineDTO(EntityHelper.prepareMedicine(1L));
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getMedicine() throws Exception {
        Mockito.when(medicineService.getMedicine(anyLong())).thenReturn(medicineDTO);

        mockMvc.perform(get("/medicines/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void removeMedicine() throws Exception {
        mockMvc.perform(delete("/medicines/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllMedicines() throws Exception {
        Mockito.when(medicineService.getAllMedicines()).thenReturn(Arrays.asList(medicineDTO));
        mockMvc.perform(get("/medicines")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addMedicine() throws Exception {
        Mockito.when(medicineService.addMedicine(any())).thenReturn(medicineDTO);
        mockMvc.perform(post("/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicineDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateMedicine() throws Exception {
        Mockito.when(medicineService.updateMedicine(any())).thenReturn(medicineDTO);
        mockMvc.perform(put("/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicineDTO)))
                .andExpect(status().isOk());
    }


}
