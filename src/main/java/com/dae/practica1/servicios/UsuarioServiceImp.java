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
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component
public class UsuarioServiceImp implements UsuarioService {

    private Set<Integer> tokenCreados;
    private Map<String, Usuario> usuarios;
    private Map<Integer, String> identificados;
    private int idUsuario = 0;

    public UsuarioServiceImp() {
        usuarios = new TreeMap<>();
        identificados = new TreeMap<>();
        tokenCreados=new TreeSet<>();
        usuarios.put("yosiph", new Usuario(++idUsuario, "yosiph", "yosiph", "Jose", new Date()));
        identificados.put(idUsuario, "yosiph");

    }
    private int GenerarToken(){
        int token=-1;
        Random random=new Random();
        do{
            token = random.nextInt(899999)+100000;
        }while(tokenCreados.contains(token));
        tokenCreados.add(token);
        return token;
    }

    @Override
    public boolean RegistraUsuario(String usuario, String password, String nombre, Date fNac) {
        
        idUsuario=GenerarToken();
        Usuario aux = new Usuario(idUsuario, usuario, nombre, password, fNac);
        Usuario anadido = usuarios.put(usuario, aux);
        

        return anadido != null;
    }

    @Override
    public int IdentificaUsuario(String usuario, String password) {
        int identificado = -1;
        Usuario u = usuarios.get(usuario);
        if (u != null) {
            if (u.getPassword().equals(password)) {
                identificado = u.getIdUsuario();
                identificados.put(identificado, usuario);
            }
        }
        return identificado;
    }

    @Override
    public List<Evento> ListaEventosInscritos(int token) {
        if (comprobarToken(token)) {
            return usuarios.get(identificados.get(token)).getEventosInscritos();
        }
        return null;
    }

    @Override
    public List<Evento> ListaEventosCreados( int token) {
        if (comprobarToken(token)) {
            return usuarios.get(identificados.get(token)).getEventosCreados();
        }
        return null;
    }

    @Override
    public Usuario devuelveUsuario(String usuario) {
        return usuarios.get(usuario);
    }



    @Override
    public List<Evento> ListaEventosEnEspera(int token) {
        if (comprobarToken(token)) {
            return usuarios.get(identificados.get(token)).getEventosEsperando();
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
