package fr.thaksbots.base.commands.admin;


import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.AliasAlreadyExistsException;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 19/11/2017.
 */
public class ForcenextCommand extends Command {

    public ForcenextCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedGroup("Music operator");
        this.setHelpContent("Force le passage à la prochaine musique");
        this.setNeedVoice(true);
        try {
            this.addAlias("fn");
            this.addAlias("forceskip");
            this.addAlias("fs");
        } catch (AliasAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                MusicManager.getGuildAudioPlayer(event.getGuild()).next();
                event.getChannel().sendMessage("Passage à la prochaine musique");
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
