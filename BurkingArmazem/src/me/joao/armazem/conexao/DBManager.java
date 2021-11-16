package me.joao.armazem.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.joao.armazem.manager.Armazem;

public class DBManager {
	private String db = "tbl_armazem";
	private final Conexao con;
	private String plugin = "[BurkingArmazem]";

	public DBManager(Conexao con) {
		this.con = con;
	}

//CACTUS, MELON, PUMPKIN, NETHER_STALK, SUGAR_CANE, WHEAT;
	public void createTable() {
		String sql = null;
		sql = "CREATE TABLE IF NOT EXISTS tbl_armazem(id integer PRIMARY KEY AUTOINCREMENT, Region text NOT NULL, Cactos integer DEFAULT 0, Cactos_Use boolean not null default false,"
				+ " Melancia integer DEFAULT 0, Melancia_Use boolean not null default false, "
				+ "Abobora integer DEFAULT 0, Abobora_Use boolean not null default false, "
				+ "Fungo integer DEFAULT 0, Fungo_Use boolean not null default false, "
				+ "Cana integer DEFAULT 0, Cana_Use boolean not null default false, "
				+ "Trigo integer DEFAULT 0, Trigo_Use boolean not null default false);";
		con.conectar();
		Statement stmt = con.criarStatement();

		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(plugin + "Erro ao criar tabela");
			System.out.println(plugin + e.getMessage());
		} finally {
			try {
				stmt.close();
				con.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void inserir(Armazem arm) {
		String sqlInsert = "INSERT INTO " + db
				+ " (id,Region,Cactos,Cactos_Use,Melancia,Melancia_Use,Abobora,Abobora_Use,Fungo,Fungo_Use,Cana,Cana_Use,Trigo,Trigo_Use) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		con.conectar();
		PreparedStatement pstmt = this.con.criarPreparedStatement(sqlInsert);
		try {
			pstmt.setString(1, null);
			pstmt.setString(2, arm.getRegionName());

			pstmt.setInt(3, arm.getCactosStored());
			pstmt.setBoolean(4, arm.isCactoUse());

			pstmt.setInt(5, arm.getMelanciaStored());
			pstmt.setBoolean(6, arm.isMelanciaUse());

			pstmt.setInt(7, arm.getAboboraStored());
			pstmt.setBoolean(8, arm.isAboboraUse());

			pstmt.setInt(9, arm.getFungoStored());
			pstmt.setBoolean(10, arm.isFungoUse());

			pstmt.setInt(11, arm.getCanaStored());
			pstmt.setBoolean(12, arm.isCanaUse());

			pstmt.setInt(13, arm.getTrigoStored());
			pstmt.setBoolean(14, arm.isTrigoUse());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao inserir dados no banco.");
			System.out.println(plugin + " Err: " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			con.desconectar();
		}
	}

	public boolean exists(String region) {
		boolean result = false;
		ResultSet rs;
		Conexao con = new Conexao();
		con.conectar();
		String query = "select * from " + db + " where Region = ?;";
		PreparedStatement pstmt = con.criarPreparedStatement(query);

		try {
			pstmt.setString(1, region);
			rs = pstmt.executeQuery();
			result = rs.next();
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao checar existencia de regiao.");
			System.out.println(plugin + " Err: " + e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(plugin + e.getMessage());
			}
			con.desconectar();
		}
		return result;
	}

	public void dropTable() {
		String sql = "drop table " + db + ";";
		this.con.conectar();

		Statement stmt = this.con.criarStatement();
		try {
			stmt.executeUpdate(sql);
			System.out.println("Tabela deletada.");
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao deletar a tabela.");
			System.out.println(plugin + " Err: " + e.getMessage());
		} finally {
			try {
				stmt.close();
				con.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.desconectar();
		}
	}

	public void removeRegion(String rg) {
		String sql = "delete from " + db + " where Region = ?";
		con.conectar();
		PreparedStatement stmt = con.criarPreparedStatement(sql);

		try {
			stmt.setString(1, rg);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao deletar region " + rg + ".");
		} finally {
			try {
				stmt.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println(plugin + " Erro ao desconectar do banco");
				System.out.println(plugin + " Err: " + e.getMessage());
			}
		}
	}

}
