package dao;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.LinkedList;
//import java.util.List;
//
//import jdbc.ConnectionProvider;
//import model.Atraccion;
//import model.TipoDeAtraccion;
import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario>{
	public abstract Usuario findByUsername(String username);
}
