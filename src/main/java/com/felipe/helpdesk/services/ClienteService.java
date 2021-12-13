package com.felipe.helpdesk.services;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.dtos.ClienteDTO;
import com.felipe.helpdesk.repositories.PessoaRepository;
import com.felipe.helpdesk.repositories.ClienteRepository;
import com.felipe.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.felipe.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service     /*vamos injetar la em Cliente resource nesse projeto*/
public class ClienteService {

    @Autowired
    private ClienteRepository repository;  /*retorna um optional, pois pode encontrar ou nao esse objeto no banco*/
    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id));  /*retorna null caso nao encontre o obj*/
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null); /*para caso vier com id retirar pois no banco e incremental e o banco pode confundir*/
        validaPorCpfeEmail(objDTO);
        /*precisa converter pois ClienteDTO não é uma entidade logo não é salvo no banco*/
        Cliente newObj = new Cliente((objDTO));
        return repository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfeEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado");
        }
        /*senao tiver chamados deleta*/
        repository.deleteById(id);
    }

    private void validaPorCpfeEmail(ClienteDTO objDTO) {
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
