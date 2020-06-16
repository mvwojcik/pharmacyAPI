package pl.zzpj.pharmacy.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.pharmacy.api.objectDTO.MedicineDTO;
import pl.zzpj.pharmacy.api.service.MedicineService;

import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MedicineDTO> getMedicine(@PathVariable("id") long id) {
        return new ResponseEntity<>(medicineService.getMedicine(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> removeMedicine(@PathVariable("id") long id) {
        medicineService.removeMedicine(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<MedicineDTO>> getAllMedicines() {
        return new ResponseEntity<>(medicineService.getAllMedicines(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicineDTO> addMedicine(@RequestBody MedicineDTO medicineDTO) {
        return new ResponseEntity<>(medicineService.addMedicine(medicineDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MedicineDTO> updateMedicine(@RequestBody MedicineDTO medicineDTO) {
        return new ResponseEntity<>(medicineService.updateMedicine(medicineDTO), HttpStatus.OK);
    }

}
