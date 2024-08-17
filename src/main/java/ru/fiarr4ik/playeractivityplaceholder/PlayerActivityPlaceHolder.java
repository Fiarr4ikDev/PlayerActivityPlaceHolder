package ru.fiarr4ik.playeractivityplaceholder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fiarr4ik.playeractivityplaceholder.placeholder.ActivityPlaceholder;

    public final class PlayerActivityPlaceHolder extends JavaPlugin implements Listener {

        @Override
        public void onEnable() {
            new ActivityPlaceholder(this).register();
            Bukkit.getPluginManager().registerEvents(this, this);
            getLogger().info("Плагин PlayerActivityPlaceHolder запущен");
        }

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.BLUE + "[OceanActivity] "
                    + ChatColor.AQUA + "разработан для проекта "
                    + ChatColor.DARK_GRAY + "Mythical World "
                    + ChatColor.AQUA + "разработчиком "
                    + ChatColor.LIGHT_PURPLE + "velier");
        }

    }
