package com.felipe.helpdesk.config;

import com.felipe.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test") /*aponta para o application-test.properties*/
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean /*faz o metodo subir de forma automatica*/
    /*toda vez que tive com perfil de teste ativo, vai chamar esse metodo que vai subir umas instancias
    * e vai salvar no banco de dados*/
    public void instanciaDB(){
        this.dbService.instanciaDB();
    }

}
