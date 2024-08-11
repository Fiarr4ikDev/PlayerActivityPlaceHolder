package ru.fiarr4ik.playeractivityplaceholder;

import org.bukkit.plugin.java.JavaPlugin;
import ru.fiarr4ik.playeractivityplaceholder.placeholder.ActivityPlaceholder;

    public final class PlayerActivityPlaceHolder extends JavaPlugin {

        @Override
        public void onEnable() {
            new ActivityPlaceholder(this).register();
        }

        @Override
        public void onDisable() {

        }

    }
