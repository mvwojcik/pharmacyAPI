package pl.zzpj.pharmacy.api.symptom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomController {
    Security security;

    public SymptomController(Security security) {
        this.security = security;
    }

    @GetMapping("/symptoms")
    public ResponseEntity get() {
        security.jadaom();
        return ResponseEntity.ok()
                             .build();
    }
}
