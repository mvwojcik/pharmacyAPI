package pl.zzpj.pharmacy.api.service;

import org.springframework.stereotype.Service;
import pl.zzpj.pharmacy.api.exception.MedicineException;
import pl.zzpj.pharmacy.api.objectDTO.MedicineDTO;
import pl.zzpj.pharmacy.api.objectDTO.mapper.MedicineMapper;
import pl.zzpj.pharmacy.api.repository.MedicineRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;


    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public MedicineDTO getMedicine(long id) {
        return medicineRepository.findById(id)
                .map(MedicineMapper::toMedicineDTO)
                .orElseThrow(() -> new MedicineException("Medykament o podanym id nie istnieje"));
    }

    public void removeMedicine(long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
        } else {
            throw new MedicineException("Medykament o podanym id nie istnieje");
        }
    }

    public List<MedicineDTO> getAllMedicines() {
        return medicineRepository.findAll()
                .stream()
                .map(MedicineMapper::toMedicineDTO)
                .collect(Collectors.toList());
    }

    public MedicineDTO addMedicine(MedicineDTO medicine) {
        if (!medicineRepository.existsByName(medicine.getName())) {
            return MedicineMapper.toMedicineDTO(medicineRepository.save(MedicineMapper.toMedicine(medicine)));
        } else {
            throw new MedicineException("Medykament o podanym loginie już istnieje");
        }
    }

    public MedicineDTO updateMedicine(MedicineDTO medicine) {
        return medicineRepository.findById(medicine.getId())
                .map(e -> medicineRepository.save(
                        MedicineMapper.toMedicine(medicine)))
                .map(MedicineMapper::toMedicineDTO)
                .orElseThrow(() -> new MedicineException("Medykament o podanym id nie istnieje"));
    }
}
