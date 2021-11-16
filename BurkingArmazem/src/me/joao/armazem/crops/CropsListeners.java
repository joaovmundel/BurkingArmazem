package me.joao.armazem.crops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;

import me.joao.armazem.conexao.CropManager;
import me.joao.armazem.manager.ArmazemManager;

public class CropsListeners implements Listener {

	/*
	 * 
	 * Adicionar a opcao de guardar sementes do trigo no armazem
	 * 
	 */

	@EventHandler
	public void cactoCresceu(BlockGrowEvent e) {
		Block bl = e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().subtract(0, 1, 0));
		if (CropManager.cm.permitedWorld(bl.getWorld().getName())) {
			if (ArmazemManager.am.getRegionAtBlock(bl) != null) {
				if (ArmazemManager.am.armazemEnabled(bl)) {
					String rgName = ArmazemManager.am.getRegionAtBlock(bl).getId();
					if (bl.getType() == Material.CACTUS) {
						if (CropManager.cm.cropPermited(rgName, Crops.CACTUS)) {
							CropManager.cm.addCrop(rgName, Crops.CACTUS, 1);
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void canaQuebrada(BlockBreakEvent e) {
		Block bl = e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation());
		Block bl_cima = e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().add(0, 1, 0));
		if (CropManager.cm.permitedWorld(bl.getWorld().getName())) {
			if (ArmazemManager.am.getRegionAtBlock(bl) != null) {
				if (ArmazemManager.am.armazemEnabled(bl)) {
					String rgName = ArmazemManager.am.getRegionAtBlock(bl).getId();
					Material ci = bl.getType();
					if (ci == Material.CACTUS || ci == Material.SUGAR_CANE || ci == Material.PUMPKIN
							|| ci == Material.MELON || ci == Material.NETHER_WARTS || ci == Material.CROPS) {
						Crops crop;
						if (ci == Material.CROPS) {
							crop = Crops.WHEAT;
						} else if (ci == Material.NETHER_WARTS) {
							crop = Crops.NETHER_STALK;
						} else {
							crop = Crops.valueOf(bl.getType().toString());
						}
						if (CropManager.cm.cropPermited(rgName, crop)) {
							if (bl.getType() == Material.SUGAR_CANE_BLOCK) {
								if (bl_cima.getType() == Material.SUGAR_CANE_BLOCK) {
									CropManager.cm.addCrop(rgName, Crops.SUGAR_CANE, 2);
									bl_cima.setType(Material.AIR);
									bl.setType(Material.AIR);
									e.setCancelled(true);
								} else {
									CropManager.cm.addCrop(rgName, Crops.SUGAR_CANE, 1);
									bl.setType(Material.AIR);
									e.setCancelled(true);
								}
							} else if (bl.getType() == Material.NETHER_WARTS) {
								if (bl.getData() == 3) {
									CropManager.cm.addCrop(rgName, Crops.NETHER_STALK, 3);
									bl.setType(Material.NETHER_WARTS);
									e.setCancelled(true);
								}

							} else if (e.getBlock().getType() == Material.CROPS) {
								if (e.getBlock().getData() == 7) {
									CropManager.cm.addCrop(rgName, Crops.WHEAT, 1);
									e.getBlock().setType(Material.CROPS);
									e.setCancelled(true);
								}
							} else if (bl.getType() == Material.PUMPKIN) {
								e.setCancelled(true);
								CropManager.cm.addCrop(rgName, Crops.PUMPKIN, 1);
								bl.setType(Material.AIR);
							} else if (bl.getType() == Material.MELON_BLOCK) {
								e.setCancelled(true);
								CropManager.cm.addCrop(rgName, Crops.MELON, bl.getDrops().size());
								bl.setType(Material.AIR);
							}
						}
						//e.getPlayer().sendMessage("E.getblock" + e.getBlock().getType().toString());
						//e.getPlayer().sendMessage("bl.gettype" + bl.getType().toString());
					}
				}
			}
		}
	}
}
