package com.felipe.helpdesk.config;

import com.felipe.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") /*aponta para o application-test.properties*/
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String value;

    @Bean /*faz o metodo subir de forma automatica*/
    /*toda vez que tive com perfil de teste ativo, vai chamar esse metodo que vai subir umas instancias
    * e vai salvar no banco de dados*/
    public boolean instanciaDB(){
        if(value.equals("create")) {
            this.dbService.instanciaDB();
        }
        return false;
    }

}
