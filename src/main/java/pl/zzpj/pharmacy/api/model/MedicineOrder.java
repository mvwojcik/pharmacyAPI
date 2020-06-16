package pl.zzpj.pharmacy.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineOrder implements Serializable {

    private int amount;

    @Id
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
