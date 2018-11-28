package com.dae.practica1.servidor;

import com.dae.practica1.cliente.Cliente;
import com.dae.practica1.servicios.EventoService;
import com.dae.practica1.servicios.EventoServiceImp;
import com.dae.practica1.servicios.UsuarioService;
import com.dae.practica1.servicios.UsuarioServiceImp;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author macosx
 */
@SpringBootApplication(scanBasePackages = "com.dae.practica1")
@EntityScan(basePackages = "com.dae.practica1.servicios")
@EnableCaching
public class Servidor {

    @Bean
    CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(Servidor.class);
        ApplicationContext contexto = servidor.run(args);

        Cliente cliente = new Cliente(contexto);
        cliente.run();

    }

}
