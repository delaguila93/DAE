/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.Date;

/**
 *
 * @author delag
 */
public class EventoDTO implements Comparable<EventoDTO> {

    private int idEvento;
    private String titulo, lugar, tipo, descripcion;
    private Date fecha;
    private int aforo;
    private String creador;
    private int usuariosInscritos;
    private int listaEspera;

    public EventoDTO(int idEvento, String titulo, String lugar, String tipo, String descripcion, Date fecha, int aforo, String creador, int usuariosInscritos, int listaEspera) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.lugar = lugar;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.aforo = aforo;
        this.creador = creador;
        this.usuariosInscritos = usuariosInscritos;
        this.listaEspera = listaEspera;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public int getUsuariosInscritos() {
        return usuariosInscritos;
    }

    public void setUsuariosInscritos(int usuariosInscritos) {
        this.usuariosInscritos = usuariosInscritos;
    }

    public int getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(int listaEspera) {
        this.listaEspera = listaEspera;
    }


    @Override
    public int compareTo(EventoDTO o) {
       if(this.fecha.after(o.fecha) ){
           return 1;
       }else if(this.fecha.before(o.fecha)){
           return -1;
       }
       return 0;
    }
    
    

}
