package app;

import dao.AtraccionesDAO;
import dao.DAOFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import dao.PromocionesDAO;
import dao.UsuarioDAO;
import model.Atraccion;
import model.ComparadorParaSugerencias;
import model.Ofertables;
import model.Promocion;
import model.Usuario;

public class App {

	public static List<Usuario> usuarios;
	public static LinkedList<Atraccion> atracciones;
	public static LinkedList<Atraccion> listaParaInteractuar;

	public static void main(String[] args) {

		AtraccionesDAO atrDAO = DAOFactory.getAtraccionDAO();
		UsuarioDAO userDAO = DAOFactory.getUsuarioDAO();
		PromocionesDAO promoDAO = DAOFactory.getPromocionDAO();

		List<Ofertables> sugerencia = new LinkedList<Ofertables>();
		List<Atraccion> atracciones = atrDAO.findAll();
		List<Usuario> users = userDAO.findAll();
		List<Promocion> packs = promoDAO.findAll();

		sugerencia.addAll(packs);
		sugerencia.addAll(atracciones);
		try (Scanner scanner = new Scanner(System.in)) {
			for (Usuario usuario : users) {
				System.out
						.println("Bienvenido " + usuario.getNombre() + "\nUsted cuenta con " + usuario.getPresupuesto()
								+ " monedas y un tiempo disponible de " + usuario.getTiempoDisponible() + "Hs.");
				sugerencia.sort(new ComparadorParaSugerencias(usuario.getAtraccionPreferida()));

				for (Ofertables oferta : sugerencia) {

					if (usuario.puedeComprar(oferta)) {

						System.out.println(oferta);
						System.out.print("Ingrese S para aceptar o N para no hacerlo: ");
						String in1 = scanner.nextLine();
						if (in1.toUpperCase().equals("S")) {
							usuario.comprarOferta(oferta);
						}
					}

				}
				System.out.println("Terminaron las sugerencias para este usuario\nPresione enter para continuar...");
				String in1 = scanner.nextLine();

			}
			String in1 = scanner.nextLine();
			System.out.println(in1);
		}

	}
}
