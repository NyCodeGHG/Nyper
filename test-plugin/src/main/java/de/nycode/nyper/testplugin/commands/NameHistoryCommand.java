package de.nycode.nyper.testplugin.commands;

import dev.schlaubi.mojang_api.MojangApi;
import dev.schlaubi.mojang_api.services.user.NameHistoryEntry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NameHistoryCommand implements CommandExecutor {

    private static final MojangApi mojangApi = MojangApi.create();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Provide a uuid. /namehistory <uuid>");
            return true;
        }
        try {
            var uuid = UUID.fromString(args[0]);
            // This is blocking code
            // *** Don't do this in production! ***
            var entries = mojangApi.getUsers().findUserNamesById(uuid);
            if (entries == null || entries.size() == 0) {
                sender.sendMessage("No entry was found!");
                return true;
            }

            sender.sendMessage("Found these entries:");
            for (NameHistoryEntry entry : entries) {
                if (entry.getChangedToAt() == null) {
                    sender.sendMessage(String.format("%s", entry.getName()));
                } else {
                    sender.sendMessage(String.format("%s - %s", entry.getName(), entry.getChangedToAt()));
                }
            }
            return true;
        } catch (IllegalArgumentException ex) {
            sender.sendMessage("Please provide a valid uuid.");
            return true;
        }
    }
}
