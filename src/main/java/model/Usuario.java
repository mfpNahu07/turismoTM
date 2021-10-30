package model;



import java.util.LinkedList;
import java.util.List;

import dao.DAOFactory;
import dao.ItinerarioDAOImplement;
import dao.UsuarioDAO;

public class Usuario {
	private int presupuesto;
	private double tiempoDisponible;
	private TipoDeAtraccion atraccionPreferida;
//	private float monedasGastadas;
	private List<Ofertables> itinerario = new LinkedList<Ofertables> ();
	private final int PRESUPUESTO_INICIAL;
	private final double TIEMPO_DISPONIBLE_INICIAL;
	private String nombre;
	
	public Usuario(TipoDeAtraccion atraccionPreferida, int presupuesto, double tiempoDisponible, String nombre) {
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.atraccionPreferida = atraccionPreferida;
		this.PRESUPUESTO_INICIAL=presupuesto;
		this.TIEMPO_DISPONIBLE_INICIAL=tiempoDisponible;
		this.nombre=nombre;
	}
	public Usuario(TipoDeAtraccion atraccionPreferida, int presupuesto, double tiempoDisponible, String nombre, List<Ofertables> itinerario) {
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.atraccionPreferida = atraccionPreferida;
		this.PRESUPUESTO_INICIAL=presupuesto;
		this.TIEMPO_DISPONIBLE_INICIAL=tiempoDisponible;
		this.nombre=nombre;
		this.itinerario=itinerario;
	}
	@Override
	public String toString() {
		return "Usuario [presupuesto=" + presupuesto + ", tiempoDisponible=" + tiempoDisponible
				+ ", atraccionPreferida=" + atraccionPreferida + "]";
	}
	public int getPresupuesto() {
		return presupuesto;
	}
	public String getNombre() {
		return this.nombre;
	}
	public double getTiempoDisponible() {
		return tiempoDisponible;
	}
	public List<Ofertables> getItinerario(){
		return this.itinerario;
	}
	public TipoDeAtraccion getAtraccionPreferida() {
		return atraccionPreferida;
	}
	public boolean puedeComprar(Ofertables oferta) {
		for (Ofertables atraccion:this.itinerario) {
			if (oferta.esOContiene(atraccion)) {
				return false;
			}
		}
		return (this.presupuesto >= oferta.getCosto()) && (this.getTiempoDisponible()>=oferta.getTiempo()) && (oferta.hayCupo());	
				}
	public void cobrar(Ofertables atraccion) throws Exception {
		UsuarioDAO userDAO =DAOFactory.getUsuarioDAO();
		if(this.presupuesto >= atraccion.getCosto()) {
			this.presupuesto -= atraccion.getCosto();
			userDAO.update(this);
		}
		else {
			throw new Exception("No alcanza el dinero para comprar.");
		}
	
	}
	public void restarTiempo(Ofertables atraccion) throws Exception {
		UsuarioDAO userDAO =DAOFactory.getUsuarioDAO();
		if(this.tiempoDisponible >= atraccion.getTiempo()) {
			this.tiempoDisponible -= atraccion.getTiempo();
			userDAO.update(this);
		}
		else {
			throw new Exception("El usuario no cuenta con el tiempo necesario.");
		}
			
	}
	public int dineroGastado() {
		return this.PRESUPUESTO_INICIAL-this.presupuesto;
	}
	public double tiempoRequerido() {
		return this.TIEMPO_DISPONIBLE_INICIAL - this.tiempoDisponible;
	}
	public void comprarOferta(Ofertables oferta) {
			for (Atraccion atr2: oferta.atraccionesIncluidas()) {
				try {
						atr2.restarUnCupo();
						this.cobrar(oferta);
						this.restarTiempo(oferta);
						addToItinerary(atr2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
	System.out.println("\n¡Ya fue agregado al itinerario!\nTenga en mente que usted aun cuenta con "+ this.getPresupuesto()+" monedas y un tiempo disponible de "+ this.getTiempoDisponible()+"Hs.");
	}
	private void addToItinerary(Atraccion atr2) {
		ItinerarioDAOImplement itDAO = DAOFactory.getItinerarioDAO();
		this.itinerario.add(atr2);
		itDAO.insert(this,atr2);
	}
}
