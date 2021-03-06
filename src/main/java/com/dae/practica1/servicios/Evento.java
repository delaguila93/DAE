/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author macosx
 */
@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    private int idEvento;
    private String titulo, lugar, tipo, descripcion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private int aforo;

    @ManyToOne
    @JoinColumn(name = "creador")
    @JsonBackReference
    private Usuario creador;

    @ManyToMany(mappedBy = "eventosInscritos")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Usuario> usuariosInscritos;

    @ManyToMany(mappedBy = "eventosEsperando")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set< Usuario> listaEspera;

    public Evento() {
        this.idEvento = -1;
        this.titulo = "";
        this.lugar = "";
        this.tipo = "";
        this.descripcion = "";
        this.fecha = null;
        this.aforo = -1;
        this.creador = null;
        usuariosInscritos = new TreeSet<>();
        listaEspera = new TreeSet<>();
    }

    public Evento(int idEvento, String titulo, String lugar, String tipo, String descripcion, Date fecha, int aforo, Usuario creador) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.lugar = lugar;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.aforo = aforo;
        this.creador = creador;
        usuariosInscritos = new TreeSet<>();
        listaEspera = new TreeSet<>();
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
        usuariosInscritos.add(usuario);//  Exception in thread "main" java.lang.ClassCastException: com.dae.practica1.servicios.Usuario cannot be cast to java.lang.Comparable
        usuario.anadirEventoInscrito(this);
    }

    public void anadirCreador(Usuario u) {
        u.anadirEventoCreado(this);
    }

    public void anadirListaEspera(Usuario usuario) {
        listaEspera.add(usuario);
        usuario.anadirEnEspera(this);
    }

    /**
     * @return the usuariosInscritos
     */
    public Set<Usuario> getUsuariosInscritos() {
        return usuariosInscritos;
    }

    /**
     * @param usuariosInscritos the usuariosInscritos to set
     */
    public void setUsuariosInscritos(Set<Usuario> usuariosInscritos) {
        this.usuariosInscritos = usuariosInscritos;
    }

    public int tamListaInscritos() {
        return usuariosInscritos.size();
    }

    public void borraUsusario(Usuario usuario) {
        usuariosInscritos.remove(usuario);
        if (listaEspera.size() > 0) {
            Usuario u=listaEspera.iterator().next();
            listaEspera.remove(u);
            usuariosInscritos.add(u);
            u.anadirEventoInscrito(this);
            u.quitarEspera(this);
        }
    }

    /**
     * @return the listaEspera
     */
    public Set< Usuario> getListaEspera() {
        return listaEspera;
    }

    /**
     * @param listaEspera the listaEspera to set
     */
    public void setListaEspera(Set< Usuario> listaEspera) {
        this.listaEspera = listaEspera;
    }
    
    public int tamListaEspera(){
        return listaEspera.size();
    }
    

    /**
     * @return the creador
     */
    public Usuario getCreador() {
        return creador;
    }

    /**
     * @param creador the creador to set
     */
    public void setCreador(Usuario creador) {
        this.creador = creador;
    }
    
    public void cancelarEvento(){
        this.creador = null;
        this.usuariosInscritos.removeAll(usuariosInscritos);
        this.listaEspera.removeAll(listaEspera);
    }
}
