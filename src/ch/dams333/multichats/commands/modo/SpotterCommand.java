package ch.dams333.multichats.commands.modo;

import ch.dams333.multichats.MultiChats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpotterCommand implements CommandExecutor {
    MultiChats main;
    public SpotterCommand(MultiChats multiChats) {
        this.main = multiChats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            if (main.allSpoter.contains(sender)) {
                main.allSpoter.remove(sender);
                sender.sendMessage(ChatColor.GREEN + "Vous êtes bien sorti du mode Spotter");
                return true;
            }
            if (!main.allSpoter.contains(sender)) {
                main.allSpoter.add((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Vous êtes bien entré en mode Spotter");
                return true;
            }
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reset")){

                if(main.chatSoptter.containsKey(sender)){
                    main.chatSoptter.remove(sender);
                }
                if(main.allSpoter.contains(sender)){
                    main.allSpoter.remove(sender);
                }
                if(main.playerSpotter.containsKey(sender)){
                    main.playerSpotter.remove(sender);
                }

                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Vos spotter ont été reset");
                return true;

            }
        }

        if(args.length == 2){

            if(args[0].equalsIgnoreCase("player")){

                String pseudo = args[1];
                if(Bukkit.getPlayer(pseudo) != null){

                    Player p = Bukkit.getPlayer(pseudo);

                    if(main.playerSpotter.containsKey(sender)) {

                        List<Player> chats = main.playerSpotter.get(sender);
                        if (chats.contains(p)) {

                            chats.remove(p);
                            main.playerSpotter.remove(sender);
                            main.playerSpotter.put((Player) sender, chats);
                            sender.sendMessage(ChatColor.RED + "Vous ne suivez plus ce joueur");

                            return true;
                        }
                        if (!chats.contains(p)) {

                            chats.add(p);
                            main.playerSpotter.remove(sender);
                            main.playerSpotter.put((Player) sender, chats);
                            sender.sendMessage(ChatColor.GREEN + "Vous suivez dorénavent ce joueur");

                            return true;
                        }

                        return true;
                    }
                    List<Player> chats = new ArrayList<>();
                    chats.add(p);
                    main.playerSpotter.put((Player) sender, chats);
                    sender.sendMessage(ChatColor.GREEN + "Vous suivez dorénavent ce joueur");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Le jouer n'est pas en ligne");
            }

            if(args[0].equalsIgnoreCase("chat")){

                String chat = args[1];
                if(main.getConfig().getKeys(false).contains(chat)){

                    if(main.chatSoptter.containsKey(sender)){

                        List<String> chats = main.chatSoptter.get(sender);
                        if(chats.contains(chat)){

                            chats.remove(chat);
                            main.chatSoptter.remove(sender);
                            main.chatSoptter.put((Player) sender, chats);
                            sender.sendMessage(ChatColor.RED + "Vous ne suivez plus ce chat");

                            return true;
                        }
                        if(!chats.contains(chat)){

                            chats.add(chat);
                            main.chatSoptter.remove(sender);
                            main.chatSoptter.put((Player) sender, chats);
                            sender.sendMessage(ChatColor.GREEN + "Vous suivez dorénavent ce chat");

                            return true;
                        }

                        return true;
                    }
                    List<String> chats = new ArrayList<>();
                    chats.add(chat);
                    main.chatSoptter.put((Player) sender, chats);
                    sender.sendMessage(ChatColor.GREEN + "Vous suivez dorénavent ce chat");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Le chat n'existe pas");
                return true;
            }

            if(args[0].equalsIgnoreCase("player")){


                return true;
            }

        }
        sender.sendMessage(ChatColor.RED + "/spotter (<chat/player/reset>)");
        return false;
    }
}
