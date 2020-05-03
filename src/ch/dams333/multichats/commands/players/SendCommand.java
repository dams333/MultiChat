package ch.dams333.multichats.commands.players;

import ch.dams333.multichats.MultiChats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCommand implements CommandExecutor {
    MultiChats main;
    public SendCommand(MultiChats multiChats) {
        this.main = multiChats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length >= 2){

            String chat = args [0];

            if(main.getConfig().getKeys(false).contains(chat)){

                if(main.getConfig().getConfigurationSection(chat).getKeys(false).contains(sender.getName())){

                    StringBuilder m = new StringBuilder();
                    for(String message : args){
                        if(!message.equals(args[0])){
                            m.append(message + " ");
                        }
                    }
                    String msg = m.toString();

                    for(Player modo : main.chatSoptter.keySet()) {

                        for (String chatToSpot : main.chatSoptter.get(modo)) {

                            if(chatToSpot.equals(chat)){

                                modo.sendMessage(ChatColor.DARK_BLUE + "[Spotter]" + ChatColor.GOLD + "[" + chat + "] " + ChatColor.YELLOW + sender.getName() + " > " + ChatColor.WHITE + msg);

                            }

                        }
                    }

                    for(String playerName : main.getConfig().getConfigurationSection(chat).getKeys(false)){

                        if(Bukkit.getPlayer(playerName) != null){

                            Bukkit.getPlayer(playerName).sendMessage(ChatColor.GOLD + "[" + chat + "] " + ChatColor.YELLOW + sender.getName() + " > " + ChatColor.WHITE + msg);

                            for(Player modo : main.playerSpotter.keySet()){

                                for(Player player : main.playerSpotter.get(modo)){

                                    if(player == Bukkit.getPlayer(playerName)){

                                        modo.sendMessage(ChatColor.DARK_BLUE + "[Spotter]" + ChatColor.GOLD + "[" + chat + "] " + ChatColor.YELLOW + sender.getName() + " > " + ChatColor.WHITE + msg);

                                    }

                                }

                            }

                        }

                    }

                    for(Player mod : main.allSpoter){
                        mod.sendMessage(ChatColor.DARK_BLUE + "[Spotter]" + ChatColor.GOLD + "[" + chat + "] " + ChatColor.YELLOW + sender.getName() + " > " + ChatColor.WHITE + msg);
                    }

                    return true;
                }

            }
            sender.sendMessage(ChatColor.RED + "Vous ne faites pas partie de ce chat");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "/send <chat> <message>");
        return false;
    }
}
