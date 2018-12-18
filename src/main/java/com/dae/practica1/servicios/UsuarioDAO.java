/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author delag
 */
@Repository
@Transactional
public class UsuarioDAO {

    @PersistenceContext
    EntityManager em;

    public Usuario DevuelveUsuario(int token) {
        return em.find(Usuario.class, token);
    }

    public void RegistraUsuario(Usuario u) {
        em.persist(u);
    }

    public Usuario DevuelveUsuario(String usuario) {
        Usuario usu = em.createQuery(
                "select u from Usuario u WHERE u.usuario=?1",
                Usuario.class).setParameter(1, usuario)
                .getSingleResult();
        return usu;
    }

    @Transactional(readOnly = true)
    public Set<Evento> EventosInscritos(int idUsuario) {
        return em.find(Usuario.class, idUsuario).getEventosInscritos();
    }

    @Transactional(readOnly = true)
    public Set<Evento> EventosEsperando(int idUsuario) {
        return em.find(Usuario.class, idUsuario).getEventosEsperando();
    }

    @Transactional(readOnly = true)
    public Set<Evento> EventosCreados(int idUsuario) {
        return em.find(Usuario.class, idUsuario).getEventosCreados();
    }



}
