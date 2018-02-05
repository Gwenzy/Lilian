package fr.thaksbots.base.exceptions.exceptions;

import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by gwend on 14/11/2017.
 */
public class CommandWrongChannelException extends ThaksbotException {

    public CommandWrongChannelException(String name, IChannel channel){
        super(name+" command can't be used in this channel");
        channel.sendMessage("La commande "+name+" ne peut pas être utilisée dans ce salon");
    }
}
