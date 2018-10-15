/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import com.dae.practica1.servicios.Evento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author macosx
 */
public class Usuario {

    private int idUsuario;
    private String usuario, nombre, password;
    private Date fNac;
    private List<Evento> eventosInscritos, eventosCreados, eventosEsperando;

    public Usuario() {
        this.idUsuario = -1;
        this.usuario = "";
        this.nombre = "";
        this.password = "";
        this.fNac = null;
        eventosInscritos = new ArrayList<>();
        eventosCreados = new ArrayList<>();
        eventosEsperando = new ArrayList<>();
    }

    public Usuario(int idUsuario, String usuario, String nombre, String password, Date fNac) {

        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
        this.fNac = fNac;
        eventosInscritos = new ArrayList<>();
        eventosCreados = new ArrayList<>();
        eventosEsperando = new ArrayList<>();
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public void anadirEventoInscrito(Evento e) {
        eventosInscritos.add(e);
    }

    /**
     * @return the eventosEsperando
     */
    public List<Evento> getEventosEsperando() {
        return eventosEsperando;
    }

    public void anadirEnEspera(Evento e) {
        eventosEsperando.add(e);
    }
    
    public void quitarEspera(Evento e){
        eventosEsperando.remove(e);
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
