package pl.zzpj.pharmacy.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// nie potrzebne    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String firstName;


    @NotEmpty
    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "client")
    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

}
