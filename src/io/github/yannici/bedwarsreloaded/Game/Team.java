package io.github.yannici.bedwarsreloaded.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

@SerializableAs("Team")
public class Team implements ConfigurationSerializable {

    private TeamColor color = null;
    private org.bukkit.scoreboard.Team scoreboardTeam = null;
    private String name = null;
    private int maxPlayers = 0;
    private Location spawnLocation = null;

    public Team(Map<String, Object> deserialize) {
        this.name = deserialize.get("name").toString();
        this.maxPlayers = Integer.parseInt(deserialize.get("maxplayers").toString());
        this.color = TeamColor.valueOf(deserialize.get("color").toString().toUpperCase());
        this.spawnLocation = (Location)deserialize.get("spawn");
    }

    public Team(String name, TeamColor color, int maxPlayers, org.bukkit.scoreboard.Team sTeam) {
        this.name = name;
        this.color = color;
        this.maxPlayers = maxPlayers;
        this.scoreboardTeam = sTeam;

        ConfigurationSerialization.registerClass(Team.class);
    }

    public boolean addPlayer(Player player) {
        if(this.scoreboardTeam.getPlayers().size() == this.maxPlayers) {
            return false;
        }

        this.scoreboardTeam.addPlayer(player);
        return true;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void removePlayer(Player player) {
        if(this.scoreboardTeam.hasPlayer(player)) {
            this.scoreboardTeam.removePlayer(player);
        }
    }

    public boolean isInTeam(Player p) {
        if(this.scoreboardTeam.getPlayers().contains(p)) {
            return true;
        }

        return false;
    }

    public void setScoreboardTeam(org.bukkit.scoreboard.Team sbt) {
        this.scoreboardTeam = sbt;
    }

    public TeamColor getColor() {
        return this.color;
    }

    public ChatColor getChatColor() {
        return ChatColor.valueOf(this.color.toString());
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for(OfflinePlayer player : this.scoreboardTeam.getPlayers()) {
            if(player.isOnline()) {
                players.add(player.getPlayer());
            }
        }

        return players;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public void setSpawnLocation(Location spawn) {
        this.spawnLocation = spawn;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> team = new HashMap<>();

        team.put("name", this.name);
        team.put("color", this.color.toString());
        team.put("maxplayers", this.maxPlayers);
        team.put("spawn", this.spawnLocation);
        return team;
    }

}
