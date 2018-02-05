package fr.thaksbots.base.commands.admin;


import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.AliasAlreadyExistsException;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 16/11/2017.
 */
public class VolumeCommand extends Command {

    public VolumeCommand(String name, String prefix, boolean enable) {
        super(name, prefix, enable);
        this.addAuthorizedGroup("Music operator");
        this.setHelpContent("Change le volume");
        this.addNeededArg("volume");
        this.setNeedVoice(true);
        try {
            this.addAlias("vl");
        } catch (AliasAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                int volume = Integer.parseInt(getArgs(event.getMessage().getFormattedContent())[0]);
                int initial = MusicManager.getGuildAudioPlayer(event.getGuild()).getPlayer().getVolume();
                MusicManager.getGuildAudioPlayer(event.getGuild()).getPlayer().setVolume(volume);
                event.getChannel().sendMessage("Volume passé de "+initial+" à "+volume);
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }

    }
}
