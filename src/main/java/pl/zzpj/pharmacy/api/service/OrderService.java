package pl.zzpj.pharmacy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.pharmacy.api.exception.OrderException;
import pl.zzpj.pharmacy.api.exception.OrderNotValidException;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.objectDTO.OrderDTO;
import pl.zzpj.pharmacy.api.objectDTO.mapper.OrderMapper;
import pl.zzpj.pharmacy.api.repository.ClientRepository;
import pl.zzpj.pharmacy.api.repository.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import pl.zzpj.pharmacy.api.repository.Raport;

@Service
public class OrderService {

    private final OrderRepository ordersRepository;
    private final ClientRepository clientRepository;


    public OrderService(OrderRepository ordersRepository, ClientRepository clientRepository) {
        this.ordersRepository = ordersRepository;
        this.clientRepository = clientRepository;
    }

    public OrderDTO getOrder(long id) {
        return ordersRepository.findById(id)
                               .map(OrderMapper::toOrderDTO)
                               .orElseThrow(() -> new OrderException("Zamówienie o podanym id nie istnieje"));
    }

    public void removeOrder(long id) {
        //simplify logic throwing exception
        if (ordersRepository.existsById(id)) {
            ordersRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public List<OrderDTO> getAllOrders() {
        return ordersRepository.findAll()
                               .stream()
                               .map(OrderMapper::toOrderDTO)
                               .collect(Collectors.toList());
    }

    public OrderDTO createOrder(OrderDTO order) {
        Optional<Client> optionalClient = clientRepository.findById(order.getClient()
                                                                              .getId());
        return optionalClient.map(client -> OrderMapper.toOrder(order, client))
                             .map(ordersRepository::save)
                             .map(OrderMapper::toOrderDTO)
                             .orElseThrow(OrderNotValidException::new);
    }

    public OrderDTO updateOrder(OrderDTO order) {
        Optional<Client> optionalClient = clientRepository.findById(order.getClient()
                                                                              .getId());
        Function<Client, OrderDTO> processUpdate = client ->
                ordersRepository.findById(order.getId())
                                .map(ord -> ordersRepository.save(OrderMapper.toOrder(order, client)))
                                .map(OrderMapper::toOrderDTO)
                                .orElseThrow(() -> new OrderException(
                                        "order does not exist"));
        return optionalClient.map(processUpdate)
                             .orElseThrow(EntityNotFoundException::new);
    }

    public  HashMap<Long, String> getAllRaports() {

        HashMap<Long, String> raports = new HashMap<>();
        List<Raport> raportList = ordersRepository.getRaportForOrders();

        for(Raport raport : raportList){
            raports.put(raport.getOrderID(), raport.getFirstName()+" "+raport.getLastName()+", medicines: "+raport.getMedicines());
        }
        return raports;
    }
}
