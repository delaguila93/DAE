/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static java.lang.Math.toIntExact;
import javax.persistence.NoResultException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author delag
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public class EventoDAO {

    @PersistenceContext
    EntityManager em;

    public Evento DevuelveEvento(int idEvento) {
        return em.find(Evento.class, idEvento);
    }

    @Transactional
    public void CreaEvento(Evento e, int idUsuario) {
        Usuario u = em.find(Usuario.class, idUsuario);
        e.inscribirUsuario(u);
        em.persist(e);
    }

    @Transactional(readOnly = true)

    public List<Evento> eventosCreados() {
        List<Evento> eventosCreados = em.createQuery(
                "select e from Evento e",
                Evento.class).getResultList();
        return eventosCreados;
    }

    public List<Evento> BuscaEvento(String cadena) {
        List<Evento> eventosTipo = em.createQuery(
                "select e from Evento e WHERE e.tipo=?1 OR e.titulo LIKE ?2 OR e.descripcion LIKE ?2",
                Evento.class).setParameter(1, cadena).setParameter(2, "%" + cadena + "%")
                .getResultList();
        return eventosTipo;
    }

    @Cacheable(value = "eventosTitulo")
    public Evento BuscaTitulo(String cadena) {
        Evento e;
        try {
            e = em.createQuery(
                    "select e from Evento e WHERE  e.titulo=?1",
                    Evento.class).setParameter(1, cadena).getSingleResult();
        } catch (NoResultException ex) {
            e = null;
        }
        return e;
    }



    @Transactional
    public void anadirInscritos(int idEvento, int idUsuario) {
        
        Usuario u = em.find(Usuario.class, idUsuario);
        Evento e = em.find(Evento.class, idEvento);
        e.inscribirUsuario(u);
        em.merge(u);
        em.merge(e);

    }

    @Transactional
    public void anadirListaEspera(int idEvento, int idUsuario) {
       
        Usuario u = em.find(Usuario.class, idUsuario);
        Evento e = em.find(Evento.class, idEvento);
        e.anadirListaEspera(u);
        em.merge(e);
        em.merge(u);
    }

    @Transactional
    public void BorraEvento(Evento e) {
        Usuario creador = e.getCreador();
        creador.eliminarEvento(e);
        em.merge(creador);
        for (Usuario u : e.getUsuariosInscritos()) {
            u.eliminarEvento(e);
            em.merge(u);
        }
        for (Usuario u : e.getListaEspera()) {
            u.eliminarEvento(e);
            em.merge(u);
        }
        e.cancelarEvento();
        em.remove(em.merge(e));
    }

    @Transactional
    public void cancelaUsuario(int idEvento, int idUsuario) {
        Usuario u = em.find(Usuario.class, idUsuario);
        Evento e = em.find(Evento.class, idEvento);
        e.borraUsusario(u);
        u.eliminarEvento(e);
        em.merge(e);
        em.merge(u);
    }

}
