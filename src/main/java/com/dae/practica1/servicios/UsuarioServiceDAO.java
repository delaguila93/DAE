/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author delag
 */
@Repository
@Transactional
public class UsuarioServiceDAO implements UsuarioService {

    @PersistenceContext
    EntityManager em;

    int idUsuario;
    private Set<Integer> tokenCreados;

    private int GenerarToken() {
        int token = -1;
        Random random = new Random();
        do {
            token = random.nextInt(899999) + 100000;
        } while (tokenCreados.contains(token));
        tokenCreados.add(token);
        return token;
    }

    @Override
    public void RegistraUsuario(String usuario, String password, String nombre, Date fNac) throws UsuarioNoCreadoException {
        if (usuario.length() > 1 && password.length() > 3 && nombre.length() > 2) {
            try {
                idUsuario = GenerarToken();
                Usuario aux = new Usuario(idUsuario, usuario, nombre, password, fNac);

                em.persist(aux);
            } catch (Exception e) {
                throw new UsuarioNoCreadoException();
            }
        } else {
            throw new UsuarioNoCreadoException();
        }
    }

    @Override
    public int IdentificaUsuario(String usuario, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> ListaEventosInscritos(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> ListaEventosCreados(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario devuelveUsuario(String usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> ListaEventosEnEspera(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean comprobarToken(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario devuelveUsuario(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
