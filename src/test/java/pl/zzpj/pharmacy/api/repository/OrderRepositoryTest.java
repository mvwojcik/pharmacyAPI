package pl.zzpj.pharmacy.api.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private ClientRepository clients;
    @Autowired
    private OrderRepository orderRepo;

    @Test
    public void addOrder() {
        Set<Order> orders = new HashSet<>();
        Order order = Order.builder().build();
        orders.add(order);
        Client client = Client.builder()
                .firstName("Adrian")
                .lastName("Zupełny")
                .address("Łękołody")
                .build();
        order.setClient(client);
        clients.save(client);
        orderRepo.save(order);

        Client client1 = Client.builder()
                .firstName("Kazimir")
                .lastName("Polak")
                .address("Chrząszczyrzewoszyce")
                .build();
        clients.save(client1);

        List<Order> result = orderRepo.findByClient(client);
        Assert.assertFalse(result.isEmpty());
        result = orderRepo.findByClient(client1);
        Assert.assertTrue(result.isEmpty());
    }
}
