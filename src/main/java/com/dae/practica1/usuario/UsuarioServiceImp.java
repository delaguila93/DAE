/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.usuario;

import com.dae.practica1.evento.Evento;
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
    private int idUsuario = 0;
    private int token;

    public UsuarioServiceImp() {
        usuarios = new TreeMap<>();
        usuarios.put("yosiph", new Usuario(idUsuario++, "yosiph", "yosiph", "Jose", new Date()));
        token = -1;
    }

    @Override
    public boolean RegistraUsuario(String usuario, String password, String nombre, Date fNac) {

        Usuario aux = new Usuario(idUsuario++, usuario, nombre, password, fNac);
        Usuario puti = usuarios.put(usuario, aux);
        return puti != null;
    }

    @Override
    public int IdentificaUsuario(String usuario, String password) {
        int identificado = -1;
        if (usuarios.get(usuario) != null) {
            if (usuarios.get(usuario).getPassword().equals(password)) {
                identificado = token = usuarios.get(usuario).getIdUsuario();
                
            }
        }
        return identificado;
    }
    
    @Override
    public List<Evento> ListaEventosInscritos(String usuario) {
        return usuarios.get(usuario).getEventosInscritos();
    }

    @Override
    public List<Evento> ListaEventosCreados(String usuario) {
        return usuarios.get(usuario).getEventosCreados();
    }

    @Override
    public Usuario devuelveUsuario(String usuario) {
        return usuarios.get(usuario);
    }

    @Override
    public Map<String, Usuario> usuariosRegistrados() {

        return usuarios;

    }

}
