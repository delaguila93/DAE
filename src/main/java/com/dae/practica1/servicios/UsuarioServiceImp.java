/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import com.dae.practica1.servicios.Evento;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component
public class UsuarioServiceImp implements UsuarioService {

    private Map<String, Usuario> usuarios;
    private Map<Integer, String> identificados;
    private int idUsuario = 0;

    public UsuarioServiceImp() {
        usuarios = new TreeMap<>();
        identificados = new TreeMap<>();
        usuarios.put("yosiph", new Usuario(++idUsuario, "yosiph", "yosiph", "Jose", new Date()));
        identificados.put(idUsuario, "yosiph");

    }

    @Override
    public boolean RegistraUsuario(String usuario, String password, String nombre, Date fNac) {

        Usuario aux = new Usuario(++idUsuario, usuario, nombre, password, fNac);
        Usuario anadido = usuarios.put(usuario, aux);

        return anadido != null;
    }

    @Override
    public int IdentificaUsuario(String usuario, String password) {
        int identificado = -1;
        if (usuarios.get(usuario) != null) {
            if (usuarios.get(usuario).getPassword().equals(password)) {
                identificado = usuarios.get(usuario).getIdUsuario();
                identificados.put(usuarios.get(usuario).getIdUsuario(), usuario);
            }
        }
        return identificado;
    }

    @Override
    public List<Evento> ListaEventosInscritos(String usuario, int token) {
        if (comprobarToken(token)) {
            return usuarios.get(usuario).getEventosInscritos();
        }
        return null;
    }

    @Override
    public List<Evento> ListaEventosCreados(String usuario, int token) {
        if (comprobarToken(token)) {
            return usuarios.get(usuario).getEventosCreados();
        }
        return null;
    }

    @Override
    public Usuario devuelveUsuario(String usuario) {
        return usuarios.get(usuario);
    }



    @Override
    public List<Evento> ListaEventosEnEspera(String usuario, int token) {
        if (comprobarToken(token)) {
            return usuarios.get(usuario).getEventosEsperando();
        }
        return null;
    }

    @Override
    public boolean comprobarToken(int token) {
        return identificados.containsKey(token);
    }

    @Override
    public Usuario devuelveUsuario(int token) {
        return usuarios.get(identificados.get(token));
    }
    
    

}
