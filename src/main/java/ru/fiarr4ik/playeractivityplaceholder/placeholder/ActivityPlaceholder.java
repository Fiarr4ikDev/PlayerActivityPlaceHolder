package ru.fiarr4ik.playeractivityplaceholder.placeholder;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

    public class ActivityPlaceholder extends PlaceholderExpansion {

        private final JavaPlugin plugin;
        private final HashMap<UUID, Location> lastLocations = new HashMap<>();
        private final HashMap<UUID, Long> idleTimes = new HashMap<>();
        private final HashMap<UUID, Long> activeTimes = new HashMap<>();
        private final HashMap<UUID, Long> onlineTimes = new HashMap<>();
        private ResourceBundle messages;

        public ActivityPlaceholder(JavaPlugin plugin) {
            this.plugin = plugin;
            this.messages = ResourceBundle.getBundle("messages", Locale.ENGLISH); // Default language
            Bukkit.getScheduler().runTaskTimer(plugin, this::updatePlayerActivityTime, 1L, 1L);
        }

        @Override
        public boolean register() {
            if (!PlaceholderAPI.isRegistered(getIdentifier())) {
                return super.register();
            } else {
                return false;
            }
        }

        @Override
        public @NotNull String getIdentifier() {
            return "activitytime";
        }

        @Override
        public @NotNull String getAuthor() {
            return "Fiarr4ik";
        }

        @Override
        public @NotNull String getVersion() {
            return "0.0.3";
        }

        @Override
        public String onPlaceholderRequest(Player player, String identifier) {
            if (player == null) {
                return "";
            }

            String locale = "ru";
            String language = player.getLocale(); //TODO

            this.messages = ResourceBundle.getBundle("messages", new Locale(locale));

            if (identifier.equals("afk_time")) {
                long idleTimeTicks = idleTimes.getOrDefault(player.getUniqueId(), 0L);
                return String.format(messages.getString("afk_time"), formatTime(idleTimeTicks));
            }

            if (identifier.equals("active_time")) {
                long activeTimeTicks = activeTimes.getOrDefault(player.getUniqueId(), 0L);
                return String.format(messages.getString("active_time"), formatTime(activeTimeTicks));
            }

            for (int i = 1; i <= 10; i++) {
                if (identifier.equals("top" + i + "_player")) {
                    return getTopPlayerName(i - 1, activeTimes);
                }

                if (identifier.equals("top" + i + "_active_time")) {
                    return getTopPlayerActiveTime(i - 1, activeTimes);
                }

                if (identifier.equals("top" + i + "_afk_player")) {
                    return getTopPlayerName(i - 1, idleTimes);
                }

                if (identifier.equals("top" + i + "_afk_time")) {
                    return getTopPlayerActiveTime(i - 1, idleTimes);
                }
            }

            return null;
        }

        private void updatePlayerActivityTime() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerUUID = player.getUniqueId();
                Location lastLocation = lastLocations.get(playerUUID);
                Location currentLocation = player.getLocation();

                onlineTimes.put(playerUUID, onlineTimes.getOrDefault(playerUUID, 0L) + 1L);

                if (lastLocation != null && lastLocation.getWorld().equals(currentLocation.getWorld())) {
                    if (lastLocation.distanceSquared(currentLocation) < 0.01) {
                        idleTimes.put(playerUUID, idleTimes.getOrDefault(playerUUID, 0L) + 1L);
                    } else {
                        activeTimes.put(playerUUID, activeTimes.getOrDefault(playerUUID, 0L) + 1L);
                    }
                }

                lastLocations.put(playerUUID, currentLocation);
            }
        }

        private String formatTime(long ticks) {
            long totalSeconds = ticks / 20;
            long days = totalSeconds / 86400;
            long hours = (totalSeconds % 86400) / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;

            return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
        }

        private List<Map.Entry<UUID, Long>> getTopPlayers(HashMap<UUID, Long> timeMap) {
            List<Map.Entry<UUID, Long>> timeList = new ArrayList<>(timeMap.entrySet());
            timeList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // сортировка по убыванию
            return timeList;
        }

        private String getTopPlayerName(int index, HashMap<UUID, Long> timeMap) {
            List<Map.Entry<UUID, Long>> topPlayers = getTopPlayers(timeMap);
            if (index < topPlayers.size()) {
                UUID playerUUID = topPlayers.get(index).getKey();
                Player player = Bukkit.getPlayer(playerUUID);
                return player != null ? player.getName() : "Unknown";
            }
            return "N/A";
        }

        private String getTopPlayerActiveTime(int index, HashMap<UUID, Long> timeMap) {
            List<Map.Entry<UUID, Long>> topPlayers = getTopPlayers(timeMap);
            if (index < topPlayers.size()) {
                long timeTicks = topPlayers.get(index).getValue();
                return formatTime(timeTicks);
            }
            return "N/A";
        }

    }
