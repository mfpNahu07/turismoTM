package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jdbc.ConnectionProvider;

import model.Promocion;
import model.PromocionPorcentual;
import model.PromocionAbsoluta;
import model.PromocionAxB;
import model.Atraccion;

public class PromocionesDAOImplement implements PromocionesDAO {

	public List<Promocion> findAll() {
		try {
// System.out.println("HOLA");

			String sql = "SELECT p.nombre, group_concat(Nombre_atraccion) as Atracciones FROM Promociones p LEFT JOIN Promocion_Atraccion ap ON p.Nombre = ap.Nombre_promocion GROUP BY p.Nombre;";
			String sql2 = "SELECT * FROM Porcentual;";
			String sql3 = "SELECT * FROM Absoluta;";
			String sql4 = "SELECT * FROM AXB;";

			Connection conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			Connection conn2 = ConnectionProvider.getConnection();
			conn2.setAutoCommit(false);
			Connection conn3 = ConnectionProvider.getConnection();
			conn3.setAutoCommit(false);
			Connection conn4 = ConnectionProvider.getConnection();
			conn4.setAutoCommit(false);

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			PreparedStatement statement2 = conn2.prepareStatement(sql2);
			ResultSet resultados2 = statement2.executeQuery();

			PreparedStatement statement3 = conn3.prepareStatement(sql3);
			ResultSet resultados3 = statement3.executeQuery();

			PreparedStatement statement4 = conn4.prepareStatement(sql4);
			ResultSet resultados4 = statement4.executeQuery();

			List<Promocion> promos = new LinkedList<Promocion>();

			Map<String, String> dicPor = new TreeMap<String, String>();
			Map<String, String> dicAbs = new TreeMap<String, String>();
			Map<String, String> dicAxb = new TreeMap<String, String>();

			while (resultados2.next()) {
				String key = resultados2.getString("Nombre");
				String value = resultados2.getString("Descuento");
				dicPor.put(key, value);
			}
			while (resultados3.next()) {
				String key = resultados3.getString("Nombre");
				String value = resultados3.getString("Precio");
				dicAbs.put(key, value);

			}
			while (resultados4.next()) {
				String key = resultados4.getString("Nombre");
				String value = resultados4.getString("Atraccion_gratis");
				dicAxb.put(key, value);

			}
			while (resultados.next()) {
// System.out.println(resultados);
				promos.add(toPromocion(resultados, dicPor, dicAbs, dicAxb));
			}

			return promos;

		} catch (Exception e) {
// System.out.println("CHAU");
			throw new MiDataException(e);
		}

	}

	private Promocion toPromocion(ResultSet resultados, Map<String, String> dicPor, Map<String, String> dicAbs,
			Map<String, String> dicAxb) throws SQLException {

		AtraccionesDAO atrDAO = DAOFactory.getAtraccionDAO();
		List<Atraccion> allAtr = atrDAO.findAll();// Todas las atracciones
		List<Atraccion> listAtr = new LinkedList<Atraccion>();// Vamos a guardar aca las atracciones (Objetos) que
																// incluye el pack
		String[] atraccionesDelPack;
//atraccionesDelPack= resultados.getString("Atracciones").split(",");//Extraidas de la base de datos
	//	System.out.println(resultados.getString(2));
	//	System.out.println("--------------------------------------------------------------");
		atraccionesDelPack = resultados.getString(2).split(",");// Extraidas de la base de datos
//System.out.println(atraccionesDelPack);
		for (Atraccion atr : allAtr) {
			for (String nombre : atraccionesDelPack) {
				if (atr.getNombre().equals(nombre)) {
					listAtr.add(atr);
				}
			}

		}
		String nombre = resultados.getString(1);
		if (dicPor.containsKey(nombre)) {
			double descuento = Double.parseDouble(dicPor.get(nombre));
			return new PromocionPorcentual(resultados.getString("Nombre"), listAtr, descuento);
		} else if (dicAbs.containsKey(nombre)) {
			int costo = Integer.parseInt(dicAbs.get(nombre).toString(), 10);
			return new PromocionAbsoluta(resultados.getString("Nombre"), listAtr, costo);
		} else if (dicAxb.containsKey(nombre)) {
			Atraccion atrGratis = null;
			for (Atraccion atr : allAtr) {
				if (dicAxb.get(nombre).equals(atr.getNombre())) {
					atrGratis = atr;
				}
			}
			return new PromocionAxB(resultados.getString("Nombre"), listAtr, atrGratis);
		} else {
			return null;
		}

	}

	@Override
	public int update(Promocion t) {
		return 0;
	}

	public static void main(String[] args) {
		PromocionesDAO p = new PromocionesDAOImplement();
//p.findAll();
		for (Promocion promo : p.findAll()) {
			System.out.println(promo);
			//System.out.println("-----------------------------------------");

		}
	}

}