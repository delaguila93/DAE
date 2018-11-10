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
                "select * from Usuario u WHERE u.usuario=usuario",
                Usuario.class).setParameter(1, usuario)
                .getSingleResult();
        return usu;
    }

    public List<Evento> EventosInscritos(int token) {
        List<Evento> eventosInscritos = em.createQuery(
                "select e.* from usuario_eventos_inscritos uei ,Evento e WHERE uei.usuarios_inscritos_id_usuario=token",
                Evento.class)
                .setParameter(1, token)
                .getResultList();
        return eventosInscritos;
    }

    public List<Evento> EventosEsperando(int token) {
        List<Evento> eventosEsperando = em.createQuery(
                "select e.* from usuario_eventos_esperando uee ,Evento e WHERE uee.lista_espera_id_usuario=token",
                Evento.class)
                .setParameter(1, token)
                .getResultList();
        return eventosEsperando;
    }

    public List<Evento> EventosCreados(int token) {
        List<Evento> eventosCreados = em.createQuery(
                "select e.* from Evento e WHERE e.creador=token",
                Evento.class)
                .setParameter(1, token)
                .getResultList();
        return eventosCreados;
    }

}
