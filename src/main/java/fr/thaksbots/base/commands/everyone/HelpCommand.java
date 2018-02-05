package fr.thaksbots.base.commands.everyone;


import fr.thaksbots.base.Base;
import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

/**
 * Created by gwend on 14/11/2017.
 */
public class HelpCommand extends Command {
    public HelpCommand(String name, String prefix, boolean enable) {
        super(name, prefix, enable);
        this.setHelpContent("Affiche ce message ou une aide détaillé pour la commande");
        this.addAuthorizedGroup("@everyone");
        this.addFacultativeArg("command");

    }

    @Override
    public void handle(MessageReceivedEvent event) {
        try {
            if (canBeExecuted(event)) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.withTitle("Commande d'aide");
                eb.withColor(Color.CYAN);

                if (getArgs(event.getMessage().getFormattedContent()).length != 0 && Base.cm.getTextCommands().contains(getArgs(event.getMessage().getFormattedContent())[0].replaceAll("!!", ""))) {
                    Command cmd = null;
                    for (Command c : Base.cm.getCommands()) {
                        String command = getArgs(event.getMessage().getFormattedContent())[0].replace("!!", "");

                        if (c.getName().equalsIgnoreCase(command) || c.getAliases().contains(command)) {
                            cmd = c;
                            break;
                        }
                    }

                    String content = cmd.getHelpContent() + "\n\n" + (cmd.getAliases().size() != 0 ? "Aliases:" : "No alias");
                    for (String alias : cmd.getAliases()) {
                        content += "\n" + cmd.getPrefix() + alias;
                    }
                    content += "\n\nUtilisateurs autorisés : \n";
                    for (String client : cmd.getAuthorizedClients()) {
                        content += Base.logged.getUserByID(Long.parseLong(client)).getName() + "#" + Base.logged.getUserByID(Long.parseLong(client)).getDiscriminator() + "\n";

                    }
                    content += "\nGroupes autorisés : \n";
                    for (String client : cmd.getAuthorizedGroups()) {
                        IRole role = null;
                        try {
                            long id = Long.parseLong(client);
                            role = event.getGuild().getRoleByID(id);
                        } catch (Exception e) {
                            try {
                                role = event.getGuild().getRolesByName(client).get(0);
                            } catch (Exception e1) {
                            }
                        }
                        if (role != null)
                            content += role.getName() + "\n";

                    }
                    content += "\nSalons interdits : \n";
                    for (String channel : cmd.getForbiddenChannels()) {
                        try{
                            IChannel ch = event.getGuild().getChannelByID(Long.parseLong(channel));
                            content+=ch.mention();
                        }catch(Exception e){}
                    }
                    content += "\nSalons obligatoires : \n";
                    for (String channel : cmd.getForcedChannels()) {
                        try{
                            IChannel ch = event.getGuild().getChannelByID(Long.parseLong(channel));
                            content+=ch.mention();
                        }catch(Exception e){}
                    }
                    eb.appendField(cmd.getHelpTitle()+(!cmd.getEnabled()?"♦DÉSACTIVÉ♦":""), content, false);


                } else
                    for (Command cmd : Base.cm.getCommands()) {

                        String content = cmd.getHelpContent() + "\n\n" + (cmd.getAliases().size() != 0 ? "Alis:" : "Pas d'Alias");
                        for (String alias : cmd.getAliases()) {
                            content += "\n" + cmd.getPrefix() + alias;
                        }

                        if(!(cmd.getName()).equals(""))
                            eb.appendField(cmd.getHelpTitle()+(!cmd.getEnabled()?"♦DÉSACTIVÉ♦":""), content, false);

                    }
                eb.withFooterText(Credentials.GITHUB);
                event.getChannel().sendMessage(eb.build());

            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}