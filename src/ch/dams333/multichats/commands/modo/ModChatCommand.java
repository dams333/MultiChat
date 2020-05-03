package ch.dams333.multichats.commands.modo;

import ch.dams333.multichats.MultiChats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ModChatCommand implements CommandExecutor {
    MultiChats main;
    public ModChatCommand(MultiChats multiChats) {
        this.main = multiChats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length >= 1){

            if(args[0].equalsIgnoreCase("list")){
                if(args.length == 1){

                    sender.sendMessage(ChatColor.GREEN + "======= Tous les chats =======");

                    for(String chat : main.getConfig().getKeys(false)){
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "-" + chat);
                    }

                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/modchat list");
                return true;
            }

            if(args[0].equalsIgnoreCase("enter")){
                if(args.length == 2){

                    String chat = args[1];

                    if(main.getConfig().getKeys(false).contains(chat)){

                        main.getConfig().getConfigurationSection(chat).set(sender.getName(), "Moderator");
                        main.saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Vous ête bien entré dans le chat");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Le chat n'existe pas");
                    return true;

                }
                sender.sendMessage(ChatColor.RED + "/modchat enter <chat>");
                return true;
            }

            if(args[0].equalsIgnoreCase("delete")){
                if(args.length == 2){

                    String chat = args[1];

                    if(main.getConfig().getKeys(false).contains(chat)){

                        main.getConfig().set(chat, null);
                        main.saveConfig();

                        sender.sendMessage(ChatColor.GREEN + "Chat supprimé avec succès");

                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Le chat n'existe pas");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/modchat delete <chat>");
                return true;
            }

        }
        sender.sendMessage(ChatColor.RED + "/modchat <list/enter/delete>");
        return false;
    }
}
