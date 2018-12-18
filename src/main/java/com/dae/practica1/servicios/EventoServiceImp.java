/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;


import java.util.List;
import java.util.*;
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
public class EventoServiceImp implements EventoService {

    private int idEvento = 0;
    private Set<Integer> idsCreados;
    @Autowired
    private UsuarioService usuarios;

    @Autowired
    private EventoDAO eventoDAO;

    public EventoServiceImp() {
        idsCreados = new TreeSet<>();
    }

    private int GenerarIdEvento() {
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

    @Override
    @RequestMapping(value = "eventos/{cadena}", method = GET, produces = "application/json")
    public List<EventoDTO> BuscaEvento(@PathVariable("cadena") String cadena) {
        List<EventoDTO> lista = new ArrayList<>();
        List<Evento> lEvento = eventoDAO.BuscaEvento(cadena);
        for (Evento e : lEvento) {
            lista.add(cambioBOtoDTO(e));
        }
        return lista;
    }

    @Override
    @RequestMapping(value = "/evento/{usuario}", method = POST, consumes = "application/json")
    public void CreaEvento(@RequestBody EventoDTO e, @PathVariable("usuario") String usuario) throws EventoNoCreadoException {

        if (e == null) {
            throw new EventoNoCreadoException();
        }
        if (eventoDAO.BuscaTitulo(e.getTitulo()) != null) {
            throw new EventoNoCreadoException();
        }
        Usuario u = usuarios.devuelveUsuario(usuario);

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u != null && u.getUsuario().equals(user.getUsername())) {
            if (idEvento == 0) {
                idEvento = GenerarIdEvento();
            }
            Evento evento = new Evento(idEvento, e.getTitulo(), e.getLugar(), e.getTipo(), e.getDescripcion(), e.getFecha(), e.getAforo(), u);
            eventoDAO.CreaEvento(evento, u.getIdUsuario());
        } else {
            throw new EventoNoCreadoException();
        }

    }

    @Override
    @RequestMapping(value = "/usuarios/{usuario}/eventos/{titulo}", method = DELETE)
    public void BorraEvento(@PathVariable("titulo") String titulo, @PathVariable("usuario") String usuario) throws EventoNoEncontradoException {

        Usuario u = usuarios.devuelveUsuario(usuario);
        Evento e = eventoDAO.BuscaTitulo(titulo);
        if (u != null && u.getUsuario().equals(e.getCreador().getUsuario())) {
            try {
                eventoDAO.BorraEvento(e);
            } catch (Exception ex) {
                throw new EventoNoEncontradoException();
            }
        }
    }

    @Override
    @RequestMapping(value = "/eventos/{titulo}/{usuario}", method = PUT)
    public boolean InscribeUsuario(@PathVariable("titulo") String titulo, @PathVariable("usuario") String usuario) {
        Evento e = eventoDAO.BuscaTitulo(titulo);
        Usuario u = usuarios.devuelveUsuario(usuario);

        if (u != null ) {
            if (e.getAforo() == e.tamListaInscritos()) {
                eventoDAO.anadirListaEspera(e.getIdEvento(), u.getIdUsuario());
                return false;
            } else {
                eventoDAO.anadirInscritos(e.getIdEvento(), u.getIdUsuario());
                return true;
            }
        }
        return false;

    }

    @Override
    @RequestMapping(value = "/eventos/{titulo}/usuarios/{usuario}", method = PUT)
    public void CancelaUsuario(@PathVariable("titulo") String titulo, @PathVariable("usuario") String usuario) {
        Evento e = eventoDAO.BuscaTitulo(titulo);
        Usuario u = usuarios.devuelveUsuario(usuario);

        if (u != null ) {
            eventoDAO.cancelaUsuario(e.getIdEvento(), u.getIdUsuario());
        }
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = "/eventos", method = GET, produces = "application/json")
    public List<EventoDTO> eventosCreados() {
        List<EventoDTO> lista = new ArrayList<>();
        List<Evento> lEvento = eventoDAO.eventosCreados();
        for (Evento e : lEvento) {
            lista.add(cambioBOtoDTO(e));
        }

        return lista;
    }

}
