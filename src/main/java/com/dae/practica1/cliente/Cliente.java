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
    private int token;

    public Cliente(ApplicationContext context) {
        token = -1;
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

    public void crearEvento(EventoService servicioEvento, Usuario usuario,UsuarioService servicioUsuario) throws ParseException {
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

        servicioEvento.CreaEvento(titulo, lugar, fechaNac, tipo, descripcion, aforo, token,servicioUsuario);

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
            token = usuarioService.IdentificaUsuario(usuario, password);
            System.out.println("Usuario identificado con exito");

        } else {

            System.out.println("Credenciales incorrectas");
        }
    }

    public void cancelaEvento(UsuarioService servicioUsuario, String usuario, EventoService servicioEvento) {
        
        Usuario usu = servicioUsuario.devuelveUsuario(usuario);
        Scanner scanner = new Scanner(System.in);
        String titulo;

        for (Evento e : usu.getEventosCreados()) {
            System.out.println("Nombre de evento: " + e.getTitulo());
            System.out.println("Descripcion: " + e.getDescripcion());
            System.out.println("Fecha del evento: " + e.getLugar());
            System.out.println("Tipo de evento" + e.getTipo());
        }

        System.out.print("Introduce el nombre del evento que vas a cancelar: ");
        titulo = scanner.nextLine();
        if (servicioEvento.BorraEvento(titulo,token,servicioUsuario)) {
            System.out.println("Evento borrado correctamente");

        } else {
            System.out.println("Fallo a la hora de borrar el evento");
        }

    }

    public void inscribirUsuario(EventoService servicioEvento, String usuario, UsuarioService servicioUsuario) {
        Scanner scanner = new Scanner(System.in);
        String titulo;

        for (Map.Entry<String, Evento> salida : servicioEvento.eventosCreados().entrySet()) {
            System.out.println("------");
            System.out.println("Nombre de ususario: " + salida.getValue().getTitulo());
            System.out.println("Nombre: " + salida.getValue().getDescripcion());
            System.out.println("Fecha del evento: " + salida.getValue().getFecha().toString());
            System.out.println("Tipo de evento: " + salida.getValue().getTipo());
        }

        System.out.print("Introduce el titulo del evento: ");
        titulo = scanner.nextLine();

        if (servicioEvento.InscribeUsuario(servicioUsuario.devuelveUsuario(usuario), titulo)) {
            System.out.println("Usuario inscrito en el evento");
        } else {
            System.out.println("Usuario añadido a la lista de espera");
        }
    }

    public void cancelarInscripcion(EventoService servicioEvento, UsuarioService servicioUsuario, String usuario) {
        Scanner scanner = new Scanner(System.in);
        String titulo;

        for (Evento e : servicioUsuario.ListaEventosInscritos(usuario,token)) {
            System.out.println("------");
            System.out.println("Nombre de usuario: " + e.getTitulo());
            System.out.println("Nombre: " + e.getDescripcion());
            System.out.println("Fecha del evento: " + e.getFecha().toString());
            System.out.println("Tipo de evento: " + e.getTipo());
        }

        System.out.print("Introduce el titulo del evento: ");
        titulo = scanner.nextLine();

        servicioEvento.CancelaUsuario(servicioUsuario.devuelveUsuario(usuario), titulo,servicioUsuario,token);
        System.out.println("Inscripcion cancelada al evento.");

    }

    public void listarEventosCreados(UsuarioService servicioUsuario, String usuario) {
        for (Evento e : servicioUsuario.ListaEventosCreados(usuario,token)) {
            System.out.println("------");
            System.out.println("Nombre de usuario: " + e.getTitulo());
            System.out.println("Nombre: " + e.getDescripcion());
            System.out.println("Fecha del evento: " + e.getFecha().toString());
            System.out.println("Tipo de evento: " + e.getTipo());
        }
    }

    public void listarEventosInscritos(UsuarioService servicioUsuario, String usuario) {
        System.out.println("--Eventos en los que estas inscrito--");

        for (Evento e : servicioUsuario.ListaEventosInscritos(usuario,token)) {
            System.out.println("------");
            System.out.println("Nombre de usuario: " + e.getTitulo());
            System.out.println("Nombre: " + e.getDescripcion());
            System.out.println("Fecha del evento: " + e.getFecha().toString());
            System.out.println("Tipo de evento: " + e.getTipo());
        }

        System.out.println("--Eventos en los que estas en lista de espera--");

        for (Evento e : servicioUsuario.ListaEventosEnEspera(usuario,token)) {
            System.out.println("------");
            System.out.println("Nombre de usuario: " + e.getTitulo());
            System.out.println("Nombre: " + e.getDescripcion());
            System.out.println("Fecha del evento: " + e.getFecha().toString());
            System.out.println("Tipo de evento: " + e.getTipo());
        }
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
            System.out.println("6.- Cancelar evento");
            System.out.println("7.- Inscribirse a un evento");
            System.out.println("8.- Cancelar inscripcion a un evento");

            System.out.print("Introduce una opcion: ");
            opcion = scanner.nextInt();

            String usuario = "yosiph", password = "Jose";
            switch (opcion) {
                case 1: {

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

                    crearEvento(servicioEvento, servicioUsuario.devuelveUsuario(usuario),servicioUsuario);
                }
                break;
                case 6:
                    cancelaEvento(servicioUsuario, usuario, servicioEvento);
                    break;
            }

        } while (opcion != 0);
    }

}
