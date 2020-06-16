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

    boolean existsById( Long id );
}
