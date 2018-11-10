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

/**
 *
 * @author delag
 */
@Repository
@Transactional

public class EventoDAO {

    @PersistenceContext
    EntityManager em;

    public Evento DevuelveEvento(int idEvento) {
        return em.find(Evento.class, idEvento);
    }

    public void CreaEvento(Evento e) {
        em.persist(e);
    }

    public List<Evento> eventosCreados() {
        List<Evento> eventosCreados = em.createQuery(
                "select * from Evento e",
                Evento.class).getResultList();
        return eventosCreados;
    }

    public List<Evento> BuscaEvento(String cadena) {
        List<Evento> eventosTipo = em.createQuery(
                "select * from Evento e WHERE e.tipo=cadena OR e.titulo LIKE '%cadena%' OR e.descripcion LIKE '%cadena%'",
                Evento.class).setParameter(1, cadena).getResultList();
        return eventosTipo;
    }

    public Evento BuscaTitulo(String cadena) {
        Evento e = em.createQuery(
                "select * from Evento e WHERE e.tipo=cadena OR e.titulo LIKE '%cadena%' OR e.descripcion LIKE '%cadena%'",
                Evento.class).setParameter(1, cadena).getSingleResult();
        return e;
    }

    public int obtenerInscritos(String titulo) {
        int inscritos = em.createQuery("SELECT COUNT uei.usuarios_inscritos_id_usuario FROM usuario_evento_inscritos uei,Evento e WHERE e.idEvento=uei.eventos_inscritos AND e.titulo=titulo")
                .setParameter(1, titulo)
                .getFirstResult();
        return inscritos;
    }

    public int obtenerAforo(String titulo) {
        int aforo = em.createQuery("SELECT aforo FROM Evento e WHERE e.titulotitulo")
                .setParameter(1, titulo)
                .getFirstResult();
        return aforo;
    }

    public void anadirInscritos(int idEvento,int idUsuario){
                em.createQuery("INSERT INTO usuario_evento_inscritos(usuarios_inscritos_id_usuario,eventos_inscritos_id_evento) VALUES (idUsuario,idEvento)")
                .setParameter(1, idUsuario)
                .setParameter(2, idEvento);
    }
    
    public void anadirListaEspera(int idEvento, int idUsuario) {
        Date fecha=new Date();
        em.createQuery("INSERT INTO usuario_evento_esperando(lista_espera_id_usuario,eventos_esperando_id_evento,lista_espera_key) VALUES (idUsuario,idEvento,fecha)")
                .setParameter(1, idUsuario)
                .setParameter(2, idEvento)
                .setParameter(3, fecha);
    }

    public void BorraEvento(Evento e) {
        em.remove(em.merge(e));
    }
    
    public void borraListaEspera(int idEvento){
        em.createQuery("DELETE FROM usuario_evento_esperando WHERE eventos_esperando_id_evento=idEvento ")
                .setParameter(1,idEvento);
    }
        public void borraListaInscritos(int idEvento){
        em.createQuery("DELETE FROM usuario_evento_inscritos WHERE eventos_inscritos_id_evento=idEvento ")
                .setParameter(1,idEvento);
    }

}
