/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Evento> EventosInscritos(int token) {
        return em.find(Usuario.class, token).getEventosInscritos();
    }

    @Transactional(readOnly = true)
    public List<Evento> EventosEsperando(int token) {
        return em.find(Usuario.class, token).getEventosEsperando();
    }

    @Transactional(readOnly = true)
    public List<Evento> EventosCreados(int token) {
        return em.find(Usuario.class, token).getEventosCreados();
    }

}
