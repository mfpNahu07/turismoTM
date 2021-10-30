package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoDeAtraccion;

public class AtraccionDAOImplement implements AtraccionesDAO {

	//
	public int update(Atraccion atr) {
		try {
			String sql = "UPDATE ATRACCIONES SET CUPO = ? WHERE NOMBRE = ?";
			Connection conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
	
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, atr.getCupo());
			statement.setString(2, atr.getNombre());
			int rows=0;
			try {

				rows = statement.executeUpdate();
			} catch (Exception e) {
				conn.rollback();
			} finally {
				conn.commit();
			}
	
			return rows;
			
		} catch (Exception e) {
			throw new MiDataException(e);
		}
		
   }

	public Atraccion findByAtrName(String atrname) {

		try {

			String sql = "SELECT * FROM ATRACCIONES WHERE NOMBRE = ?";
			Connection conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, atrname);
			ResultSet resultados = statement.executeQuery();

			Atraccion atr = null;

			if (resultados.next()) {
				atr = toAtraccion(resultados);
			}

			return atr;

		} catch (Exception e) {
			throw new MiDataException(e);
		}

	}

	private Atraccion toAtraccion(ResultSet resultados) throws SQLException {
		return new Atraccion(resultados.getString("Nombre"), resultados.getInt("Costo"), resultados.getDouble("Tiempo"),
				resultados.getInt("Cupo"), TipoDeAtraccion.valueOf(resultados.getString("Tipo")));
	}

	public List<Atraccion> findAll() {

		try {

			String sql = "SELECT * FROM ATRACCIONES";
			Connection conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Atraccion> usuarios = new LinkedList<Atraccion>();
			while (resultados.next()) {
				usuarios.add(toAtraccion(resultados));
			}

			return usuarios;

		} catch (Exception e) {
			throw new MiDataException(e);
		}

	}

}
