package ch.dams333.multichats;

import ch.dams333.multichats.commands.modo.ModChatCommand;
import ch.dams333.multichats.commands.modo.SpotterCommand;
import ch.dams333.multichats.commands.players.ChatCommand;
import ch.dams333.multichats.commands.players.MpCommand;
import ch.dams333.multichats.commands.players.SendCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiChats extends JavaPlugin {

    public Map<Player, String> join = new HashMap<>();
    public List<Player> allSpoter = new ArrayList<>();
    public Map<Player, List<String>> chatSoptter = new HashMap<>();
    public Map<Player, List<Player>> playerSpotter = new HashMap<>();

    @Override
    public void onEnable(){

        getCommand("chat").setExecutor(new ChatCommand(this));
        getCommand("send").setExecutor(new SendCommand(this));
        getCommand("modchat").setExecutor(new ModChatCommand(this));
        getCommand("spotter").setExecutor(new SpotterCommand(this));
        getCommand("mp").setExecutor(new MpCommand(this));

    }

}
