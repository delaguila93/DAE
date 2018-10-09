/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.evento;

import com.dae.practica1.usuario.Usuario;
import java.util.*;

/**
 *
 * @author macosx
 */
public class Evento {

    private int idEvento;
    private String titulo, lugar, tipo, descripcion;
    private Date fecha;
    private int aforo;

    private ArrayList<Usuario> usuariosInscritos;
    private LinkedList<Usuario> listaEspera;

    public Evento() {
        this.idEvento = -1;
        this.titulo = "";
        this.lugar = "";
        this.tipo = "";
        this.descripcion = "";
        this.fecha = null;
        this.aforo = -1;
        usuariosInscritos = new ArrayList<>();
        listaEspera = new LinkedList<>();
    }

    public Evento(int idEvento, String titulo, String lugar, String tipo, String descripcion, Date fecha, int aforo) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.lugar = lugar;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.aforo = aforo;
        usuariosInscritos = new ArrayList<>();
        listaEspera = new LinkedList<>();
    }

    /**
     * @return the idEvento
     */
    public int getIdEvento() {
        return idEvento;
    }

    /**
     * @param idEvento the idEvento to set
     */
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the aforo
     */
    public int getAforo() {
        return aforo;
    }

    /**
     * @param aforo the aforo to set
     */
    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public void inscribirUsuario(Usuario usuario) {
        usuariosInscritos.add(usuario);
        for(Usuario u:usuariosInscritos){
            if(u.getUsuario() == null ? usuario.getUsuario() == null : u.getUsuario().equals(usuario.getUsuario())){
                u.anadirEventoInscrito(this);
            }
        }
    }

    public void anadirListaEspera(Usuario ususario) {
        listaEspera.addLast(ususario);
    }

    /**
     * @return the usuariosInscritos
     */
    public ArrayList<Usuario> getUsuariosInscritos() {
        return usuariosInscritos;
    }

    /**
     * @param usuariosInscritos the usuariosInscritos to set
     */
    public void setUsuariosInscritos(ArrayList<Usuario> usuariosInscritos) {
        this.usuariosInscritos = usuariosInscritos;
    }

    public int tamListaInscritos() {
        return usuariosInscritos.size();
    }

    public boolean borraUsusario(Usuario usuario) {
        return usuariosInscritos.remove(usuario);    
    }

    /**
     * @return the listaEspera
     */
    public LinkedList<Usuario> getListaEspera() {
        return listaEspera;
    }

    /**
     * @param listaEspera the listaEspera to set
     */
    public void setListaEspera(LinkedList<Usuario> listaEspera) {
        this.listaEspera = listaEspera;
    }
}
