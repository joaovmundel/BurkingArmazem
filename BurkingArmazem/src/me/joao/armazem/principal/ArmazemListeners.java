package me.joao.armazem.principal;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.joao.armazem.conexao.CropManager;
import me.joao.armazem.crops.Crops;
import me.joao.armazem.manager.ArmazemManager;

public class ArmazemListeners implements Listener {
	public ArrayList<Player> responde = new ArrayList<>();
	public HashMap<Player, Crops> responder = new HashMap<>();
	public CropManager cc = CropManager.cm;


	@EventHandler
	public void interagirMenu(InventoryClickEvent e) {
		if (e.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase("Armazem")) {
			try {
				if (e.getClickedInventory().getTitle().equalsIgnoreCase("Armazem")) {

					Player p = (Player) e.getWhoClicked();
					String rgName = ArmazemManager.am.getRegionAtPlayer(p).getId();
					Material ci = e.getCurrentItem().getType();
					if (ci != null) {
						if (ci != Material.AIR) {
							if (ci == Material.CACTUS || ci == Material.SUGAR_CANE || ci == Material.PUMPKIN
									|| ci == Material.MELON || ci == Material.NETHER_STALK || ci == Material.WHEAT) {

								Crops crop = Crops.valueOf(e.getCurrentItem().getType().toString());
								if (e.getClick() == ClickType.RIGHT) {
									p.sendMessage("");
									p.sendMessage("§eDigite §6§lTUDO §epara pegar tudo");
									p.sendMessage("§eOu Digite a §6§lQTD §edesejada.");
									p.sendMessage("");
									p.closeInventory();
									responder.put(p, crop);
								} else if (e.getClick() == ClickType.LEFT) {
									int cropStored = cc.getCropStored(rgName, crop);
									if (cropStored > 0) {
										venderTudo(p, crop);
										p.updateInventory();
									}
								}
								e.setCancelled(true);
							}
						}
					}
				}
			} catch (NullPointerException ex) {
			}
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onResponder(AsyncPlayerChatEvent e) {
		if (responder.containsKey(e.getPlayer())) {
			Player p = e.getPlayer();
			String msg = e.getMessage();

			if (msg.equalsIgnoreCase("tudo")) {
				pegarCrop(p, responder.get(p), msg);
			} else {
				try {
					pegarCrop(p, responder.get(p), msg);
				} catch (NumberFormatException ex) {
				}
			}

			responder.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (responde.contains(e.getPlayer())) {
			responde.remove(e.getPlayer());
		}
	}

	public int invSpace(PlayerInventory inv, Material m) {
		int count = 0;
		for (int slot = 0; slot < 36; slot++) {
			ItemStack is = inv.getItem(slot);
			if (is == null) {
				count += m.getMaxStackSize();
			}
			if (is != null) {
				if (is.getType() == m) {
					count += (m.getMaxStackSize() - is.getAmount());
				}
			}
		}
		return count;
	}

	public void pegarCrop(Player p, Crops crop, String res) {
		String rgName = ArmazemManager.am.getRegionAtPlayer(p).getId();
		int cropStored = CropManager.cm.getCropStored(rgName, crop);
		int qtd = Integer.parseInt(res);

		if (res.equalsIgnoreCase("tudo")) {
			if (CropManager.cm.getCropStored(rgName, crop) > invSpace(p.getInventory(),
					Material.valueOf(crop.toString()))) {
				p.getInventory().addItem(new ItemStack(Material.valueOf(crop.toString()),
						invSpace(p.getInventory(), Material.valueOf(crop.toString()))));
				p.sendMessage("§a§lARMAZEM §aVoce pegou todos " + crop.toString() + ".");
				CropManager.cm.removeCrop(rgName, crop, invSpace(p.getInventory(), Material.valueOf(crop.toString())));
			} else {
				p.getInventory().addItem(
						new ItemStack(Material.valueOf(crop.toString()), CropManager.cm.getCropStored(rgName, crop)));
				p.sendMessage("§a§lARMAZEM §aVoce pegou todos " + crop.toString() + ".");
				CropManager.cm.removeCrop(rgName, crop, CropManager.cm.getCropStored(rgName, crop));
			}
		} else {
			if (qtd > 0) {
				if (cropStored >= qtd) {
					if (invSpace(p.getInventory(), Material.valueOf(crop.toString())) >= qtd) {
						p.getInventory().addItem(new ItemStack(Material.valueOf(crop.toString()), qtd));
						p.sendMessage("§a§lARMAZEM §aVoce pegou " + qtd + " " + crop.toString() + ".");
						CropManager.cm.removeCrop(rgName, crop, qtd);
					}
				}
			}
		}
	}

	public void venderTudo(Player p, Crops crop) {
		String rgName = ArmazemManager.am.getRegionAtPlayer(p).getId();
		int price = CropManager.cm.getCropPrice(crop);
		int qtd = CropManager.cm.getCropStored(rgName, crop);

		switch (crop) {
		case CACTUS:
			Main.econ.depositPlayer(p, price);
			CropManager.cm.removeCrop(rgName, crop, qtd);
			p.sendMessage("§a§lARMAZEM §aVoce vendeu todos os crops por " + price * qtd);
			atualizarInv(p);
			break;
		case MELON:
			Main.econ.depositPlayer(p, price);
			CropManager.cm.removeCrop(rgName, crop, qtd);
			p.sendMessage("§a§lARMAZEM §aVoce vendeu todos os crops por " + price * qtd);
			atualizarInv(p);
			break;
		case NETHER_STALK:
			Main.econ.depositPlayer(p, price);
			CropManager.cm.removeCrop(rgName, crop, qtd);
			p.sendMessage("§a§lARMAZEM §aVoce vendeu todos os crops por " + price * qtd);
			atualizarInv(p);
			break;
		case PUMPKIN:
			Main.econ.depositPlayer(p, price);
			CropManager.cm.removeCrop(rgName, crop, qtd);
			p.sendMessage("§a§lARMAZEM §aVoce vendeu todos os crops por " + price * qtd);
			atualizarInv(p);
			break;
		case SUGAR_CANE:
			Main.econ.depositPlayer(p, price);
			CropManager.cm.removeCrop(rgName, crop, qtd);
			p.sendMessage("§a§lARMAZEM §aVoce vendeu todos os crops por " + price * qtd);
			atualizarInv(p);
			break;
		case WHEAT:
			Main.econ.depositPlayer(p, price);
			CropManager.cm.removeCrop(rgName, crop, qtd);
			p.sendMessage("§a§lARMAZEM §aVoce vendeu todos os crops por " + price * qtd);
			atualizarInv(p);
			break;
		default:
			break;
		}
	}
	
	public void atualizarInv(Player p) {
		Comandos.openArmazem(p);
	}

}
