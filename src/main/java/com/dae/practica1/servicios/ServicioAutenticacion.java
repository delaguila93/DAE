/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author delag
 */
@Component
public class ServicioAutenticacion implements UserDetailsService {
    
    
    @Autowired 
    UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario u = usuarioDAO.DevuelveUsuario(usuario);
        if (u == null) {
            throw new UsernameNotFoundException("No se ha encontrado");
        }
        
        
        return User.withUsername(usuario).password(u.getPassword()).roles("USUARIO").build();
    }
}
