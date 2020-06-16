package pl.zzpj.pharmacy.api.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zzpj.pharmacy.api.model.MedicineOrder;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {

    private Long id;
    private ClientDTO client;
    private Set<MedicineOrder> medicineOrders;
}
