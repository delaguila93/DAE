/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.cliente;

import com.dae.practica1.evento.EventoService;
import com.dae.practica1.usuario.UsuarioService;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author delag
 */
public class Cliente {

    ApplicationContext context;

    public Cliente(ApplicationContext context) {
        this.context = context;
    }

    public void registrarUsuario(UsuarioService servicioUsusario) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce un nombre de ususario");
        String usuario=scanner.nextLine();
        System.out.println("Introduce una contraseña: ");
        
        System.out.println("");
        System.out.println("1.- Identidficarse");
        System.out.println("2.- Registrarse");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        UsuarioService servicioUsuario = (UsuarioService) context.getBean("usuarioServiceImp");
        EventoService servicioEvento = (EventoService) context.getBean("eventoServiceImp");

        System.out.println("--------- Menu ----------");
        System.out.println("Introduce una opcion: ");
        System.out.println("0.- Salir de la aplicacion");
        System.out.println("1.- Identidficarse");
        System.out.println("2.- Registrarse");
        int opcion = scanner.nextInt();

        if (opcion == 2) {
            registrarUsuario(servicioUsuario);
        }

    }

}
