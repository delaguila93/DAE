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
import java.util.Set;
import org.springframework.stereotype.Component;


/**
 *
 * @author macosx
 */
@Component("usuarioService")
public interface UsuarioService {
    
    public void RegistraUsuario(String usuario, String password, String nombre, Date fNac)throws UsuarioNoCreadoException;
    
    public int IdentificaUsuario(String usuario, String password);
    
    public Set<Evento> ListaEventosInscritos(int token);
    
    public Set<Evento> ListaEventosCreados(int token );
    
    public Usuario devuelveUsuario(String usuario);
    
    public Set<Evento> ListaEventosEnEspera(int token);
    
    public boolean comprobarToken(int token);
    
    public Usuario devuelveUsuario(int token);
    
}
