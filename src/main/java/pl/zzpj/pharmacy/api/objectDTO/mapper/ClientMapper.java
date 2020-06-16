package pl.zzpj.pharmacy.api.objectDTO.mapper;

import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;

import java.util.Set;

public class ClientMapper {

    public static Client toClient(ClientDTO clientDTO, Set<Order> orders) {
        return Client.builder()
                     .id(clientDTO.getId())
                     .address(clientDTO.getAddress())
                     .firstName(clientDTO.getFirstName())
                     .lastName(clientDTO.getLastName())
                     .orders(orders)
                     .prescriptions(clientDTO.getPrescriptions())
                     .build();
    }

    public static ClientDTO toClientDTO(Client client) {
        return ClientDTO.builder()
                        .firstName(client.getFirstName())
                        .lastName(client.getLastName())
                        .address(client.getAddress())
                        .id(client.getId())
                        .build();
    }
}
