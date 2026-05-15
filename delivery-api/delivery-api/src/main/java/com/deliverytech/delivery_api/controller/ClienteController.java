package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.req.ClienteReqDTO;
import com.deliverytech.delivery_api.dto.res.ClienteResDTO;
import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResDTO> cadastrarCliente(@Valid @RequestBody ClienteReqDTO dto) {
        ClienteResDTO cliente = clienteService.cadastrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResDTO> buscarPorId(@PathVariable Long id) {
        ClienteResDTO cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResDTO>> listarClientesAtivos() {
        List<ClienteResDTO> clientes = clienteService.listarClientesAtivos();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteReqDTO dto) {
        ClienteResDTO cliente = clienteService.atualizarCliente(id, dto);
        return ResponseEntity.ok(cliente);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ClienteResDTO> ativarDesativarCliente(@PathVariable Long id) {
        ClienteResDTO cliente = clienteService.ativarDesativarCliente(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResDTO> buscarPorEmail(@PathVariable String email) {
        ClienteResDTO cliente = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(cliente);
    }
}