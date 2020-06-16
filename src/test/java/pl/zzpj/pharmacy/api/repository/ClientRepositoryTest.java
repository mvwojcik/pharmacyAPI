package pl.zzpj.pharmacy.api.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clients;

    @Autowired
    private OrderRepository ordersRepo;

    @Test
    public void addClient() {
        Client client = Client.builder()
                .firstName("Piotr")
                .lastName("Ruc")
                .address("Tu")
                .build();
        clients.save(client);

        List<Client> result = clients.findByFirstNameAndLastName("Piotr", "Ruc");
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.stream().anyMatch(r -> r.getAddress().equals("Tu")));
    }


    @Test
    public void deleteClient() {
        long count = clients.count();

        Client client = Client.builder()
                .firstName("Piotr")
                .lastName("Ruc")
                .address("Tu")
                .build();
        clients.save(client);
        Assert.assertEquals(count + 1, clients.count());
        clients.delete(client);
        Assert.assertEquals(count, clients.count());
    }

    @Test
    public void updateClient() {
        Client client = Client.builder()
                .firstName("Piter")
                .lastName("Ruc")
                .address("Tu")
                .build();
        clients.save(client);

        List<Client> result = clients.findByFirstNameAndLastName("Piter", "Ruc");
        Assert.assertTrue(result.stream().anyMatch(r -> r.getAddress().equals("Tu")));

        long count = clients.count();
        client.setFirstName("Pioter");
        client.setAddress("Tam");

        result = clients.findByFirstNameAndLastName("Pioter", "Ruc");
        Assert.assertTrue(result.stream().anyMatch(r -> r.getAddress().equals("Tam")));
        Assert.assertEquals(count, clients.count());
    }
}
