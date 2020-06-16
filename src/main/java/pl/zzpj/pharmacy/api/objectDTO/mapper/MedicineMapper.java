package pl.zzpj.pharmacy.api.objectDTO.mapper;

import pl.zzpj.pharmacy.api.model.Medicine;
import pl.zzpj.pharmacy.api.objectDTO.MedicineDTO;

import java.util.HashSet;

public class MedicineMapper {

    public static Medicine toMedicine(MedicineDTO medicineDTO) {
        return Medicine.builder()
                .id(medicineDTO.getId())
                .isPrescript(medicineDTO.getIsPrescript())
                .name(medicineDTO.getName())
                .price(medicineDTO.getPrice())
                .description(medicineDTO.getDescription())
                .prescriptions(new HashSet<>())
                .medicineOrders(new HashSet<>())
                .build();
    }


    public static MedicineDTO toMedicineDTO(Medicine medicine) {
        return MedicineDTO.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .isPrescript(medicine.isPrescript())
                .price(medicine.getPrice())
                .description(medicine.getDescription())
                .build();
    }

}
