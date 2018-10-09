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
import org.springframework.stereotype.Component;


/**
 *
 * @author macosx
 */
@Component
public interface UsuarioService {
    
    public boolean RegistraUsuario(String usuario, String password, String nombre, Date fNac);
    
    public void IdentificaUsuario(String usuario, String password);
    
    public List<Evento> ListaEventosInscritos(String usuario);
    
    public List<Evento> ListaEventosCreados(String usuario);
    
    public Usuario devuelveUsuario(String usuario);
    
    public Map<String,Usuario> usuariosRegistrados();
}
