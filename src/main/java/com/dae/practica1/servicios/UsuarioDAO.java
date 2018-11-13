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
        List<Evento> eventosInscritos = em.createQuery(
                "select e FROM Evento e INNER JOIN e.idEvento eventosInscritos WHERE eventosInscritos.idUsuario =?1",
                Evento.class)
                .setParameter(1, token)
                .getResultList();
        return eventosInscritos;
    }

    @Transactional(readOnly = true)
    public List<Evento> EventosEsperando(int token) {
        List<Evento> eventosEsperando = em.createQuery(
                "select e from usuario_eventos_esperando uee ,Evento e WHERE uee.lista_espera_id_usuario=?1",
                Evento.class)
                .setParameter(1, token)
                .getResultList();
        return eventosEsperando;
    }

    @Transactional(readOnly = true)
    public List<Evento> EventosCreados(int token) {
        List<Evento> eventosCreados = em.createQuery(
                "select e from Evento e WHERE e.creador=?1",
                Evento.class)
                .setParameter(1, token)
                .getResultList();
        return eventosCreados;
    }

}
