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
    // @Qualifier("usuarioService")
    private UsuarioService usuarios;

    public EventoServiceImp() {
        eventos = new TreeMap<>();
        eventos.put("Micasa", new Evento(idEvento++, "Micasa", "Andujar", "Charla", "Hambre mucha hambre", new Date(), 1));
        tiposEvento = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            tiposEvento.add(new ArrayList<>());
        }
        tiposEvento.get(0).add("Micasa");

    }

    @Override
    public List<Evento> BuscaEvento(String cadena) {
        List<Evento> resultadoBusqueda = new ArrayList<>();
        int posTipo = -1;
        switch (cadena) {
            case "Charla":
                posTipo = 0;
                break;
            case "Curso":
                posTipo = 1;
                break;
            case "Actividad deportiva":
                posTipo = 2;
                break;
            case "Visita cultutal":
                posTipo = 3;
                break;

        }

        for (Map.Entry<String, Evento> salida : eventos.entrySet()) {
            if (salida.getKey().contains(cadena) || salida.getValue().getDescripcion().contains(cadena)) {
                resultadoBusqueda.add(salida.getValue());
            }
        }

        if (posTipo != -1) {
            for (int i = 0; i < tiposEvento.get(posTipo).size(); ++i) {
                resultadoBusqueda.add(eventos.get(tiposEvento.get(posTipo).get(i)));
            }
        }
        return resultadoBusqueda;
    }

    @Override
    public void CreaEvento(String titulo, String lugar, Date fecha, String tipo, String descripcion, int aforo, int token, Usuario u) throws EventoNoCreadoException {
        Evento e = eventos.get(titulo);
        if (usuarios.comprobarToken(token)) {
            if (e != null) {
                throw new EventoNoCreadoException();
            } else {
                eventos.put(titulo, new Evento(idEvento++, titulo, lugar, tipo, descripcion, fecha, aforo));
                e.inscribirUsuario(u);
                e.anadirCreador(u);
                switch (tipo) {
                    case "Charla":
                        tiposEvento.get(0).add(titulo);
                        break;
                    case "Curso":
                        tiposEvento.get(1).add(titulo);
                        break;
                    case "Actividad deportiva":
                        tiposEvento.get(2).add(titulo);
                        break;
                    case "Visita cultutal":
                        tiposEvento.get(3).add(titulo);
                        break;
                }
            }
        } else {
            throw new EventoNoCreadoException();
        }

    }

    @Override
    public void BorraEvento(String titulo, int token) throws EventoNoEncontradoException{
        Evento e = eventos.get(titulo);
        if (usuarios.comprobarToken(token)) {
            switch (e.getTipo()) {

                case "Charla":
                    tiposEvento.get(0).remove(titulo);
                    break;
                case "Curso":
                    tiposEvento.get(1).remove(titulo);
                    break;
                case "Actividad deportiva":
                    tiposEvento.get(2).remove(titulo);
                    break;
                case "Visita cultutal":
                    tiposEvento.get(3).remove(titulo);
                    break;
            }
            for (Usuario u : e.getUsuariosInscritos()) {
                u.eliminarEvento(e);
            }
            eventos.remove(titulo, e);
        }else{
            throw new EventoNoEncontradoException();
        }
    }

    @Override
    public boolean InscribeUsuario(Usuario usuario, String titulo) {
        Evento e = eventos.get(titulo);
        if (e.getAforo() == e.tamListaInscritos()) {
            e.anadirListaEspera(usuario);
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
