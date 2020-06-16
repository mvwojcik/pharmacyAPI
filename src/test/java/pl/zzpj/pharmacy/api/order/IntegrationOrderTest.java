package pl.zzpj.pharmacy.api.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.repository.ClientRepository;
import pl.zzpj.pharmacy.api.repository.OrderRepository;
import pl.zzpj.pharmacy.api.service.OrderService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegrationOrderTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    OrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldSave() throws Exception {
        Client save = clientRepository.save(EntityHelper.prepareClient(null));

        MvcResult result = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EntityHelper.prepareOrderDTO(null,
                        EntityHelper.prepareClientDTO(save.getId())))))
                .andReturn();
        assertThat(result.getResponse().getStatus(), Matchers.is(201));
    }

    @Test
    public void shouldNotSave() throws Exception {
        MvcResult result = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EntityHelper.prepareOrderDTO(null,
                        EntityHelper.prepareClientDTO(7L)))))
                .andReturn();
        assertThat(result.getResponse().getStatus(), Matchers.is(400));
    }
}
