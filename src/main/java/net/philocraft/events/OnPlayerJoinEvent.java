package net.philocraft.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.littlebigowl.api.models.EssentialsPermission;
import net.philocraft.AdminEssentials;

public class OnPlayerJoinEvent implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if(EssentialsPermission.isVanished(player)) {
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(AdminEssentials.getPlugin(), player);
            }
        
        } else {
            for(Player vanishedPlayer : EssentialsPermission.getVanishedPlayers()) {
                player.hidePlayer(AdminEssentials.getPlugin(), vanishedPlayer);
            }
        }
    }

}
