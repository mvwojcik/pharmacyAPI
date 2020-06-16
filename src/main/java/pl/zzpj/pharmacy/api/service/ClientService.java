package pl.zzpj.pharmacy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.pharmacy.api.exception.ClientException;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.objectDTO.mapper.ClientMapper;
import pl.zzpj.pharmacy.api.repository.ClientRepository;
import pl.zzpj.pharmacy.api.repository.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clients;
    private OrderRepository orderRepository;

    @Autowired
    public ClientService(ClientRepository clients, OrderRepository orders) {
        this.clients = clients;
        this.orderRepository = orders;
    }

    public ClientDTO getClient(long id) {
        return clients.findById(id)
                .map(ClientMapper::toClientDTO)
                .orElseThrow(() -> new ClientException("Klient o podanym id nie istnieje"));
    }

    public void removeClient(long id) {
        try {
            clients.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException();
        }
    }

    public List<ClientDTO> getAllClients() {
        return clients.findAll()
                .stream()
                .map(ClientMapper::toClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO addClient(ClientDTO client) {
        return ClientMapper.toClientDTO(clients.save(ClientMapper.toClient(client, new HashSet<>())));
    }

    public ClientDTO updateClient(ClientDTO client) {
        Set<Order> orders = new HashSet<>(orderRepository.findAllByClient_Id(client.getId()));
        return clients.findById(client.getId())
                .map(c -> clients.save(ClientMapper.toClient(client, orders)))
                .map(ClientMapper::toClientDTO)
                .orElseThrow(() -> new ClientException("Klient o podanym id nie istnieje"));
    }
}
