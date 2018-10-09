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

    
    private Map<String,Evento> eventos;
    private int idEvento = 0;
    
    public EventoServiceImp(){
        eventos = new TreeMap<>();
        eventos.put("Micasa", new Evento(idEvento++,"Micasa","Andujar","Solitario","Hambre mucha hambre",new Date(),1));
    }
    
    @Override
    public List<Evento> BuscaEvento(String busqueda) {
        List<Evento> resultadoBusqueda = new ArrayList<>();
        eventos.values().stream().filter((e) -> (e.getTitulo().contentEquals(busqueda))).forEachOrdered((e) -> {
            resultadoBusqueda.add(e);
        });
      
        return resultadoBusqueda;
    }

    @Override
    public boolean CreaEvento(String titulo, String lugar, Date fecha, String tipo, String descripcion, int aforo) {
        return eventos.put(titulo, new Evento(idEvento++,titulo,lugar,tipo,descripcion,fecha,aforo)) != null;
    }

    @Override
    public boolean BorraEvento(String titulo) {
        return eventos.remove(titulo, eventos.get(titulo));
    }

    @Override
    public void InscribeUsuario(Usuario usuario, String titulo) {
        if(eventos.get(titulo).getAforo() == eventos.get(titulo).tamListaInscritos()){
            eventos.get(titulo).anadirListaEspera(usuario);
        }
        eventos.get(titulo).inscribirUsuario(usuario);
        
    }

    @Override
    public boolean CancelaUsuario(Usuario usuario, String titulo) {
        return eventos.get(titulo).borraUsusario(usuario);
    }
    
}
