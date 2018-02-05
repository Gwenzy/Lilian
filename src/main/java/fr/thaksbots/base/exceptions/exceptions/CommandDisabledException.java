package fr.thaksbots.base.exceptions.exceptions;

import fr.thaksbots.base.Base;
import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by Shû~ on 17/11/2017.
 */
public class CommandDisabledException extends ThaksbotException {
    public CommandDisabledException(IChannel channel){
        super("La commande a été désactivée");
        channel.sendMessage("Woops, cette commande a été désactivée par le développeur, essayez de le contacter avec "+ Base.cm.getCommand("dev").getFullCommand());
    }
}
