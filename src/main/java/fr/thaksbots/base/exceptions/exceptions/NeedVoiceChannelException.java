package fr.thaksbots.base.exceptions.exceptions;

import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by Shû~ on 17/11/2017.
 */
public class NeedVoiceChannelException extends ThaksbotException {
    public NeedVoiceChannelException(IChannel channel){
        super("Bot need to be on a voice channel to execute that command");
        channel.sendMessage("J'ai besoin d'être dans un salon vocal pour exécuter cette commande");
    }
}
