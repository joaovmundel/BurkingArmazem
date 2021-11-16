package me.joao.armazem.manager;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.joao.armazem.conexao.CropManager;
import me.joao.flags.BFlags;

public class ArmazemManager {
	public static ArmazemManager am = new ArmazemManager();
	public WorldGuardPlugin wg = WorldGuardPlugin.inst();
	public static HashMap<String, Integer> newcacto = new HashMap<>();
	public CropManager qr = CropManager.cm;

	public boolean isOwner(Player p) {
		boolean result = false;
		RegionManager rm = wg.getRegionManager(p.getWorld());
		Vector pt = new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
		ApplicableRegionSet set = rm.getApplicableRegions(pt);

		for (ProtectedRegion pro : set) {
			if (pro.getOwners().contains(p.getUniqueId())) {
				result = true;
				break;
			}
		}

		if (p.hasPermission("armazem.admin")) {
			result = true;
		}

		return result;
	}

	public boolean armazemEnabled(Player p) {
		boolean retorno = false;
		if (getRegionAtPlayer(p) != null) {
			retorno = BFlags.getFlagState(getRegionAtPlayer(p), BFlags.getFlagArmazem());
		} else {
			retorno = false;
		}
		return retorno;
	}

	public boolean armazemEnabled(Block bl) {
		boolean retorno = false;
		if (getRegionAtBlock(bl) != null) {
			retorno = BFlags.getFlagState(getRegionAtBlock(bl), BFlags.getFlagArmazem());
		} else {
			retorno = false;
		}

		return retorno;
	}

	public void setArmazemState(Player p, boolean state) {
		if (state == false) {
			getRegionAtPlayer(p).setFlag(BFlags.getFlagArmazem(), State.DENY);
		} else {
			getRegionAtPlayer(p).setFlag(BFlags.getFlagArmazem(), State.ALLOW);
		}
	}

	public ProtectedRegion getRegionAtBlock(Block bl) {
		RegionManager rm = wg.getRegionManager(bl.getWorld());
		Vector pt = new Vector(bl.getLocation().getX(), bl.getLocation().getY(), bl.getLocation().getZ());
		ProtectedRegion prot = null;
		ApplicableRegionSet set = rm.getApplicableRegions(pt);

		for (ProtectedRegion pro : set) {
			prot = pro;
		}
		return prot;

	}

	public ProtectedRegion getRegionAtPlayer(Player p) {
		RegionManager rm = wg.getRegionManager(p.getWorld());
		Vector pt = new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
		ProtectedRegion prot = null;
		ApplicableRegionSet set = rm.getApplicableRegions(pt);

		for (ProtectedRegion pro : set) {
			prot = pro;
		}
		return prot;

	}

	public String getRegionName(Player p) {
		RegionManager rm = wg.getRegionManager(p.getWorld());
		Vector pt = new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
		String prot = "";
		ApplicableRegionSet set = rm.getApplicableRegions(pt);

		for (ProtectedRegion pro : set) {
			if (set.size() > 1) {
				if (!(pro.getId().equalsIgnoreCase("__global__"))) {
					prot = pro.getId();
				}
			} else {
				prot = pro.getId();
			}
		}
		return prot;
	}

	public boolean canOpenArmazem(Player p) {
		boolean result = false;
		RegionManager rm = wg.getRegionManager(p.getWorld());
		Vector pt = new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
		ApplicableRegionSet set = rm.getApplicableRegions(pt);

		for (ProtectedRegion pro : set) {
			if (pro.getOwners().contains(p.getUniqueId()) || pro.getMembers().contains(p.getUniqueId())) {
				result = true;
				break;
			}
		}

		if (p.hasPermission("armazem.admin")) {
			result = true;
		}

		return result;
	}
}
