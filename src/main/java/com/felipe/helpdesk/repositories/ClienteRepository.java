package com.felipe.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.felipe.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
