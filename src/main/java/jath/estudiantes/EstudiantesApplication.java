package jath.estudiantes;

import jath.estudiantes.modelo.Estudiante;
import jath.estudiantes.servicio.EstudianteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

	@Autowired
	private EstudianteServicio estudianteServicio;

	private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion...");

		//Levantar la fabrica de spring
		SpringApplication.run(EstudiantesApplication.class, args);

		logger.info("Aplicacion finalizada!");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(nl + "Ejecutando metodo run de Spring" + nl);
		Scanner consola = new Scanner(System.in);
		boolean salir = false;

		logger.info("\uD83D\uDCD5 \uD83D\uDCD5 Sistema de Estudiantes Spring \uD83D\uDCD5 \uD83D\uDCD5" + nl);

		while (!salir) {
			mostrarMenu();
			try {
				int opcion = Integer.parseInt(consola.nextLine());

				switch (opcion) {
					case 1 -> {
						logger.info(nl + "Listado de Estudiantes: " + nl);
						listarEstudiantes();
					}
					case 2 -> {
						logger.info(nl + "Buscar Estudiante: " + nl);
						buscarEstudiante(consola);
					}
					case 3 -> {
						logger.info(nl + "Agregar Estudiante: " + nl);
						agregarEstudiante(consola);
					}
					case 4 -> {
						logger.info(nl + "Modificar Estudiante: " + nl);
						modificarEstudiante(consola);
					}
					case 5 -> {
						logger.info(nl + "Eliminar Estudiante: " + nl);
						eliminarEstudiante(consola);
					}
					case 6 -> {
						logger.info(nl + "Hasta pronto... " + nl);
						salir = true;
					}
					default -> logger.info(nl + "❌ Opcion incorrecta, vuelve a intentar" + nl);
				}
			} catch (Exception e) {
				logger.info("❌ Ocurrio un error: " + e.getMessage());
			}
		}
	}

	private void eliminarEstudiante(Scanner consola) {
		System.out.print("Ingresa el ID del Estudiante a eliminar: ");
		int idEstudiante = Integer.parseInt(consola.nextLine());
		Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);

		if(estudiante != null){
			estudianteServicio.eliminarEstudiante(estudiante);
			logger.info("✅ Estudiante eliminado: " + estudiante);
		} else {
			logger.info("❌ No existe ese Estudiante :( ");
		}
	}

	private void modificarEstudiante(Scanner consola) {
		System.out.print("Ingresa el ID del Estudiante a modificar: ");
		Integer idEstudiante = Integer.parseInt(consola.nextLine());
		Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);

		if(estudiante != null){
			System.out.print("Nombre: ");
			String nombre = consola.nextLine();
			System.out.print("Apellidos: ");
			String apellidos = consola.nextLine();
			System.out.print("Telefono: ");
			String telefono = consola.nextLine();
			System.out.print("Email: ");
			String email = consola.nextLine();

			estudiante.setNombre(nombre);
			estudiante.setApellidos(apellidos);
			estudiante.setTelefono(telefono);
			estudiante.setEmail(email);

			estudianteServicio.guardarEstudiante(estudiante);
			logger.info("✅ Estudiante modificado exitosamente: " + estudiante + nl);

		} else {
			logger.info("❌ Estudiante no encontrado :( " + nl);
		}


	}

	private void agregarEstudiante(Scanner consola) {
		logger.info("Ingresa los siguientes datos: " + nl);
		System.out.print("Nombre: ");
		String nombre = consola.nextLine();
		System.out.print("Apellidos: ");
		String apellidos = consola.nextLine();
		System.out.print("Telefono: ");
		String telefono = consola.nextLine();
		System.out.print("Email: ");
		String email = consola.nextLine();

		// Crear objeto sin el id
		Estudiante estudiante = new Estudiante();

		estudiante.setNombre(nombre);
		estudiante.setApellidos(apellidos);
		estudiante.setTelefono(telefono);
		estudiante.setEmail(email);

		estudianteServicio.guardarEstudiante(estudiante);

		logger.info("✅ Estudiante agregado: " + estudiante + nl);
	}

	private void buscarEstudiante(Scanner consola) {
		System.out.print("Ingresa el ID del estudiante: ");
		int idEstudiante = Integer.parseInt(consola.nextLine());
		Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);

		if(estudiante != null) {
			logger.info("✅ Estudiante encontrado: " + estudiante);
		} else {
			logger.info("❌ Estudiante no encontrado :( ");
		}
	}

	private void listarEstudiantes() {
		List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
		estudiantes.forEach((estudiante -> logger.info(estudiante.toString() + nl)));
	}

	private void mostrarMenu() {
		String separator = "\uD83D\uDCDD ";
		for (int i = 0; i <= 18; i++){
			separator = new String(new char[i]).replace("\0", "\uD83D\uDCDD ");
		}

		logger.info(nl + separator + nl);
		logger.info("""
				1. Listar Estudiantes
				2. Buscar Estudiante
				3. Agregar Estudiante
				4. Modificar Estudiante
				5. Eliminar Estudiante
				6. Salir
				""");
		logger.info(nl + separator + nl);

		System.out.print("▶\uFE0F Elige una opcion: ");


	}
}
