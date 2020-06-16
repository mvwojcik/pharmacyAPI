package pl.zzpj.pharmacy.api.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MedicineDTO {

    private Long id;
    private String name;
    private Boolean isPrescript;
    private String description;
    private double price;
}
