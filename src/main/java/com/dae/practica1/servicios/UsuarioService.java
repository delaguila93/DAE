/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;


import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * @author macosx
 */
@Component("usuarioService")
public interface UsuarioService {
    
    public void RegistraUsuario(@RequestBody UsuarioDTO usuario)throws UsuarioNoCreadoException;
    
    public Set<EventoDTO> ListaEventosInscritos(String usuario);
    
    public Set<EventoDTO> ListaEventosCreados(String usuario );
    
    public Usuario devuelveUsuario(String usuario);
    
    public Set<EventoDTO> ListaEventosEnEspera(String usuario);
    
    public Usuario devuelveUsuario(int idUsuario);
    
}
