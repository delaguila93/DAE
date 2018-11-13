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
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author delag
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS)

public class EventoDAO {

    @PersistenceContext
    EntityManager em;

    public Evento DevuelveEvento(int idEvento) {
        return em.find(Evento.class, idEvento);
    }

    @Transactional
    public void CreaEvento(Evento e,int token) {     
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

    public Evento BuscaTitulo(String cadena) {
        Evento e = em.createQuery(
                "select e from Evento e WHERE  e.titulo=?1",
                Evento.class).setParameter(1, cadena).getSingleResult();
        return e;
    }

    public int obtenerInscritos(String titulo) {
        int inscritos = em.createQuery("SELECT COUNT(uei.usuarios_inscritos_id_usuario) FROM usuario_evento_inscritos uei,Evento e WHERE e.idEvento=uei.eventos_inscritos AND e.titulo=?1")
                .setParameter(1, titulo)
                .getFirstResult();
        return inscritos;
    }

    public int ultimoID() {
        int id = toIntExact((long) em.createQuery("Select COUNT(e.idEvento) From Evento e").getSingleResult());
        return id;
    }

    public int obtenerAforo(String titulo) {
        int aforo = em.createQuery("SELECT aforo FROM Evento e WHERE e.titulotitulo=?1")
                .setParameter(1, titulo)
                .getFirstResult();
        return aforo;
    }

    @Transactional(propagation=Propagation.REQUIRED,
            rollbackFor=EventoNoCreadoException.class)
    public void anadirInscritos(int idEvento, int idUsuario) {
        em.getTransaction().begin();
        Usuario u = em.find(Usuario.class, idUsuario);
        Evento e = em.find(Evento.class, idEvento);
        u.anadirEventoInscrito(e);
        e.inscribirUsuario(u);
        em.merge(e);
    }

    public void anadirListaEspera(int idEvento, int idUsuario) {
        Date fecha = new Date();
        em.createNativeQuery("INSERT INTO usuario_evento_esperando(lista_espera_id_usuario,eventos_esperando_id_evento,lista_espera_key) VALUES (?1,?2,?3)")
                .setParameter(1, idUsuario)
                .setParameter(2, idEvento)
                .setParameter(3, fecha);
    }

    public void BorraEvento(Evento e) {
        em.remove(em.merge(e));
    }

    public void borraListaEspera(int idEvento) {
        em.createQuery("DELETE FROM usuario_evento_esperando WHERE eventos_esperando_id_evento=idEvento ")
                .setParameter(1, idEvento);
    }

    public void borraListaInscritos(int idEvento) {
        em.createQuery("DELETE FROM usuario_evento_inscritos WHERE eventos_inscritos_id_evento=idEvento ")
                .setParameter(1, idEvento);
    }

}
