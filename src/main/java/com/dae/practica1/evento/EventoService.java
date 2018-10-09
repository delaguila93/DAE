/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.evento;

import com.dae.practica1.usuario.Usuario;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component
public interface EventoService {
    
    public List<Evento> BuscaEvento(String busqueda);
    
    public boolean CreaEvento(String titulo, String lugar, Date fecha, String tipo, String decripcion, int aforo);
    
    public boolean BorraEvento(String titulo);
    
    public void InscribeUsuario(Usuario usuario,String titulo);//No es string es un objeto Usuario para el usuario, lo mismo para Evento
    
    public boolean CancelaUsuario(Usuario usuario,String titulo);//No es string es un objeto Usuario para el usuario, lo mismo para Evento
    
    
    
}
