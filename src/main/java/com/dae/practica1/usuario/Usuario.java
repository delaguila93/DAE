/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.usuario;

import com.dae.practica1.evento.Evento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author macosx
 */
public class Usuario {

    private String usuario, nombre, password;
    private Date fNac;
    private List<Evento> eventosInscritos, eventosCreados;

    public Usuario() {

        this.usuario = "";
        this.nombre = "";
        this.password = "";
        this.fNac = null;
        eventosInscritos = new ArrayList<>();
        eventosCreados = new ArrayList<>();
    }

    public Usuario(String usuario, String nombre, String password, Date fNac) {

        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
        this.fNac = fNac;
        eventosInscritos = new ArrayList<>();
        eventosCreados = new ArrayList<>();
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public Date getfNac() {
        return fNac;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfNac(Date fNac) {
        this.fNac = fNac;
    }

    /**
     * @return the eventosInscritos
     */
    public List<Evento> getEventosInscritos() {
        return eventosInscritos;
    }

    /**
     * @param eventosInscritos the eventosInscritos to set
     */
    public void setEventosInscritos(List<Evento> eventosInscritos) {
        this.eventosInscritos = eventosInscritos;
    }

    public void anadirEventoInscrito(Evento e){
        eventosInscritos.add(e);
    }
    
    /**
     * @return the eventosCreados
     */
    public List<Evento> getEventosCreados() {
        return eventosCreados;
    }

    /**
     * @param eventosCreados the eventosCreados to set
     */
    public void setEventosCreados(List<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

}
