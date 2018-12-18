/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author macosx
 */
@Component
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE}, maxAge = 18000)
public class UsuarioServiceImp implements UsuarioService {

    private Set<Integer> idsCreados;
    private Map<Integer, String> identificados;
    private int idUsuario = 0;

    @Autowired
    private UsuarioDAO usuarioDAO;

    public UsuarioServiceImp() {
        identificados = new TreeMap<>();
        idsCreados = new TreeSet<>();
    }

    private int GenerarIdUsuario() {
        int token = -1;
        Random random = new Random();
        do {
            token = random.nextInt(899999) + 100000;
        } while (idsCreados.contains(token));
        idsCreados.add(token);
        return token;
    }

    private EventoDTO cambioBOtoDTO(Evento e) {
        return new EventoDTO(e.getIdEvento(), e.getTitulo(), e.getLugar(), e.getTipo(), e.getDescripcion(), e.getFecha(), e.getAforo(), e.getCreador().getUsuario(), e.tamListaInscritos(), e.tamListaEspera());
    }

    protected UsuarioDTO cambioBOtoDTO(Usuario u) {
        return new UsuarioDTO(u.getIdUsuario(), u.getUsuario(), u.getNombre(), u.getPassword(), u.getfNac());
    }

    Usuario cambioDTOtoBO(UsuarioDTO u) {
        return usuarioDAO.DevuelveUsuario(u.getUsuario());
    }

    @Override
    @RequestMapping(value = "usuario/{usuario}", method = POST, consumes = "application/json")
    public void RegistraUsuario(@RequestBody UsuarioDTO usuario) throws UsuarioNoCreadoException {
        if (usuario.getUsuario().length() > 1 && usuario.getPassword().length() > 3 && usuario.getNombre().length() > 2) {
            try {
                idUsuario = GenerarIdUsuario();
                Usuario aux = new Usuario(idUsuario, usuario.getUsuario(), usuario.getNombre(), usuario.getPassword(), usuario.getfNac());
                usuarioDAO.RegistraUsuario(aux);
            } catch (NullPointerException e) {
                throw new UsuarioNoCreadoException();
            }
        } else {
            throw new UsuarioNoCreadoException();
        }

    }

    @Override
    @RequestMapping(value = "/usuarios/{usuario}/inscritos", method = GET, produces = "application/json")
    public Set<EventoDTO> ListaEventosInscritos(@PathVariable("usuario") String usuario) {

        Set<EventoDTO> conjunto = new TreeSet<>();

        int id = usuarioDAO.DevuelveUsuario(usuario).getIdUsuario();
        Set<Evento> inscritos = usuarioDAO.EventosInscritos(id);
        Usuario u = usuarioDAO.DevuelveUsuario(usuario);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u != null && u.getUsuario().equals(user.getUsername())) {
            for (Evento e : inscritos) {
                conjunto.add(cambioBOtoDTO(e));
            }
        }
        return conjunto;
    }

    @Override
    @RequestMapping(value = "/usuarios/{usuario}/creados", method = GET, produces = "application/json")
    public Set<EventoDTO> ListaEventosCreados(@PathVariable("usuario") String usuario) {
        Set<EventoDTO> conjunto = new TreeSet<>();
        int id = usuarioDAO.DevuelveUsuario(usuario).getIdUsuario();

        Set<Evento> creados = usuarioDAO.EventosCreados(id);
        Usuario u = usuarioDAO.DevuelveUsuario(usuario);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u != null && u.getUsuario().equals(user.getUsername())) {
            for (Evento e : creados) {
                conjunto.add(cambioBOtoDTO(e));
            }
        }

        return conjunto;
    }

    @Override
    @RequestMapping(value = "/usuarios/{usuario}/espera", method = GET, produces = "application/json")
    public Set<EventoDTO> ListaEventosEnEspera(@PathVariable("usuario") String usuario) {
        Set<EventoDTO> conjunto = new TreeSet<>();

        int id = usuarioDAO.DevuelveUsuario(usuario).getIdUsuario();
        Usuario u = usuarioDAO.DevuelveUsuario(usuario);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u != null && u.getUsuario().equals(user.getUsername())) {
            Set<Evento> espera = usuarioDAO.EventosEsperando(id);
            for (Evento e : espera) {
                conjunto.add(cambioBOtoDTO(e));
            }
        }
        return conjunto;

    }

    @Override
    public Usuario devuelveUsuario(String usuario) {
        return usuarioDAO.DevuelveUsuario(usuario);
    }

    @Override
    public Usuario devuelveUsuario(int idUsuario) {
        return usuarioDAO.DevuelveUsuario(idUsuario);
    }

}
