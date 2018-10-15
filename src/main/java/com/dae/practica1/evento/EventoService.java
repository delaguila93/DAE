/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.evento;

import com.dae.practica1.usuario.Usuario;
import com.dae.practica1.usuario.UsuarioService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component
public interface EventoService {
    
    public List<Evento> BuscaEvento(String busqueda);
    
    public boolean CreaEvento(String titulo, String lugar, Date fecha, String tipo, String decripcion, int aforo,int token,UsuarioService usuarios);
    
    public boolean BorraEvento(String titulo,int token, UsuarioService usuarios);
    
    public boolean InscribeUsuario(Usuario usuario,String titulo);//No es string es un objeto Usuario para el usuario, lo mismo para Evento
    
    public void CancelaUsuario(Usuario usuario,String titulo, UsuarioService usuarios,int token);//No es string es un objeto Usuario para el usuario, lo mismo para Evento
    
    public Map<String,Evento> eventosCreados();
    
    
}
