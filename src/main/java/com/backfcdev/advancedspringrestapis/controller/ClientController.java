package com.backfcdev.advancedspringrestapis.controller;

import com.backfcdev.advancedspringrestapis.dto.ClientDTO;
import com.backfcdev.advancedspringrestapis.model.Client;
import com.backfcdev.advancedspringrestapis.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final IClientService clientService;
    private final ModelMapper mapper;

    @GetMapping
    ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> clients = clientService.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    ResponseEntity<ClientDTO> save(@RequestBody ClientDTO clientDTO){
        Client client = convertToEntity(clientDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertToDto(clientService.save(client)));
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(clientService.findById(id)));
    }

    // Hateoas - Nivel 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<ClientDTO> findByIdHateoas(@PathVariable Integer id){
        EntityModel<ClientDTO> resource = EntityModel.of(this.convertToDto(clientService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("client-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientDTO clientDTO){
        clientDTO.setId(id);
        Client client = clientService.update(id, convertToEntity(clientDTO));
        return ResponseEntity.ok(convertToDto(client));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id){
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ClientDTO convertToDto(Client dto){
        return mapper.map(dto, ClientDTO.class);
    }
    public Client convertToEntity(ClientDTO entity){
        return mapper.map(entity, Client.class);
    }
}
