package com.felipe.helpdesk.services;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.dtos.TecnicoDTO;
import com.felipe.helpdesk.repositories.PessoaRepository;
import com.felipe.helpdesk.repositories.TecnicoRepository;
import com.felipe.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.felipe.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service     /*vamos injetar la em tecnico resource nesse projeto*/
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;  /*retorna um optional, pois pode encontrar ou nao esse objeto no banco*/
    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id));  /*retorna null caso nao encontre o obj*/
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); /*para caso vier com id retirar pois no banco e incremental e o banco pode confundir*/
        validaPorCpfeEmail(objDTO);
        /*precisa converter pois TecnicoDTO não é uma entidade logo não é salvo no banco*/
        Tecnico newObj = new Tecnico((objDTO));
        return repository.save(newObj);
    }

    private void validaPorCpfeEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        /*isPresent() verifica se ele existe */
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("CPF ja cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail ja cadastrado no sistema");
        }
    }
}
