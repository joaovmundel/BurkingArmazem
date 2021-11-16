package me.joao.armazem.principal;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;

import me.joao.armazem.conexao.Conexao;
import me.joao.armazem.conexao.DBManager;
import me.joao.armazem.crops.CropsListeners;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static StateFlag ARMAZEM;
	public static WorldGuardPlugin wg = WorldGuardPlugin.inst();
	public static Economy econ = null;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("armazem").setExecutor(new Comandos());
		Bukkit.getPluginManager().registerEvents(new ArmazemListeners(), this);
		Bukkit.getPluginManager().registerEvents(new CropsListeners(), this);
		initDB();
		setupEconomy();
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onDisable() {

	}

	public void initDB() {
		saveDefaultConfig();
		File f = new File(this.getDataFolder() + "/data");
		if (!f.exists()) {
			f.mkdir();
		}
		DBManager dbm = new DBManager(new Conexao());
		dbm.createTable();
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Main getInstance() {
		return (Main) Bukkit.getPluginManager().getPlugin("BurkingArmazem");
	}
}
