package pl.zzpj.pharmacy.api.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zzpj.pharmacy.api.helpers.EntityHelper;
import pl.zzpj.pharmacy.api.model.Medicine;
import pl.zzpj.pharmacy.api.objectDTO.MedicineDTO;
import pl.zzpj.pharmacy.api.repository.MedicineRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MedicineServiceTest {

    private static final double DELTA = 1e-6;

    @Mock
    MedicineRepository medicineRepository;

    @InjectMocks
    MedicineService medicineService;

    Medicine medicine;
    Medicine medicine2;
    MedicineDTO medicineDTO;


    @BeforeEach
    void setup() {
        medicineService = new MedicineService(medicineRepository);
        medicine = EntityHelper.prepareMedicine(1L);
        medicine2 = EntityHelper.prepareMedicine(2L);
        medicineDTO = EntityHelper.prepareMedicineDTO(medicine);
    }


    @Test
    public void getMedicine() {
        Mockito.when(medicineRepository.findById(anyLong())).thenReturn(Optional.of(medicine));

        MedicineDTO dto = medicineService.getMedicine(1L);
        Assert.assertEquals(dto.getId(), medicine.getId());
        Assert.assertEquals(dto.getName(), medicine.getName());
        Assert.assertEquals(dto.getIsPrescript(), medicine.isPrescript());
        Assert.assertEquals(dto.getDescription(), medicine.getDescription());
        Assert.assertEquals(dto.getPrice(), medicine.getPrice(), DELTA);

        Mockito.verify(medicineRepository).findById(anyLong());
    }

    @Test
    public void getMedicineWhenNoMedicine() {
        Mockito.when(medicineRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(pl.zzpj.pharmacy.api.exception.MedicineException.class, () -> medicineService.getMedicine(1L));
    }

    @Test
    public void removeMedicine() {
        Mockito.when(medicineRepository.existsById(1L))
                .thenReturn(true);
        assertDoesNotThrow(() -> medicineService.removeMedicine(1L));
    }

    @Test
    public void removeMedicineWhenNoMedicine() {
        Mockito.when(medicineRepository.existsById(1L))
                .thenReturn(false);
        assertThrows(pl.zzpj.pharmacy.api.exception.MedicineException.class,
                () -> medicineService.removeMedicine(1L));
    }

    @Test
    public void getAllMedicines() {
        Mockito.when(medicineRepository.findAll()).thenReturn(Arrays.asList(medicine, medicine2));
        List<MedicineDTO> medicineDTOS = medicineService.getAllMedicines();
        Assert.assertEquals(2, medicineDTOS.size());

        Mockito.verify(medicineRepository).findAll();
    }

    @Test
    public void addMedicine(){
        Mockito.when(medicineRepository.existsByName(anyString())).thenReturn(false);
        Mockito.when(medicineRepository.save(any())).thenReturn(medicine);

        MedicineDTO dto = medicineService.addMedicine(medicineDTO);

        Assert.assertEquals(dto.getId(), medicine.getId());
        Assert.assertEquals(dto.getName(), medicine.getName());
        Assert.assertEquals(dto.getIsPrescript(), medicine.isPrescript());
        Assert.assertEquals(dto.getPrice(), medicine.getPrice(), DELTA);
        Assert.assertEquals(dto.getDescription(), medicine.getDescription());
        Mockito.verify(medicineRepository).existsByName(anyString());
        Mockito.verify(medicineRepository).save(any());

    }

    @Test
    public void addMedicineWhenNameExists(){
        Mockito.when(medicineRepository.existsByName(anyString())).thenReturn(true);
        assertThrows(pl.zzpj.pharmacy.api.exception.MedicineException.class,
                () -> medicineService.addMedicine(medicineDTO));
    }

    @Test
    public void updateMedicine(){
        Mockito.when(medicineRepository.findById(anyLong())).thenReturn(Optional.of(medicine));
        Mockito.when(medicineRepository.save(any())).thenReturn(medicine);

        MedicineDTO dto = medicineService.updateMedicine(medicineDTO);

        Assert.assertEquals(dto.getId(), medicine.getId());
        Assert.assertEquals(dto.getName(), medicine.getName());
        Assert.assertEquals(dto.getIsPrescript(), medicine.isPrescript());
        Assert.assertEquals(dto.getPrice(), medicine.getPrice(), DELTA);
        Assert.assertEquals(dto.getDescription(), medicine.getDescription());
        Mockito.verify(medicineRepository).findById(anyLong());
        Mockito.verify(medicineRepository).save(any());
    }



    @Test
    public void updateMedicineWhenNoMedicine(){

        Mockito.when(medicineRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(pl.zzpj.pharmacy.api.exception.MedicineException.class,
                () -> medicineService.updateMedicine(medicineDTO));
    }
}
