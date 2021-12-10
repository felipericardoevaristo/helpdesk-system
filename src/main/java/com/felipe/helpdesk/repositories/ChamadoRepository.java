package com.felipe.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.felipe.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
