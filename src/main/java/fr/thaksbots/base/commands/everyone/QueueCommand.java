package fr.thaksbots.base.commands.everyone;


import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.commands.admin.EnableCommand;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Shû~ on 18/11/2017.
 */
public class QueueCommand extends Command {

    public QueueCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedGroup("@everyone");
        this.setHelpContent("Voir la file");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                if(!EnableCommand.MUSIC){
                    RequestBuffer.request(()->event.getChannel().sendMessage("La fonctionnalité Musique a été désactivée par un administrateur"));
                    return;
                }
                if(MusicManager.getGuildAudioPlayer(event.getGuild()).getQueueDesc().equals(""))
                    event.getChannel().sendMessage("La file est vide ;-;");
                else
                    event.getChannel().sendMessage(MusicManager.getGuildAudioPlayer(event.getGuild()).getQueueDesc());
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }


}
