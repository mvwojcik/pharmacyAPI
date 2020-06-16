package pl.zzpj.pharmacy.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zzpj.pharmacy.api.exception.OrderException;
import pl.zzpj.pharmacy.api.exception.OrderNotValidException;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.MedicineOrder;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.objectDTO.OrderDTO;
import pl.zzpj.pharmacy.api.repository.ClientRepository;
import pl.zzpj.pharmacy.api.repository.OrderRepository;
import pl.zzpj.pharmacy.api.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ClientRepository clientRepository;
    OrderService orderService;

    Client client;
    ClientDTO clientDTO;
    OrderDTO orderDTO;
    Order order;
    MedicineOrder medicineOrder;

    @BeforeEach
    void setup() {
        orderService = new OrderService(orderRepository, clientRepository);
        client = EntityHelper.prepareClient(3L);
        clientDTO = EntityHelper.prepareClientDTO(3L);
        orderDTO = EntityHelper.prepareOrderDTO(7L, clientDTO);
        order = EntityHelper.prepareOrder(7L, client);

    }

    @Test
    public void shouldSaveOrder() {
        Mockito.when(clientRepository.findById(3L))
               .thenReturn(Optional.of(client));
        Mockito.when(orderRepository.save(order))
               .thenReturn(order);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertThat(result, notNullValue());
        assertThat(result.getId(), is(7L));
    }

    @Test
    public void shouldNotSaveOrder() {
        Mockito.when(clientRepository.findById(3L))
               .thenReturn(Optional.empty());
        assertThrows(OrderNotValidException.class, () -> orderService.createOrder(orderDTO));
        verifyNoInteractions(orderRepository);
    }

    @Test
    public void shouldReturnOrder() {
        Mockito.when(orderRepository.findById(7L))
               .thenReturn(Optional.of(order));
        OrderDTO order = orderService.getOrder(7L);
        assertThat(order.getId(), is(7L));
    }

    @Test
    public void shouldNotFindOrder() {
        Mockito.when(orderRepository.findById(420L))
               .thenReturn(Optional.empty());
        assertThrows(OrderException.class, () -> orderService.getOrder(420L));
    }

    @Test
    public void shouldRemoveOrder() {
        Mockito.when(orderRepository.existsById(7L))
               .thenReturn(true);
        assertDoesNotThrow(() -> orderService.removeOrder(7L));

    }

    @Test
    public void shouldNotRemoveOrder() {
        Mockito.when(orderRepository.existsById(420L))
               .thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> orderService.removeOrder(420L));
    }

    @Test
    public void shouldUpdateOrder() {
        //other order
        Mockito.when(clientRepository.findById(3L))
               .thenReturn(Optional.of(client));
        Mockito.when(orderRepository.findById(7L))
               .thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(order))
               .thenReturn(order);
        OrderDTO updatedOrderDTO = orderService.updateOrder(this.orderDTO);
        verify(orderRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldNotUpdateOrderOrderDoesNotExist() {
        Mockito.when(clientRepository.findById(3L))
               .thenReturn(Optional.of(client));
        Mockito.when(orderRepository.findById(7L))
               .thenReturn(Optional.empty());
        assertThrows(OrderException.class, () -> orderService.updateOrder(orderDTO));
    }

    @Test
    public void shouldNotUpdateOrderClientDoesNotExist() {
        Mockito.when(clientRepository.findById(3L))
               .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrder(orderDTO));

    }

}
