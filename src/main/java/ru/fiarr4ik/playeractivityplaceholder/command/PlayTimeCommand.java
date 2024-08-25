package ru.fiarr4ik.playeractivityplaceholder.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.fiarr4ik.playeractivityplaceholder.placeholder.ActivityPlaceholder;

import java.util.HashMap;
import java.util.UUID;

import static ru.fiarr4ik.playeractivityplaceholder.utils.FormatUtils.formatTime;

    public class PlayTimeCommand implements CommandExecutor {

        private ActivityPlaceholder activityPlaceholder;

        public PlayTimeCommand(ActivityPlaceholder activityPlaceholder) {
            this.activityPlaceholder = activityPlaceholder;
        }

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            Player player = (Player) sender;

            HashMap<UUID, Long> idleTimes = activityPlaceholder.getIdleTimes();
            HashMap<UUID, Long> activeTimes = activityPlaceholder.getActiveTimes();
            HashMap<UUID, Long> onlineTimes = activityPlaceholder.getOnlineTimes();

            long activeTimeTicks = activeTimes.getOrDefault(player.getUniqueId(), 0L);
            String activeTimeString = formatTime(activeTimeTicks);

            long onlineTimeTicks = onlineTimes.getOrDefault(player.getUniqueId(), 0L);
            String onlineTimeString = formatTime(onlineTimeTicks);

            long idleTimeTicks = idleTimes.getOrDefault(player.getUniqueId(), 0L);
            String idleTimeString = formatTime(idleTimeTicks);

            player.sendMessage(ChatColor.GOLD + sender.getName() + ":");
            player.sendMessage(ChatColor.AQUA + "Общее время игры " + onlineTimeString);
            player.sendMessage(ChatColor.AQUA + "Активное время " + activeTimeString);
            player.sendMessage(ChatColor.AQUA + "Время в афк " + idleTimeString);

            return true;
        }
    }
