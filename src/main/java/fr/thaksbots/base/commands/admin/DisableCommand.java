package fr.thaksbots.base.commands.admin;

import fr.thaksbots.base.Base;
import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 17/11/2017.
 */
public class DisableCommand extends Command {

    public DisableCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedClient(Credentials.ID_DEVELOPPER);
        this.addNeededArg("Command");
        this.setHelpContent("Désactive une commande spéciale : réservé au développeur");
        this.setAcceptPrivate(true);
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                Base.cm.getCommand(getArgs(event.getMessage().getFormattedContent())[0]).setEnabled(false);
                event.getChannel().sendMessage(getArgs(event.getMessage().getFormattedContent())[0]+" command successfully disabled");
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        } catch(NullPointerException e){
            event.getChannel().sendMessage("Error while attempting to disable command : command not found");
        }
    }
}
