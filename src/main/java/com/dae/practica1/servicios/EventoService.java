/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;


import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author macosx
 */
@Component("eventoService")
public interface EventoService {
    
    public List<EventoDTO> BuscaEvento(String busqueda);
    
    public void CreaEvento(@RequestBody EventoDTO e,@PathVariable("usuario") String usuario) throws EventoNoCreadoException;
    
    public void BorraEvento(@PathVariable ("titulo")String titulo,@PathVariable ("usuario")String usuario) throws EventoNoEncontradoException;
    
    public boolean InscribeUsuario(@PathVariable("titulo") String titulo, @PathVariable ("usuario")String usuario);
    
    public void CancelaUsuario(@PathVariable ("titulo")String titulo,@PathVariable ("usuario")String usuario);
    
    public List<EventoDTO> eventosCreados();
    
    
}
