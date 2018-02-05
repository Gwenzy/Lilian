package fr.thaksbots.base.commands.admin;


import fr.thaksbots.base.Base;
import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.AliasAlreadyExistsException;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by gwend on 14/11/2017.
 */
public class LogoutCommand extends Command {
    public LogoutCommand(String name, String prefix, boolean enable) {
        super(name, prefix, enable);
        try {
            this.addAlias("disconnect");
            this.addAuthorizedClient(Credentials.ID_DEVELOPPER);
            this.setHelpContent("Makes the bot disconnect : reserved to bot's developer");
        } catch (AliasAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                event.getChannel().sendMessage("Hey ! I'm leaving now, see you soon ;-)");
                Base.logged.logout();
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
