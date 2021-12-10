package com.felipe.helpdesk.resources;

import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.dtos.TecnicoDTO;
import com.felipe.helpdesk.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos")  /*endpoint inicial para os endpoints dos tecnicos*/
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    /*ResponseEntity = queremos representar toda a resposta http, é uma boa pratica usa-lo,
    trabalha mehor a segurança da informação*/
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);  /*a requisição da certo e no corpo da resposta vai retornar o listDTO */
    }
                                                /*@Valid - valida para mim os campos que colocamos aqui como not null*/
    @PostMapping                             /*no corpo da requisição deve vir um TecnicoDTO*/
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();  /*uri e tipo o link de acesso ao objeto criado */
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
