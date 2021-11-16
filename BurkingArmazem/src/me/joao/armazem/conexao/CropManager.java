package me.joao.armazem.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import me.joao.armazem.crops.Crops;
import me.joao.armazem.manager.Armazem;
import me.joao.armazem.principal.Main;

public class CropManager {


	protected Conexao con;
	private String db = "tbl_armazem";
	private String plugin = "[BurkingArmazem]";
	private DBManager dbm = new DBManager(con);
	public static CropManager cm = new CropManager(new Conexao());

	public CropManager() {
	}

	public CropManager(Conexao con) {
		this.con = con;
	}

	@Deprecated
	public int getCactos(String region) {
		String query = "select Cactos from " + db + " where Region = ?;";
		int cactos = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con.conectar();
		pstmt = con.criarPreparedStatement(query);
		try {
			pstmt.setString(1, region);
			rs = pstmt.executeQuery();
			cactos = rs.getInt("Cactos");
		} catch (SQLException e) {
			System.out.println("Erro ao checar valores no banco");
		} finally {
			try {
				pstmt.close();
				rs.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println(plugin + " Erro ao fechar conexao");
				System.out.println(plugin + " Err: " + e.getMessage());
			}
		}
		return cactos;
	}

	@Deprecated
	public void setCactos(String region, int qtd) {
		String query = "update " + db + " set Cactos = ? where Region = ?;";

		PreparedStatement pstmt = null;
		con.conectar();
		pstmt = con.criarPreparedStatement(query);

		try {
			pstmt.setInt(1, qtd);
			pstmt.setString(2, region);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao setar cactos da region " + region + ".");
		} finally {
			try {
				pstmt.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println(plugin + "Erro ao desconectar/fechar statement");
				System.out.println(plugin + e.getMessage());
			}

		}
	}

	@Deprecated
	public void addCactos(String region, int qtd) {
		String sql = "update " + db + " set Cactos = ? where Region = ?;";
		int cactos_atual = getCactos(region);
		if (qtd >= 0) {
			int cactos_final = cactos_atual + qtd;
			con.conectar();
			PreparedStatement pstmt = con.criarPreparedStatement(sql);
			try {
				pstmt.setInt(1, cactos_final);
				pstmt.setString(2, region);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(plugin + " Erro ao adicionar cactos.");
				System.out.println(plugin + e.getMessage());
			}
		}
	}

	@Deprecated
	public void removeCactos(String region, int qtd) {
		String sql = "update " + db + " set Cactos = ? where Region = ?;";
		int cactos_atual = getCropStored(region, Crops.CACTUS);
		if (qtd >= 0 && cactos_atual >= qtd) {
			int cactos_final = cactos_atual - qtd;
			con.conectar();
			PreparedStatement pstmt = con.criarPreparedStatement(sql);
			try {
				pstmt.setInt(1, cactos_final);
				pstmt.setString(2, region);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(plugin + " Erro ao remover cactos.");
				System.out.println(plugin + e.getMessage());
			}
		}
	}

	@Deprecated
	public boolean cactoPermited(String region) {
		ResultSet rs = null;
		String sql = "select Cacto_Use from " + db + " where Region = ?;";
		PreparedStatement pstmt = con.criarPreparedStatement(sql);
		boolean permi = false;
		try {
			pstmt.setString(1, region);
			rs = pstmt.executeQuery();
			permi = rs.getBoolean("Cacto_Use");
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao consultar permissao de crop.");
			System.out.println(plugin + " Err: " + e.getMessage());
		}
		return permi;
	}

	//
	//
	//
	// NOVAS CONSULTAS
	//
	//
	//

	public void setCropPermtied(String region, Crops crop, boolean permited) {
		if (!(dbm.exists(region))) {
			Armazem arm = new Armazem(region);
			dbm.inserir(arm);
		}

		String cropdb = getCropByName(crop);
		String sql = "update " + db + " set " + cropdb + "_Use = ? where Region = ?;";
		con.conectar();
		PreparedStatement pstmt = con.criarPreparedStatement(sql);

		try {
			pstmt.setBoolean(1, permited);
			pstmt.setString(2, region);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao alterar estado de permissao do crop " + cropdb + ".");
			System.out.println(plugin + " Err: " + e.getMessage());
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con.desconectar();
		}
	}

	public boolean cropPermited(String region, Crops crop) {
		if (!(dbm.exists(region))) {
			Armazem arm = new Armazem(region);
			dbm.inserir(arm);
		}
		String crop_db = getCropByName(crop);
		ResultSet rs = null;
		String sql = "select " + crop_db + "_Use from " + db + " where Region = ?;";
		con.conectar();
		PreparedStatement pstmt = con.criarPreparedStatement(sql);
		boolean permi = false;
		try {
			pstmt.setString(1, region);
			rs = pstmt.executeQuery();
			permi = rs.getBoolean(crop_db + "_Use");
		} catch (SQLException e) {
			System.out.println(plugin + " Erro ao consultar permissao de " + crop_db + ".");
			System.out.println(plugin + " Err: " + e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.desconectar();
		}

		return permi;
	}

	public void addCrop(String region, Crops crop, int qtd) {
		if (!(dbm.exists(region))) {
			Armazem arm = new Armazem(region);
			dbm.inserir(arm);
		}
		String crop_db = getCropByName(crop);
		String sql = "update " + db + " set " + crop_db + " = ? where Region = ?;";
		int crop_atual = getCropStored(region, crop);
		if (qtd >= 0) {
			int crop_final = crop_atual + qtd;
			con.conectar();
			PreparedStatement pstmt = con.criarPreparedStatement(sql);
			try {
				pstmt.setInt(1, crop_final);
				pstmt.setString(2, region);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(plugin + " Erro ao adicionar " + crop_db + ".");
				System.out.println(plugin + e.getMessage());
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				con.desconectar();
			}
		}
	}

	public void removeCrop(String region, Crops crop, int qtd) {
		if (!(dbm.exists(region))) {
			Armazem arm = new Armazem(region);
			dbm.inserir(arm);
		}
		String crop_db = getCropByName(crop);
		String sql = "update " + db + " set " + crop_db + " = ? where Region = ?;";
		int crop_atual = getCropStored(region, crop);
		if (qtd >= 0 && crop_atual >= qtd) {
			int crop_final = crop_atual - qtd;
			con.conectar();
			PreparedStatement pstmt = con.criarPreparedStatement(sql);
			try {
				pstmt.setInt(1, crop_final);
				pstmt.setString(2, region);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(plugin + " Erro ao remover " + crop_db + ".");
				System.out.println(plugin + e.getMessage());
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				con.desconectar();
			}
		}

	}

	public void setCrop(String region, Crops crop, int qtd) {
		if (!(dbm.exists(region))) {
			Armazem arm = new Armazem(region);
			dbm.inserir(arm);
		}
		String crop_db = getCropByName(crop);

		String query = "update " + db + " set " + crop_db + " = ? where Region = ?;";

		PreparedStatement pstmt = null;
		con.conectar();
		pstmt = con.criarPreparedStatement(query);

		try {
			pstmt.setInt(1, qtd);
			pstmt.setString(2, region);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao setar " + crop_db + " da region: " + region + ".");
		} finally {
			try {
				pstmt.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println(plugin + "Erro ao desconectar/fechar statement");
				System.out.println(plugin + e.getMessage());
			}

		}

	}

	public int getCropStored(String region, Crops crop) {
		if (!(dbm.exists(region))) {
			Armazem arm = new Armazem(region);
			dbm.inserir(arm);
		}
		String crop_db = getCropByName(crop);
		String query = "select " + crop_db + " from " + db + " where Region = ?;";
		int cropqtd = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con.conectar();
		pstmt = con.criarPreparedStatement(query);
		try {
			pstmt.setString(1, region);
			rs = pstmt.executeQuery();
			cropqtd = rs.getInt(crop_db);
		} catch (SQLException e) {
			System.out.println("Erro ao checar valores no banco.");
		} finally {
			try {
				pstmt.close();
				rs.close();
				con.desconectar();
			} catch (SQLException e) {
				System.out.println("erro ao fechar conexao");
			}
		}
		return cropqtd;
	}

	private String getCropByName(Crops crop) {
		String result = "CROP INVALIDO";

		switch (crop) {
		case CACTUS:
			result = "Cactos";
			break;
		case MELON:
			result = "Melancia";
			break;
		case NETHER_STALK:
			result = "Fungo";
			break;
		case PUMPKIN:
			result = "Abobora";
			break;
		case SUGAR_CANE:
			result = "Cana";
			break;
		case WHEAT:
			result = "Trigo";
			break;
		default:
			result = "INVALIDO";
			break;
		}

		return result;
	}

	public Armazem getArmazem(String regionName) {
		if (!(dbm.exists(regionName))) {
			Armazem arm = new Armazem(regionName);
			dbm.inserir(arm);
		}
		// id, Region, Cactos, Cactos_Use, Melancia, Melancia_Use,
		// Abobora, Abobora_Use, Fungo, Fungo_Use, Cana, Cana_Use, Trigo, Trigo_Use
		Armazem arm = new Armazem(regionName);

		arm.setCactosStored(getCropStored(regionName, Crops.CACTUS));
		arm.setCactoUse(cropPermited(regionName, Crops.CACTUS));

		arm.setMelanciaStored(getCropStored(regionName, Crops.MELON));
		arm.setMelanciaUse(cropPermited(regionName, Crops.MELON));

		arm.setAboboraStored(getCropStored(regionName, Crops.PUMPKIN));
		arm.setAboboraUse(cropPermited(regionName, Crops.PUMPKIN));

		arm.setFungoStored(getCropStored(regionName, Crops.NETHER_STALK));
		arm.setFungoUse(cropPermited(regionName, Crops.NETHER_STALK));

		arm.setCanaStored(getCropStored(regionName, Crops.SUGAR_CANE));
		arm.setCanaUse(cropPermited(regionName, Crops.SUGAR_CANE));

		arm.setTrigoStored(getCropStored(regionName, Crops.WHEAT));
		arm.setTrigoUse(cropPermited(regionName, Crops.WHEAT));

		return arm;
	}

	public boolean permitedWorld(String worldName) {
		return Main.getInstance().getConfig().getStringList("Mundos").contains(worldName);
	}

	public String getCropName(Crops crop) {
		return Main.getInstance().getConfig().getString("Crops." + crop.toString() + ".Nome".replace("&", "§"));
	}

	public int getCropPrice(Crops crop) {
		return Main.getInstance().getConfig().getInt("Crops." + crop.toString() + ".Price");
	}

	public int getCropSlot(Crops crop) {
		return Main.getInstance().getConfig().getInt("Crops." + crop.toString() + ".Slot");
	}

	public List<String> getLore(Crops crop) {
		return Main.getInstance().getConfig().getStringList("Crops." + crop.toString() + ".Lore");
	}
}
