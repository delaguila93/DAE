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

    Map<String,Usuario> usuarios;
    
    public UsuarioServiceImp(){
        usuarios = new TreeMap<>();
        usuarios.put("yosiph", new Usuario("yosiph","yosiph","Jose",new Date()));
    }
    
    @Override
    public boolean RegistraUsuario(String usuario, String password, String nombre, Date fNac) {
        return usuarios.put(usuario,new Usuario(usuario,nombre,password,fNac)) != null;
    }

    @Override
    public void IdentificaUsuario(String usuario, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
