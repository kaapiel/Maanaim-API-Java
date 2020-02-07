package br.com.celula.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return (Connection) DriverManager.getConnection("jdbc:mysql://mysql06.maanaimosasco.com:3306/maanaimosasco",
					"maanaimosasco","cbm18Ti@3");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.getMessage());

		}
	
	}
}
