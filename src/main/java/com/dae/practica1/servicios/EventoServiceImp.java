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
            eventoDAO.CreaEvento(e,token);   
        } else {
            throw new EventoNoCreadoException();
        }

    }

    @Override
    public void BorraEvento(String titulo, int token) throws EventoNoEncontradoException {
        Evento e = eventoDAO.BuscaTitulo(titulo);
        if (usuarios.comprobarToken(token)) {
            eventoDAO.BorraEvento(e);
        } else {
            throw new EventoNoEncontradoException();
        }
    }

    @Override
    public boolean InscribeUsuario(Usuario usuario, String titulo) {

        Evento e = eventoDAO.BuscaTitulo(titulo);
        if (e.getAforo() == e.tamListaInscritos()) {
            eventoDAO.anadirListaEspera(e.getIdEvento(), usuario.getIdUsuario());
            return false;
        }
        e.inscribirUsuario(usuario);
        return true;

    }

    @Override
    public void CancelaUsuario(String titulo, int token) {
        Evento e =eventoDAO.BuscaTitulo(titulo);
        if (usuarios.comprobarToken(token)) {
            eventoDAO.cancelaUsuario(e.getIdEvento(), token);

        }
    }

    @Override
    public List<Evento> eventosCreados() {
        return eventoDAO.eventosCreados();
    }

}
