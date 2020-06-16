package pl.zzpj.pharmacy.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) {
        return new ResponseEntity<>(clientService.getClient(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeClient(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.addClient(clientDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.updateClient(clientDTO), HttpStatus.OK);
    }
}
