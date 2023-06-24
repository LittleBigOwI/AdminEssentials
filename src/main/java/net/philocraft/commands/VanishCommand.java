package net.philocraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.littlebigowl.api.constants.Colors;
import dev.littlebigowl.api.errors.InvalidSenderException;
import dev.littlebigowl.api.models.EssentialsPermission;
import net.philocraft.AdminEssentials;

public class VanishCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(!label.equals("vanish")) {
            return new InvalidSenderException().sendCause(sender);
        }

        if(sender instanceof Player && EssentialsPermission.isVanished((Player)sender) && args.length == 0) {
            Player player = (Player)sender;
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(AdminEssentials.getPlugin(), player);
            }

            EssentialsPermission.removeVanished(player);
            AdminEssentials.blueMap.getWebApp().setPlayerVisibility(player.getUniqueId(), true);
            player.sendMessage(Colors.INFO.getChatColor() + "You are now visible to all players.");
        
        } else if(sender instanceof Player && !EssentialsPermission.isVanished((Player)sender) && args.length == 0){
            Player player = (Player)sender;
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(AdminEssentials.getPlugin(), player);
            }

            EssentialsPermission.addVanished(player);
            AdminEssentials.blueMap.getWebApp().setPlayerVisibility(player.getUniqueId(), false);
            player.sendMessage(Colors.INFO.getChatColor() + "You are now invisible to all players.");
        
        } else if(sender instanceof ConsoleCommandSender && args.length == 1) {
            OfflinePlayer player = Bukkit.getPlayer(args[0]);
            if(player == null) {
                for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if(offlinePlayer.getName().equals(args[0])) {
                        player = offlinePlayer;
                    }
                }
            }

            if(!EssentialsPermission.isVanished(player.getUniqueId())) {
                EssentialsPermission.addVanished(player.getUniqueId());
                AdminEssentials.blueMap.getWebApp().setPlayerVisibility(player.getUniqueId(), false);
                sender.sendMessage(Colors.INFO_DARK.getChatColor() + player.getName() + Colors.INFO.getChatColor() + " is now invisible to all players");

            } else {
                EssentialsPermission.removeVanished(player.getUniqueId());
                AdminEssentials.blueMap.getWebApp().setPlayerVisibility(player.getUniqueId(), true);
                sender.sendMessage(Colors.INFO_DARK.getChatColor() + player.getName() + Colors.INFO.getChatColor() + " is now visible to all players");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> playerNames = new ArrayList<>();
        
        Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
        for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            playerNames.add(player.getName());
        }
        
        return args.length == 1 ? playerNames : new ArrayList<>();
    }
    
}
