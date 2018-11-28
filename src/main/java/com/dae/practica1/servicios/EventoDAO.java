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
    public void CreaEvento(Evento e, int token) {
        Usuario u = em.find(Usuario.class, token);
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
                "select e from Evento e WHERE e.tipo=?1 OR e.titulo LIKE '%?1%' OR e.descripcion LIKE '%?1%'",
                Evento.class).setParameter(1, cadena).getResultList();
        return eventosTipo;
    }

    @Cacheable(value="eventosTitulo")
    public Evento BuscaTitulo(String cadena) {
        Evento e = em.createQuery(
                "select e from Evento e WHERE  e.titulo=?1",
                Evento.class).setParameter(1, cadena).getSingleResult();
        return e;
    }

    public int ultimoID() {
        int id = toIntExact((long) em.createQuery("Select COUNT(e.idEvento) From Evento e").getSingleResult());
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = EventoNoCreadoException.class)
    public void anadirInscritos(int idEvento, int idUsuario) {
        em.getTransaction().begin();
        Usuario u = em.find(Usuario.class, idUsuario);
        Evento e = em.find(Evento.class, idEvento);
        u.anadirEventoInscrito(e);
        e.inscribirUsuario(u);
        em.merge(e);
     
        
    }

    @Transactional
    public void anadirListaEspera(int idEvento, int idUsuario) {
         em.getTransaction().begin();
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
        for(Usuario u :e.getUsuariosInscritos()){
            u.eliminarEvento(e);
            em.merge(u);
        }
        for(Usuario u :e.getListaEspera()){
            u.eliminarEvento(e);
            em.merge(u);
        }
        e.cancelarEvento();
        em.remove(em.merge(e));
    }

    @Transactional
    public void cancelaUsuario(int idEvento,int idUsuario){
        Usuario u = em.find(Usuario.class, idUsuario);
        Evento e = em.find(Evento.class, idEvento);
        e.borraUsusario(u);
        u.eliminarEvento(e);
        em.merge(e);
        em.merge(u);
    }
    
}
