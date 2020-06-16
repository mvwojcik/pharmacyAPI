package pl.zzpj.pharmacy.api.objectDTO.mapper;

import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.OrderDTO;

public class OrderMapper {

    public static Order toOrder(OrderDTO orderDTO, Client client) {
        return Order.builder()
                    .id(orderDTO.getId())
                    .client(client)
                    //todo mapper for medicine orders and client
                    .medicineOrders(orderDTO.getMedicineOrders())
                    .build();
    }

    public static OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                       .client(ClientMapper.toClientDTO(order.getClient()))
                       .id(order.getId())
                       .medicineOrders(order.getMedicineOrders())
                       .build();
    }
}
