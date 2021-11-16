package me.joao.armazem.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import me.joao.armazem.principal.Main;

public class Conexao {

	private Connection con;
	private String plugin = "[BurkingArmazem]";

	public boolean conectar() {

		String url = "jdbc:sqlite:" + Main.getInstance().getDataFolder() + "/data/armazem.db";
		try {
			this.con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao conectar ao banco.");
			System.out.println(plugin + " " + e.getMessage());
		}

		return true;
	}

	public boolean desconectar() {

		try {
			if (!con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao desconectar do banco");
			System.out.println(plugin + " " + e.getMessage());
		}
		return true;
	}

	public Statement criarStatement() {
		try {
			return this.con.createStatement();
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao criar statement.");
			return null;
		}
	}

	public PreparedStatement criarPreparedStatement(String sql) {
		try {
			return this.con.prepareStatement(sql);
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao criar prepared statement.");
			System.out.println(plugin + e.getMessage());
			return null;
		}
	}

	public Connection getConexao() {
		return con;
	}

}
