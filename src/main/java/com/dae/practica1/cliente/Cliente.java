/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.practica1.cliente;

import com.dae.practica1.servicios.Evento;
import com.dae.practica1.servicios.EventoService;
import com.dae.practica1.servicios.Usuario;
import com.dae.practica1.servicios.UsuarioService;
import com.dae.practica1.servicios.UsuarioNoCreadoException;
import com.dae.practica1.servicios.EventoNoCreadoException;
import com.dae.practica1.servicios.EventoNoEncontradoException;
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

    public void registrarUsuario(UsuarioService servicioUsusario) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Introduce un nombre de usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Introduce una contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Introduce un nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce tu fecha de nacimiento formato DD/MM/YYYY: ");
        String fecha = scanner.nextLine();
        Date fechaNac = format.parse(fecha);
        try {
            servicioUsusario.RegistraUsuario(usuario, password, nombre, fechaNac);
            
        System.out.println("Insercion correcta");
        } catch (UsuarioNoCreadoException e) {
            System.out.println("Insercion no realizada");
        }

    }

    public void crearEvento(EventoService servicioEvento, Usuario usuario, UsuarioService servicioUsuario) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Introduce un titulo: ");
        String titulo = scanner.nextLine();

        System.out.print("Introduce un lugar: ");
        String lugar = scanner.nextLine();

        System.out.print("Introduce la fecha (DD/MM/YYYY): ");
        String fecha = scanner.nextLine();
        Date fechaNac = format.parse(fecha);

        String tipo;
        do {
            System.out.print("Introduce un tipo(Charla,Curso,Actividad deportiva,Visita cultural): ");
            tipo = scanner.nextLine();
        } while (!"Charla".equals(tipo) && !"Curso".equals(tipo) && !"Actividad deportiva".equals(tipo) && !"Visita cultural".equals(tipo));

        System.out.print("Introduce una descripcion: ");
        String descripcion = scanner.nextLine();

        System.out.print("Introduce un aforo: ");
        int aforo = scanner.nextInt();

        try{
            servicioEvento.CreaEvento(titulo, lugar, fechaNac, tipo, descripcion, aforo, token);             
            System.out.println("Evento creado satisfactoriamente.");
        } catch(EventoNoCreadoException e) {
            System.out.println("El evento no ha podido ser creado.");
        }

    }

    public void buscarEvento(EventoService servicioEvento) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce lo que deseas buscar: ");
        String tipo = scanner.nextLine();

        List<Evento> resultado = servicioEvento.BuscaEvento(tipo);
        for (Evento e : resultado) {
            System.out.println("***********");
            System.out.println("Título del evento: " + e.getTitulo());
            System.out.println("Día:" + e.getFecha().toString());
            System.out.println("Tipo: " + e.getTipo());
            System.out.println("Descripcion: " + e.getDescripcion());
//            System.out.println("Aforo restante: " + (e.getAforo() - e.getUsuariosInscritos().size()));
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
       try{
           servicioEvento.BorraEvento(titulo, token);
            System.out.println("Evento borrado correctamente");

        } catch(EventoNoEncontradoException e) {
            System.out.println("Fallo a la hora de borrar el evento");
        }

    }

    public void inscribirUsuario(EventoService servicioEvento, int token, UsuarioService servicioUsuario) {
        Scanner scanner = new Scanner(System.in);
        String titulo;

        for (Map.Entry<String, Evento> salida : servicioEvento.eventosCreados().entrySet()) {
            System.out.println("------");
            System.out.println("Titulo evento: " + salida.getValue().getTitulo());
            System.out.println("descripcion: " + salida.getValue().getDescripcion());
            System.out.println("Fecha: " + salida.getValue().getFecha().toString());
            System.out.println("Tipo de evento: " + salida.getValue().getTipo());
        }

        System.out.print("Introduce el titulo del evento: ");
        titulo = scanner.nextLine();

        if (servicioEvento.InscribeUsuario(servicioUsuario.devuelveUsuario(token), titulo)) {
            System.out.println("Usuario inscrito en el evento");
        } else {
            System.out.println("Usuario añadido a la lista de espera");
        }
    }

    public void cancelarInscripcion(EventoService servicioEvento, UsuarioService servicioUsuario) {
        Scanner scanner = new Scanner(System.in);
        String titulo;

        for (Evento e : servicioUsuario.ListaEventosInscritos(token)) {

            System.out.println("Titulo evento: " + e.getTitulo());
            System.out.println("Fecha: " + e.getFecha().toString());
            System.out.println("Tipo de evento: " + e.getTipo());
        }

        System.out.print("Introduce el titulo del evento: ");
        titulo = scanner.nextLine();

        servicioEvento.CancelaUsuario(titulo, token);
        System.out.println("Inscripcion cancelada al evento.");

    }

    public void listarEventosCreados(UsuarioService servicioUsuario, String usuario) {
        Usuario usu = servicioUsuario.devuelveUsuario(usuario);
        if (!usu.getEventosCreados().isEmpty()) {
            for (Evento e : usu.getEventosCreados()) {
                System.out.println("------");
                System.out.println("Titulo evento: " + e.getTitulo());
                System.out.println("descripcion: " + e.getDescripcion());
                System.out.println("Fecha: " + e.getFecha().toString());
                System.out.println("Tipo de evento: " + e.getTipo());
            }
        } else {
            System.out.println("Aun no has creado ningún evento");
        }
    }

    public void listarEventosInscritos(UsuarioService servicioUsuario) {

        if (!servicioUsuario.ListaEventosInscritos(token).isEmpty()) {
            System.out.println("--Eventos en los que estas inscrito--");
            for (Evento e : servicioUsuario.ListaEventosInscritos(token)) {
                System.out.println("------");
                System.out.println("Titulo evento: " + e.getTitulo());
                System.out.println("descripcion: " + e.getDescripcion());
                System.out.println("Fecha: " + e.getFecha().toString());
                System.out.println("Tipo de evento: " + e.getTipo());
            }
        } else {
            System.out.println("No se ha inscrito en ningun evento todavia.");
        }
        if (!servicioUsuario.ListaEventosEnEspera(token).isEmpty()) {
            System.out.println("--Eventos en los que estas en lista de espera--");

            for (Evento e : servicioUsuario.ListaEventosEnEspera(token)) {
                System.out.println("------");
                System.out.println("Titulo evento: " + e.getTitulo());
                System.out.println("descripcion: " + e.getDescripcion());
                System.out.println("Fecha: " + e.getFecha().toString());
                System.out.println("Tipo de evento: " + e.getTipo());
            }
        }
    }

    public void run() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        UsuarioService servicioUsuario = (UsuarioService) context.getBean("usuarioServiceImp");
        EventoService servicioEvento = (EventoService) context.getBean("eventoServiceImp");
        String usuario = "", password;
        do {
            System.out.println("--------- Menu ----------");
            System.out.println("Introduce una opcion: ");
            System.out.println("0.- Salir de la aplicacion");
            System.out.println("1.- Identidficarse");
            System.out.println("2.- Registrarse");
            System.out.println("3.- Buscar eventos(tipo)");
            if (token != -1) {
                System.out.println("4.- Crear evento");
                System.out.println("5.- Cancelar evento");
                System.out.println("6.- Inscribirse a un evento");
                System.out.println("7.- Cancelar inscripcion a un evento");
                System.out.println("8.- Mostras eventos creados");
                System.out.println("9.- mostrar eventos inscritos");
            }

            System.out.print("Introduce una opcion: ");
            opcion = scanner.nextInt();
            System.out.println("-------------------------");

            switch (opcion) {
                case 1: {
                    scanner.nextLine(); //Consume \n
                    System.out.print("Introduce usuario:");
                    usuario = scanner.nextLine();

                    System.out.print("Introduce Contraseña:");
                    password = scanner.nextLine();
                    identificarUsuario(servicioUsuario, usuario, password);
                }
                break;
                case 2:
                    registrarUsuario(servicioUsuario);
                    break;
                case 3:
                    buscarEvento(servicioEvento);
                    break;

                case 4:
                    crearEvento(servicioEvento, servicioUsuario.devuelveUsuario(usuario), servicioUsuario);
                    break;
                case 5:
                    cancelaEvento(servicioUsuario, usuario, servicioEvento);
                    break;
                case 6:
                    inscribirUsuario(servicioEvento, token, servicioUsuario);
                    break;
                case 7:
                    cancelarInscripcion(servicioEvento, servicioUsuario);
                    break;
                case 8:
                    listarEventosCreados(servicioUsuario, usuario);
                    break;
                case 9:
                    listarEventosInscritos(servicioUsuario);
                    break;
            }

        } while (opcion != 0);
        scanner.close();
    }

}
