package net.philocraft;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import de.bluecolored.bluemap.api.BlueMapAPI;
import dev.littlebigowl.api.EssentialsAPI;
import net.philocraft.commands.OpeninvCommand;
import net.philocraft.commands.OpenecCommand;
import net.philocraft.commands.VanishCommand;
import net.philocraft.events.OnPlayerJoinEvent;

public final class AdminEssentials extends JavaPlugin {

    public static final EssentialsAPI api = (EssentialsAPI)Bukkit.getServer().getPluginManager().getPlugin("EssentialsAPI");
    public static BlueMapAPI blueMap;
    
    private static AdminEssentials plugin;

    public static AdminEssentials getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        BlueMapAPI.onEnable(bluemap -> {
            blueMap = bluemap;
        });

        plugin = this;

        PermissionAttachment attachement = Bukkit.getConsoleSender().addAttachment(this);
        attachement.setPermission("philocraft.admin.vanish", true);
        attachement.setPermission("philocraft.admin.openinv", true);
        attachement.setPermission("philocraft.admin.openec", true);

        //!REGISTER COMMANDS
        this.getCommand("vanish").setExecutor(new VanishCommand());
        this.getCommand("openinv").setExecutor(new OpeninvCommand());
        this.getCommand("openec").setExecutor(new OpenecCommand());

        //!REGISTER EVENTS
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}
