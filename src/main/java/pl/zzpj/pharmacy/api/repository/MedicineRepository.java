package pl.zzpj.pharmacy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.pharmacy.api.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    boolean existsByName(String name);
}
