package net.estinet.gFeatures.Feature.Friendship.Menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.estinet.gFeatures.API.Inventory.InventoryAPI;

public class FriendsPending {
	public void init(List<String> hash, String player){
		InventoryAPI open = makeInventory(hash, Bukkit.getPlayer(player));
		open.open(Bukkit.getPlayer(player));
		Bukkit.getPlayer(player).updateInventory();
	}
	public InventoryAPI makeInventory(List<String> hash, Player p){
		try{
			int amount = 9;
			if(hash.size() < 44){
				amount = (int)(hash.size() / amount) * 9 + 9; 
			}
			else{
				//will add later (Overflow friends (too many) needz more pages) plz future espidev :/:/
				amount = 45;
			}
			InventoryAPI menu = new InventoryAPI(ChatColor.GOLD + "Pending Friend Requests", amount, new InventoryAPI.OptionClickEventHandler() {
				@Override
				public void onOptionClick(InventoryAPI.OptionClickEvent event) {
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("gFeatures"), new Runnable() {
						public void run(){
							FriendsAccept fa = new FriendsAccept();
							fa.init(p, event.getName());
						}
					}, 9L);

					event.setWillClose(false);
					event.setWillDestroy(true);
				}
			}, Bukkit.getServer().getPluginManager().getPlugin("gFeatures"));

			for(int i = 0 ; i < hash.size(); i++){
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
				//SkullMeta sm = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
				SkullMeta sm = (SkullMeta) skull.getItemMeta();
				sm.setOwner(hash.get(i));
				sm.setDisplayName((String) hash.get(i));
				skull.setItemMeta(sm);
				menu.setOption(i, skull);
			}

			return menu;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public ItemStack createItem(Material material, String name, String ... lore){
		ItemStack item = new ItemStack(material, 1);
		List<String> lores = new ArrayList<>();
		for(String lor : lore){
			lores.add(lor);
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
}