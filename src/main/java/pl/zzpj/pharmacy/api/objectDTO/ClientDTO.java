package pl.zzpj.pharmacy.api.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zzpj.pharmacy.api.model.Prescription;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private Set<Prescription> prescriptions;
}
