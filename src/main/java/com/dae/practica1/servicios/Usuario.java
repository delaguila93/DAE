/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyTemporal;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.persistence.TemporalType.DATE;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author macosx
 */
@Entity
@Table(name = "Usuario")
public class Usuario implements Comparable<Usuario> {

    @Id
    private int idUsuario;
    private String usuario, nombre, password;
    @Temporal(TemporalType.DATE)
    private Date fNac;

    @OneToMany(mappedBy = "creador")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Evento> eventosCreados;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Evento> eventosInscritos;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Evento> eventosEsperando;

    public Usuario() {
        this.idUsuario = -1;
        this.usuario = "";
        this.nombre = "";
        this.password = "";
        this.fNac = null;
        eventosInscritos = new TreeSet<>();
        eventosCreados = new TreeSet<>();
        eventosEsperando = new TreeSet<>();
    }

    public Usuario(int idUsuario, String usuario, String nombre, String password, Date fNac) {

        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
        this.fNac = fNac;
        eventosInscritos = new TreeSet<>();
        eventosCreados = new TreeSet<>();
        eventosInscritos = new TreeSet<>();
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
    public Set<Evento> getEventosInscritos() {
        return eventosInscritos;
    }

    /**
     * @param eventosInscritos the eventosInscritos to set
     */
    public void setEventosInscritos(Set<Evento> eventosInscritos) {
        this.eventosInscritos = eventosInscritos;
    }

    public void anadirEventoInscrito(Evento e) {
        eventosInscritos.add(e);
    }

    /**
     * @return the eventosEsperando
     */
    public Set<Evento> getEventosEsperando() {
        return eventosEsperando;
    }

    public void anadirEnEspera(Evento e) {
        eventosEsperando.add(e);
    }

    public void quitarEspera(Evento e) {
        eventosEsperando.remove(e);
    }

    /**
     * @return the eventosCreados
     */
    public Set<Evento> getEventosCreados() {
        return eventosCreados;
    }

    /**
     * @param eventosCreados the eventosCreados to set
     */
    public void setEventosCreados(Set<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

    public void anadirEventoCreado(Evento e) {
        eventosCreados.add(e);
    }

    public void eliminarEvento(Evento e) {
        eventosInscritos.remove(e);
        eventosEsperando.remove(e);
        eventosCreados.remove(e);
    }

    @Override
    public int compareTo(Usuario o) {
        if( o.idUsuario > this.idUsuario) {
            return 1;
        }else if(o.idUsuario < this.idUsuario){
            return -1;
        }
        return 0;
    }

}
