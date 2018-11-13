/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.Date;
import java.util.List;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component
public class EventoServiceImp implements EventoService {

    private Map<String, Evento> eventos;
    private List<List<String>> tiposEvento;//pos 0= charla, pos 1 =curso,pos 2= actividad deportiva, pos 3=visita cultiral.
    private int idEvento = 0;

    @Autowired
    private UsuarioService usuarios;

    @Autowired
    private EventoDAO eventoDAO;

    public EventoServiceImp() {
        eventos = new TreeMap<>();
        //eventos.put("Micasa", new Evento(idEvento++, "Micasa", "Andujar", "Charla", "Hambre mucha hambre", new Date(), 1, new Usuario()));
        tiposEvento = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            tiposEvento.add(new ArrayList<>());
        }
        tiposEvento.get(0).add("Micasa");
    }

    @Override
    public List<Evento> BuscaEvento(String cadena) {
        return eventoDAO.BuscaEvento(cadena);
    }

    @Override
    public void CreaEvento(String titulo, String lugar, Date fecha, String tipo, String descripcion, int aforo, int token) throws EventoNoCreadoException {
        Usuario u = usuarios.devuelveUsuario(token);
        if (idEvento == 0) {
            idEvento = eventoDAO.ultimoID();
        }
        Evento e = new Evento(++idEvento, titulo, lugar, tipo, descripcion, fecha, aforo, u);
        if (usuarios.comprobarToken(token)) {

            eventoDAO.CreaEvento(e);
            eventoDAO.anadirInscritos(idEvento, token);     
        } else {
            throw new EventoNoCreadoException();
        }

    }

    @Override
    public void BorraEvento(String titulo, int token) throws EventoNoEncontradoException {
        Evento e = eventoDAO.BuscaTitulo(titulo);
        if (usuarios.comprobarToken(token)) {
            eventoDAO.borraListaEspera(e.getIdEvento());
            eventoDAO.borraListaInscritos(e.getIdEvento());
            eventoDAO.BorraEvento(e);
        } else {
            throw new EventoNoEncontradoException();
        }
    }

    @Override
    public boolean InscribeUsuario(Usuario usuario, String titulo) {

        Evento e = eventoDAO.BuscaTitulo(titulo);
        if (eventoDAO.obtenerAforo(titulo) == eventoDAO.obtenerInscritos(titulo)) {
            eventoDAO.anadirListaEspera(e.getIdEvento(), idEvento);
            return false;
        }
        e.inscribirUsuario(usuario);
        return true;

    }

    @Override
    public void CancelaUsuario(String titulo, int token) {
        if (usuarios.comprobarToken(token)) {
            eventos.get(titulo).borraUsusario(usuarios.devuelveUsuario(token));
        }
    }

    @Override
    public Map<String, Evento> eventosCreados() {
        return eventos;
    }

}
