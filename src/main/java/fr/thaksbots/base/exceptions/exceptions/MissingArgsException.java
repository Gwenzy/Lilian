package fr.thaksbots.base.exceptions.exceptions;

import fr.thaksbots.base.Base;
import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by Shû~ on 16/11/2017.
 */
public class MissingArgsException extends ThaksbotException {

    public MissingArgsException(IChannel channel) {
        super("Args are missing for this command");
        channel.sendMessage("Il manque certains arguments pour exécuter correctement cette commande, essayez "+ Base.cm.getCommand("help").getFullCommand()+" pour voir ce qu'il manque");
    }
}
