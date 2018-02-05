package fr.thaksbots.base.commands.admin;


import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 17/11/2017.
 */
public class SneakyCommand extends Command {

    public SneakyCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedGroup("Sagiri operator");
        this.setHelpContent("Active/Désactive le mode furtif (Permet de mettre des musiques en effaçant les messages de confirmation)");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                MusicManager.getGuildAudioPlayer(event.getGuild()).setSneakyMode(!MusicManager.getGuildAudioPlayer(event.getGuild()).isSneakyMode());
                event.getChannel().sendMessage("Le mode furtif est "+(MusicManager.getGuildAudioPlayer(event.getGuild()).isSneakyMode()?"activé":"désactivé"));
                }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
