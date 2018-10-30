/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.servicios;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component("eventoService")
public interface EventoService {
    
    public List<Evento> BuscaEvento(String busqueda);
    
    public void CreaEvento(String titulo, String lugar, Date fecha, String tipo, String decripcion, int aforo,int token,Usuario u) throws EventoNoCreadoException;
    
    public void BorraEvento(String titulo,int token) throws EventoNoEncontradoException;
    
    public boolean InscribeUsuario(Usuario usuario,String titulo);
    
    public void CancelaUsuario(String titulo,int token);
    
    public Map<String,Evento> eventosCreados();
    
    
}
