package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import model.TipoDeAtraccion;
import model.Usuario;

public class UsuarioDAOImplement implements UsuarioDAO{

	//Nombre - Atraccion preferida - Presupuesto - Tiempo - id_usuario
	@Override
	public int update(Usuario user){
		Integer nuevoPresu = user.getPresupuesto();
		Double nuevoTiempo = user.getTiempoDisponible();
		
			try {
				
				String sql = "UPDATE USUARIOS SET PRESUPUESTO = ?, TIEMPO = ?  WHERE NOMBRE = ?";
				Connection conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
	
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, nuevoPresu);
				statement.setFloat(2, Float.parseFloat(nuevoTiempo.toString()));
				statement.setString(3, user.getNombre());
				int rows = 0;
				try {
					rows = statement.executeUpdate();	
				}catch (Exception e){
					conn.rollback();
				}finally {
					conn.commit();
				}
				
	
				return rows;
				
			} catch (Exception e) {
				throw new MiDataException(e);
			}
			
	}


	public Usuario findByUsername(String username) {
				
			try {
				
				String sql = "SELECT * FROM USUARIOS WHERE NOMBRE = ?";
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, username);
				ResultSet resultados = statement.executeQuery();
	
				Usuario user = null;
	
				if (resultados.next()) {
					user = toUser(resultados);
				}
	
				return user;
				
			} catch (Exception e) {
				throw new MiDataException(e);
			}
			
				
	}

	public List<Usuario> findAll() {
				
		try {
					
			String sql = "SELECT * FROM USUARIOS";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
		
			List<Usuario> usuarios = new LinkedList<Usuario>();
					
			while (resultados.next()) {
				usuarios.add(toUser(resultados));
			}
					
			return usuarios;
			
		} catch (Exception e) {
			throw new MiDataException(e);
		}
				
	}

	private Usuario toUser(ResultSet resultados) throws SQLException {
		ItinerarioDAOImplement itDAO = DAOFactory.getItinerarioDAO();
		if (!itDAO.tieneItinerario(resultados.getString("Nombre"))) {

			return new Usuario(TipoDeAtraccion.valueOf(resultados.getString("Atraccion_Preferida")),
					resultados.getInt("Presupuesto"), Double.parseDouble(resultados.getString("Tiempo")),
					resultados.getString("Nombre"));
		}else {
			return new Usuario(TipoDeAtraccion.valueOf(resultados.getString("Atraccion_Preferida")),
					resultados.getInt("Presupuesto"), Double.parseDouble(resultados.getString("Tiempo")),
					resultados.getString("Nombre"),itDAO.buscarPorUsuario(resultados.getString("Nombre")));
		}
	}

       //REVISAR!---------------------------------------------------------------------------------------------------
		

}
