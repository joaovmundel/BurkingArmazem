package me.joao.armazem.principal;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.joao.armazem.conexao.Conexao;
import me.joao.armazem.conexao.CropManager;
import me.joao.armazem.conexao.DBManager;
import me.joao.armazem.crops.Crops;
import me.joao.armazem.manager.Armazem;
import me.joao.armazem.manager.ArmazemManager;

public class Comandos implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("armazem")) {
				if (p.hasPermission("armazem.use")) {
					if (ArmazemManager.am.getRegionAtPlayer(p) != null) {
						String rgName = ArmazemManager.am.getRegionAtPlayer(p).getId();
						DBManager dbm = new DBManager(new Conexao());
						if (!(dbm.exists(rgName))) {
							if (CropManager.cm.permitedWorld(p.getWorld().getName())) {
								Armazem arm = new Armazem(rgName);
								dbm.inserir(arm);
							}
						}
						if (args.length == 0) {
							if (ArmazemManager.am.getRegionAtPlayer(p) != null) {
								if (ArmazemManager.am.armazemEnabled(p)) {
									if (ArmazemManager.am.canOpenArmazem(p)) {
										openArmazem(p);
										p.sendMessage("§a§lARMAZEM §aVoce abriu o armazem.");
									} else {
										p.sendMessage("§c§lARMAZEM §cVoce nao pode abrir o armazem dessa regiao");
										return true;
									}
								} else {
									p.sendMessage("§c§lARMAZEM §cArmazem desativado nesse terreno.");
									return true;
								}
							} else {
								p.sendMessage("§c§lARMAZEM §cVoce nao está dentro de uma regiao valida.");
								return true;
							}
						} else {
							if (args[0].equalsIgnoreCase("ativar")) {
								if (args.length == 1) {
									if (p.hasPermission("armazem.toggle")) {
										if (ArmazemManager.am.isOwner(p)) {
											ArmazemManager.am.setArmazemState(p, true);
											p.sendMessage("§2§lARMAZEM §aVoce §a§lATIVOU§a o armazem no terreno.");
										} else {
											p.sendMessage("§cVoce nao e o dono dessa area.");
											return true;
										}
									} else {
										p.sendMessage("§cVoce nao tem acesso a esse comando.");
										return true;
									}
								} else {
									p.sendMessage("§cVoce quis dizer /armazem ativar ?");
									return true;
								}
							} else if (args[0].equalsIgnoreCase("desativar")) {
								if (args.length == 1) {
									if (p.hasPermission("armazem.toggle")) {
										if (ArmazemManager.am.isOwner(p)) {
											ArmazemManager.am.setArmazemState(p, false);
											p.sendMessage("§c§lARMAZEM §cVoce §c§lDESATIVOU§c o armazem no terreno.");
										} else {
											p.sendMessage("§cVoce nao e o dono dessa area.");
											return true;
										}
									} else {
										p.sendMessage("§cVoce nao tem acesso a esse comando.");
										return true;
									}
								} else {
									p.sendMessage("§cVoce quis dizer /armazem desativar ?");
									return true;
								}
							} else if (args[0].equalsIgnoreCase("toggle")) {
								if (args.length == 2) {
									if (ArmazemManager.am.isOwner(p)) {
										if (p.hasPermission("armazem.toggle")) {
											if (args[1].equalsIgnoreCase("cacto")) {
												if (p.hasPermission("armazem.cacto")) {
													Crops crop = Crops.CACTUS;
													CropManager.cm.setCropPermtied(rgName, crop,
															!CropManager.cm.cropPermited(rgName, crop));
													p.sendMessage(
															"§a§lARMAZEM §aArmazenamento de " + args[1] + " em estado: "
																	+ CropManager.cm.cropPermited(rgName, crop));
												} else {
													p.sendMessage(
															"§cVoce nao tem permissao para usar o armazem pra esse crop.");
													return true;
												}
											} else if (args[1].equalsIgnoreCase("cana")) {
												if (p.hasPermission("armazem.cana")) {
													Crops crop = Crops.SUGAR_CANE;
													CropManager.cm.setCropPermtied(rgName, crop,
															!CropManager.cm.cropPermited(rgName, crop));
													p.sendMessage(
															"§a§lARMAZEM §aArmazenamento de " + args[1] + " em estado: "
																	+ CropManager.cm.cropPermited(rgName, crop));
												} else {
													p.sendMessage(
															"§cVoce nao tem permissao para usar o armazem pra esse crop.");
													return true;
												}

											} else if (args[1].equalsIgnoreCase("fungo")) {
												if (p.hasPermission("armazem.fungo")) {
													Crops crop = Crops.NETHER_STALK;
													CropManager.cm.setCropPermtied(rgName, crop,
															!CropManager.cm.cropPermited(rgName, crop));
													p.sendMessage(
															"§a§lARMAZEM §aArmazenamento de " + args[1] + " em estado: "
																	+ CropManager.cm.cropPermited(rgName, crop));
												} else {
													p.sendMessage(
															"§cVoce nao tem permissao para usar o armazem pra esse crop.");
													return true;
												}

											} else if (args[1].equalsIgnoreCase("trigo")) {
												if (p.hasPermission("armazem.trigo")) {
													Crops crop = Crops.WHEAT;
													CropManager.cm.setCropPermtied(rgName, crop,
															!CropManager.cm.cropPermited(rgName, crop));
													p.sendMessage(
															"§a§lARMAZEM §aArmazenamento de " + args[1] + " em estado: "
																	+ CropManager.cm.cropPermited(rgName, crop));
												} else {
													p.sendMessage(
															"§cVoce nao tem permissao para usar o armazem pra esse crop.");
													return true;
												}

											} else if (args[1].equalsIgnoreCase("abobora")) {
												if (p.hasPermission("armazem.abobora")) {
													Crops crop = Crops.PUMPKIN;
													CropManager.cm.setCropPermtied(rgName, crop,
															!CropManager.cm.cropPermited(rgName, crop));
													p.sendMessage(
															"§a§lARMAZEM §aArmazenamento de " + args[1] + " em estado: "
																	+ CropManager.cm.cropPermited(rgName, crop));
												} else {
													p.sendMessage(
															"§cVoce nao tem permissao para usar o armazem pra esse crop.");
													return true;
												}

											} else if (args[1].equalsIgnoreCase("melancia")) {
												if (p.hasPermission("armazem.melancia")) {
													Crops crop = Crops.MELON;
													CropManager.cm.setCropPermtied(rgName, crop,
															!CropManager.cm.cropPermited(rgName, crop));
													p.sendMessage(
															"§a§lARMAZEM §aArmazenamento de " + args[1] + " em estado: "
																	+ CropManager.cm.cropPermited(rgName, crop));
												} else {
													p.sendMessage(
															"§cVoce nao tem permissao para usar o armazem pra esse crop.");
													return true;
												}

											} else {
												p.sendMessage(
														"§cArgumentos validos: [cacto/cana/fungo/trigo/abobora/melancia]");
												return true;
											}

										} else {
											p.sendMessage("§cVoce nao tem acesso a esse comando.");
											return true;
										}
									} else {
										p.sendMessage("§cVoce nao e o dono dessa area.");
										return true;
									}
								} else {
									p.sendMessage("§cUso correto /armazem toggle [crop]");
									return true;
								}
							} else {
								p.sendMessage("§cArgumentos validos: [ativar/desativar].");
								return true;
							}
						}
					} else {
						p.sendMessage("§cVoce nao está em uma regiao valida.");
						return true;
					}
				} else {
					p.sendMessage("§c§lARMAZEM §cVoce nao tem acesso a esse comando.");
					return true;
				}
			}
		}
		return false;
	}

	public static void openArmazem(Player p) {
		String rgName = ArmazemManager.am.getRegionAtPlayer(p).getId();
		Inventory menu = Bukkit.createInventory(null, 9 * 3, "Armazem");

		Set<String> section = Main.getInstance().getConfig().getConfigurationSection("Crops").getKeys(false);

		for (String item : section) {
			CropManager cm = new CropManager();
			ItemStack icon = new ItemStack(Material.getMaterial(item));

			ItemMeta icmeta = icon.getItemMeta();

			icmeta.setDisplayName(cm.getCropName(Crops.valueOf(item)).replace("&", "§"));

			ArrayList<String> lore = new ArrayList<String>();
			for (String linha : cm.getLore(Crops.valueOf(item))) {
				int stored = CropManager.cm.getCropStored(rgName, Crops.valueOf(item));
				boolean permited = CropManager.cm.cropPermited(rgName, Crops.valueOf(item));
				lore.add(linha.replace("&", "§").replace("@qtd", "" + stored).replace("@state", permited + ""));
			}
			icmeta.setLore(lore);

			icon.setItemMeta(icmeta);

			menu.setItem(cm.getCropSlot(Crops.valueOf(item)), icon);
			p.openInventory(menu);
		}

	}

}
