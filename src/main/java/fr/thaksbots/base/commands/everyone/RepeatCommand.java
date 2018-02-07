package fr.thaksbots.base.commands.everyone;

import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.commands.admin.EnableCommand;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Shû~ on 19/11/2017.
 */
public class RepeatCommand extends Command {

    public RepeatCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedGroup("Music operator");
        this.setHelpContent("Active ou désactive le mode repeat");
        this.setNeedVoice(true);
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                if(!EnableCommand.MUSIC){
                    RequestBuffer.request(()->event.getChannel().sendMessage("La fonctionnalité Musique a été désactivée par un administrateur"));
                    return;
                }
                MusicManager.getGuildAudioPlayer(event.getGuild()).setRepeatMode(!MusicManager.getGuildAudioPlayer(event.getGuild()).isRepeatMode());

                event.getChannel().sendMessage("Mode repeat "+(MusicManager.getGuildAudioPlayer(event.getGuild()).isRepeatMode()?"activé":"désactivé"));
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
