package de.nycode.nyper.testplugin;

import de.nycode.nyper.testplugin.commands.NameHistoryCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        //noinspection ConstantConditions
        getCommand("namehistory").setExecutor(new NameHistoryCommand());
    }
}
