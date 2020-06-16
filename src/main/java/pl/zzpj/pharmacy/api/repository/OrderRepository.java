package pl.zzpj.pharmacy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllByClient_Id( Long id );

    @Query("SELECT ord FROM Order ord WHERE ord.client =:client")
    List<Order> findByClient( @Param("client") Client client );

    @Query(nativeQuery = true, value = "select c.first_name as firstName, c.last_name as lastName, o.id  as orderID," +
            "(select group_concat(m.name) from medicines m, medicine_order where m.id = medicine_id) as medicines  " +
            "from clients c, orders o " +
            "where o.client_id = c.id")
    List<Raport> getRaportForOrders();

    boolean existsById( Long id );
}
