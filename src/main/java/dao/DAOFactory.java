package dao;

public class DAOFactory {

	public static AtraccionesDAO getAtraccionDAO() {
		return new AtraccionDAOImplement();
	}
	
	public static PromocionesDAO getPromocionDAO() {
		return new PromocionesDAOImplement();
	}
	
	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImplement();
	}
	public static ItinerarioDAOImplement getItinerarioDAO() {
		return new ItinerarioDAOImplement();
	}
}
