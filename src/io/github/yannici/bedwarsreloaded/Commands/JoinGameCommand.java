package io.github.yannici.bedwarsreloaded.Commands;

import io.github.yannici.bedwarsreloaded.ChatWriter;
import io.github.yannici.bedwarsreloaded.Main;
import io.github.yannici.bedwarsreloaded.Game.Game;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinGameCommand extends BaseCommand {

    public JoinGameCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public String getName() {
        return "Join Game";
    }

    @Override
    public String getDescription() {
        return "Joins a specific game";
    }

    @Override
    public String[] getArguments() {
        return new String[]{"game"};
    }

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if(!super.hasPermission(sender)) {
            return false;
        }

        Player player = (Player) sender;
        Game game = this.getPlugin().getGameManager().getGame(args.get(0));

        if(game == null) {
            sender.sendMessage(ChatWriter.pluginMessage(ChatColor.RED + "The given game wasn't found!"));
            return false;
        }

        if(game.playerJoins(player)) {
            sender.sendMessage(ChatWriter.pluginMessage(ChatColor.GREEN + "You successfully joined the game!"));
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "setup";
    }

}
