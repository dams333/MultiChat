package ch.dams333.multichats.commands.players;

import ch.dams333.multichats.MultiChats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    MultiChats main;
    public ChatCommand(MultiChats multiChats) {
        this.main = multiChats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length >= 1){

            if(args[0].equalsIgnoreCase("create")){
                if(args.length == 2){

                    String name = args[1];
                    if(!main.getConfig().isConfigurationSection(name)){
                        main.getConfig().createSection(name);
                        main.getConfig().getConfigurationSection(name).set(sender.getName(), "Owner");
                        main.saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Chat créé ave succès");


                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Le chat existe déjà");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/chat create <name>");
                return true;
            }

            if(args[0].equalsIgnoreCase("join")){
                if(args.length == 1){

                    if(main.join.keySet().contains(sender)){

                        main.getConfig().getConfigurationSection(main.join.get(sender)).set(sender.getName(), "Invited");
                        main.saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Chat '" + main.join.get(sender) + "' rejoint avec succès");

                        main.join.remove(sender);

                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Vous n'avez pas de demande en attente");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/chat join");
                return true;
            }
            if(args[0].equalsIgnoreCase("deny")){
                if(args.length == 1){

                    if(main.join.keySet().contains(sender)){

                        sender.sendMessage(ChatColor.GREEN + "Demande pour rejoindre le chat '" + main.join.get(sender) + "' rejetée avec succès");

                        main.join.remove(sender);

                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Vous n'avez pas de demande en attente");

                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/chat deny");
                return true;
            }

            if(args[0].equalsIgnoreCase("leave")){
                if(args.length == 2){

                    String chat = args[1];

                    if(main.getConfig().isConfigurationSection(chat)) {

                        main.getConfig().getConfigurationSection(chat).set(sender.getName(), null);
                        main.saveConfig();

                        sender.sendMessage(ChatColor.GREEN + "Vous avez bien quitté le chat '" + chat + "'");

                        if(main.getConfig().getConfigurationSection(chat).getKeys(false).size() == 0){
                            main.getConfig().set(chat, null);
                            main.saveConfig();
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Comme il n'y avait plus personne dans le chat il a été supprimé");
                        }

                        return true;
                    }

                    sender.sendMessage(ChatColor.RED + "Vous ne faites pas partie du chat '" + chat + "'");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/chat leave <name>");
                return true;
            }

            if(args[0].equalsIgnoreCase("list")){
                if(args.length == 1){

                    sender.sendMessage(ChatColor.GREEN + "======= Vos chats =======");

                    for(String chat : main.getConfig().getKeys(false)){

                        if(main.getConfig().getConfigurationSection(chat).getKeys(false).contains(sender.getName())){
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "-" + chat);
                        }

                    }

                    return true;

                }
                sender.sendMessage(ChatColor.RED + "/chat list");
                return true;
            }

            if(args[0].equalsIgnoreCase("invite")){
                if(args.length == 3){

                    String chat = args[2];
                    String player = args[1];

                    if(Bukkit.getPlayer(player) != null){

                        Player p = Bukkit.getPlayer(player);

                        if(main.join.keySet().contains(p)){
                            sender.sendMessage(ChatColor.RED + "Le joueur a déjà une invitation");
                            return true;
                        }

                        if(main.getConfig().isConfigurationSection(chat)){

                            for(String key : main.getConfig().getConfigurationSection(chat).getKeys(false)){

                                if(key.equals(sender.getName())){

                                    p.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " vous invite dans le chat '" + chat + "'");
                                    sender.sendMessage(ChatColor.GREEN + "Joueur invité avec succès");
                                    main.join.put(p, chat);

                                    return true;

                                }

                            }
                            sender.sendMessage(ChatColor.RED + "Vous devez faire partie du chat '" + chat + "' pour inviter un joueur");
                            return true;
                        }
                        sender.sendMessage(ChatColor.RED + "Le chat " + chat + " n'existe pas");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Le joueur " + player + " n'est pas en ligne");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "/chat invite <player> <chat>");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "/chat <create/invite/join/deny/leave/list>");
        return false;
    }
}
