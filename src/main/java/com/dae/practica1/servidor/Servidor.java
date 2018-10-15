package com.dae.practica1.servidor;


import com.dae.practica1.cliente.Cliente;
import com.dae.practica1.servicios.EventoService;
import com.dae.practica1.servicios.EventoServiceImp;
import com.dae.practica1.servicios.UsuarioService;
import com.dae.practica1.servicios.UsuarioServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author macosx
 */

@SpringBootApplication(scanBasePackages="com.dae.practica1")

public class Servidor {
    
    @Bean
    UsuarioService UsuarioService(){
        UsuarioServiceImp servicio = new UsuarioServiceImp();
        return servicio;
    }
    
    @Bean
    EventoService EventoService(){
        EventoServiceImp servicioEventos = new EventoServiceImp();
        return servicioEventos;
    }
    
    public static void main(String[] args) throws Exception{
        SpringApplication servidor = new SpringApplication(Servidor.class);
        ApplicationContext contexto = servidor.run(args);
        
        Cliente cliente =new Cliente(contexto);
        cliente.run();
        
    }
    
}
