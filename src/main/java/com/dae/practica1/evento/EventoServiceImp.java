/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.evento;

import com.dae.practica1.usuario.Usuario;
import java.util.Date;
import java.util.List;
import java.util.*;
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

    public EventoServiceImp() {
        eventos = new TreeMap<>();
        eventos.put("Micasa", new Evento(idEvento++, "Micasa", "Andujar", "Charla", "Hambre mucha hambre", new Date(), 1));
        tiposEvento = new ArrayList<>(4);
        for(int i=0;i<4;++i){
            tiposEvento.add(new ArrayList<>());
        }
        tiposEvento.get(0).add("Micasa");
    }

    @Override
    public List<Evento> BuscaEvento(String tipo) {
        List<Evento> resultadoBusqueda = new ArrayList<>();
        int posTipo = -1;
        switch (tipo) {
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
            default:
                return null;

        }
        
        for(int i=0;i<tiposEvento.get(posTipo).size();++i){
            resultadoBusqueda.add(eventos.get(tiposEvento.get(posTipo).get(i)));
        }

        return resultadoBusqueda;
    }

    @Override
    public boolean CreaEvento(String titulo, String lugar, Date fecha, String tipo, String descripcion, int aforo, Usuario usuario) {
        if (eventos.get(titulo) != null) {
            return false;
        } else {
            eventos.put(titulo, new Evento(idEvento++, titulo, lugar, tipo, descripcion, fecha, aforo));
            eventos.get(titulo).inscribirUsuario(usuario);
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
            return true;
        }

    }

    @Override
    public boolean BorraEvento(String titulo) {
        return eventos.remove(titulo, eventos.get(titulo));
    }

    @Override
    public void InscribeUsuario(Usuario usuario, String titulo) {
        if (eventos.get(titulo).getAforo() == eventos.get(titulo).tamListaInscritos()) {
            eventos.get(titulo).anadirListaEspera(usuario);
        }
        eventos.get(titulo).inscribirUsuario(usuario);

    }

    @Override
    public boolean CancelaUsuario(Usuario usuario, String titulo) {
        return eventos.get(titulo).borraUsusario(usuario);
    }

}
