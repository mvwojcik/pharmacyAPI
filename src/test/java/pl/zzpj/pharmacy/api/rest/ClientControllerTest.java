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
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.service.ClientService;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClientControllerTest {

    MockMvc mockMvc;

    @Mock
    ClientService clientService;

    @InjectMocks
    ClientController clientController;

    ClientDTO clientDTO;
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        clientController = new ClientController(clientService);
        clientDTO = EntityHelper.prepareClientDTO(20L);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getClient() throws Exception {
        Mockito.when(clientService.getClient(20L)).thenReturn(clientDTO);

        mockMvc.perform(get("/clients/{id}", 20L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void removeClient() throws Exception {
        mockMvc.perform(delete("/clients/{id}", 20L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllClients() throws Exception {
        Mockito.when(clientService.getAllClients()).thenReturn(Collections.singletonList(clientDTO));
        mockMvc.perform(get("/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addClient() throws Exception {
        Mockito.when(clientService.addClient(any())).thenReturn(clientDTO);
        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateClient() throws Exception {
        Mockito.when(clientService.updateClient(any())).thenReturn(clientDTO);
        mockMvc.perform(put("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());
    }


}
