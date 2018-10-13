/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.cliente;

import com.dae.practica1.evento.Evento;
import com.dae.practica1.evento.EventoService;
import com.dae.practica1.usuario.Usuario;
import com.dae.practica1.usuario.UsuarioService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public void mostrarUsusarios(UsuarioService servicioUsuario) {

        for (Map.Entry<String, Usuario> salida : servicioUsuario.usuariosRegistrados().entrySet()) {
            System.out.println("Nombre de ususario: " + salida.getValue().getUsuario());
            System.out.println("Nombre: " + salida.getValue().getNombre());
            System.out.println("Fecha de nacimiento4"
                    + ": " + salida.getValue().getfNac().toString());
        }

    }

    public void registrarUsuario(UsuarioService servicioUsusario) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("DD/MM/YYYY");

        System.out.print("Introduce un nombre de usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Introduce una contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Introduce un nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce tu fecha de nacimiento formato DD/MM/YYYY: ");
        String fecha = scanner.nextLine();
        Date fechaNac = format.parse(fecha);

        boolean insertado = servicioUsusario.RegistraUsuario(usuario, password, nombre, fechaNac);

        if (insertado) {
            System.out.println("Inserccion correcta");
        }

    }

    public void crearEvento(EventoService servicioEvento, Usuario usuario) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("DD/MM/YYYY");

        System.out.print("Introduce un titulo: ");
        String titulo = scanner.nextLine();

        System.out.print("Introduce un lugar: ");
        String lugar = scanner.nextLine();

        System.out.print("Introduce la fecha (DD/MM/YYYY): ");
        String fecha = scanner.nextLine();
        Date fechaNac = format.parse(fecha);

        System.out.print("Introduce un tipo(Charla,curso,Actividad deportiva,Visita cultural): ");
        String tipo = scanner.nextLine();

        System.out.print("Introduce una descripcion: ");
        String descripcion = scanner.nextLine();

        System.out.print("Introduce un aforo: ");
        int aforo = scanner.nextInt();

        servicioEvento.CreaEvento(titulo, lugar, fechaNac, tipo, descripcion, aforo, usuario);

    }

    public void buscarEvento(EventoService servicioEvento) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el tipo de evento: ");
        String tipo = scanner.nextLine();

        List<Evento> resultado = servicioEvento.BuscaEvento(tipo);
        for (Evento e : resultado) {
            System.out.println(e.getTitulo());
        }

    }

    public void identificarUsuario(UsuarioService usuarioService, String usuario, String password) {

        if (usuarioService.IdentificaUsuario(usuario, password) != -1) {

            System.out.println("Usuario identificado con exito");

        } else {

            System.out.println("Credenciales incorrectas");
        }
    }

    public void crearEvento(EventoService servicioEvento) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("DD/MM/YYYY");

        System.out.print("Introduce un nombre de usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Introduce una contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Introduce un nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce tu fecha de nacimiento formato DD/MM/YYYY: ");
        String fecha = scanner.nextLine();
        Date fechaNac = format.parse(fecha);

    }

  

    
    

    public void run() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        UsuarioService servicioUsuario = (UsuarioService) context.getBean("usuarioServiceImp");
        EventoService servicioEvento = (EventoService) context.getBean("eventoServiceImp");
        do {
            System.out.println("--------- Menu ----------");
            System.out.println("Introduce una opcion: ");
            System.out.println("0.- Salir de la aplicacion");
            System.out.println("1.- Identidficarse");
            System.out.println("2.- Registrarse");
            System.out.println("3.- Mostrar ususarios registrados");
            System.out.println("4.- Buscar eventos(tipo)");
            System.out.println("5.- Crear evento");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: {
                    String usuario = "yosiph", password = "Jose";

//                    System.out.print("Introduce usuario:");
//                    usuario = scanner.nextLine();
//
//                    System.out.print("Introduce Contraseña:");
//                    password = scanner.nextLine();
                    identificarUsuario(servicioUsuario, usuario, password);
                }
                break;
                case 2:
                    registrarUsuario(servicioUsuario);
                    break;
                case 3:
                    mostrarUsusarios(servicioUsuario);
                    break;
                case 4:
                    buscarEvento(servicioEvento);
                    break;
                case 5: {
                    String usuario="yosiph";
                    crearEvento(servicioEvento, servicioUsuario.devuelveUsuario(usuario));
                }
                break;

            }

        } while (opcion != 0);
    }

}
