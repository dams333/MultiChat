package ch.dams333.multichats.commands.players;

import ch.dams333.multichats.MultiChats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MpCommand implements CommandExecutor {
    MultiChats main;
    public MpCommand(MultiChats multiChats) {
        this.main = multiChats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length >= 2){

            String pseudo = args[0];

            if(Bukkit.getPlayer(pseudo) != null){

                Player target = Bukkit.getPlayer(pseudo);
                Player send = (Player) sender;
                StringBuilder m = new StringBuilder();
                for(String message : args){
                    if(!message.equals(args[0])){
                        m.append(message + " ");
                    }
                }
                String msg = m.toString();

                target.sendMessage(ChatColor.GOLD + "[" + send.getName() + " --> Vous]" + ChatColor.YELLOW + " > " + ChatColor.WHITE + msg);
                send.sendMessage(ChatColor.GOLD + "[Vous --> " + target.getName() + "]" + ChatColor.YELLOW + " > " + ChatColor.WHITE + msg);
                for(Player mod : main.allSpoter){
                    mod.sendMessage(ChatColor.DARK_BLUE + "[Spotter]" + ChatColor.GOLD + "[" + send.getName() + " --> " + target.getName() + "]" + ChatColor.YELLOW + " > " + ChatColor.WHITE + msg);
                }

                for(Player modo : main.playerSpotter.keySet()){

                    for(Player player : main.playerSpotter.get(modo)){

                        if(player == send || player == target){

                            modo.sendMessage(ChatColor.DARK_BLUE + "[Spotter]" + ChatColor.GOLD + "[" + send.getName() + " --> " + target.getName() + "]" + ChatColor.YELLOW + " > " + ChatColor.WHITE + msg);

                        }

                    }

                }

                return true;
            }
            sender.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "/mp <player> <message>");
        return false;
    }
}
