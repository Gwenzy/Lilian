package fr.thaksbots.base.commands.admin;

import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Shû~ on 07/02/2018.
 */
public class DisableCommand extends Command {

    public DisableCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.setHelpContent("Désactive une fonctionnalité du bot");
        this.addAuthorizedClient("310400283224178688");
        this.addAuthorizedClient(Credentials.ID_DEVELOPPER);
        this.addNeededArg("Musique/Notifs/Stats");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                String choix = getArgs(event.getMessage().getFormattedContent())[0];
                if(choix.equalsIgnoreCase("Musique")){
                    EnableCommand.MUSIC = false;
                } else if(choix.equalsIgnoreCase("Notifs")){
                    EnableCommand.NOTIFS = false;
                } else if(choix.equalsIgnoreCase("Stats")){
                    EnableCommand.STATS = false;
                } else {
                    RequestBuffer.request(()->event.getChannel().sendMessage("Fonctionnalité "+choix+" inconnue"));
                    return;
                }

                RequestBuffer.request(()->event.getChannel().sendMessage("La fonctionnalité "+choix+" a été désactivée"));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
