package pl.zzpj.pharmacy.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.zzpj.pharmacy.api.exception.ClientException;
import pl.zzpj.pharmacy.api.exception.OrderException;
import pl.zzpj.pharmacy.api.exception.OrderNotValidException;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.MedicineOrder;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;
import pl.zzpj.pharmacy.api.objectDTO.OrderDTO;
import pl.zzpj.pharmacy.api.repository.ClientRepository;
import pl.zzpj.pharmacy.api.repository.OrderRepository;
import pl.zzpj.pharmacy.api.service.ClientService;
import pl.zzpj.pharmacy.api.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ClientRepository clientRepository;
    ClientService clientService;

    Client client;
    ClientDTO clientDTO;
    OrderDTO orderDTO;
    Order order;

    @BeforeEach
    void setup() {
        clientService = new ClientService(clientRepository, orderRepository);
        client = EntityHelper.prepareClient(5L);
        clientDTO = EntityHelper.prepareClientDTO(5L);
        orderDTO = EntityHelper.prepareOrderDTO(8L, clientDTO);
        order = EntityHelper.prepareOrder(8L, client);

    }

    @Test
    public void shouldSaveClient() {
        Mockito.when(clientRepository.save(client))
                .thenReturn(client);

        ClientDTO result = clientService.addClient(clientDTO);

        assertThat(result, notNullValue());
        assertThat(result.getId(), is(5L));
    }

    @Test
    public void shouldReturnClient() {
        Mockito.when(clientRepository.findById(5L))
                .thenReturn(Optional.of(client));
        ClientDTO client = clientService.getClient(5L);
        assertThat(client.getId(), is(5L));
    }

    @Test
    public void shouldNotFindClient() {
        Mockito.when(clientRepository.findById(-34L))
                .thenReturn(Optional.empty());
        assertThrows(ClientException.class, () -> clientService.getClient(-34L));
    }

    @Test
    public void shouldRemoveClient() {
        Mockito.doNothing().when(clientRepository).deleteById(5L);
        assertDoesNotThrow(() -> clientService.removeClient(5L));
    }

    @Test
    public void shouldNotRemoveClient() {
        Mockito.doThrow(RuntimeException.class).when(clientRepository).deleteById(5L);
        assertThrows(EntityNotFoundException.class, () -> clientService.removeClient(5L));
    }

    @Test
    public void shouldUpdateClient() {
        Mockito.when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(any())).thenReturn(client);

        ClientDTO result = clientService.updateClient(clientDTO);

        Assert.assertEquals(result.getId(), clientDTO.getId());
        Assert.assertEquals(result.getAddress(), clientDTO.getAddress());
        Assert.assertEquals(result.getFirstName(), clientDTO.getFirstName());
        Assert.assertEquals(result.getLastName(), clientDTO.getLastName());
        Assert.assertEquals(result.getPrescriptions(), clientDTO.getPrescriptions());
        Mockito.verify(clientRepository).findById(any());
        Mockito.verify(clientRepository).save(any());
    }

    @Test
    public void shouldNotUpdateClient(){

        Mockito.when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientException.class, () -> clientService.updateClient(clientDTO));
    }
}
