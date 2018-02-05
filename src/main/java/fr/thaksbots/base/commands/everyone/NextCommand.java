package fr.thaksbots.base.commands.everyone;


import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.AliasAlreadyExistsException;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 19/11/2017.
 */
public class NextCommand extends Command {

    public NextCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.setHelpContent("Demande la musique suivante, la musique sera passée si au moins 50% des utilisateurs ont demandé à passer");
        this.addAuthorizedGroup("@everyone");
        this.setNeedVoice(true);
        try {
            this.addAlias("skip");
        } catch (AliasAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                if(MusicManager.getGuildAudioPlayer(event.getGuild()).askNext(event.getAuthor().getLongID(), event.getGuild().getConnectedVoiceChannel())){
                    event.getChannel().sendMessage("OK ! Musique passée");
                }
                else
                    event.getChannel().sendMessage("Requête reçue, "+ MusicManager.getGuildAudioPlayer(event.getGuild()).getWantToSkip(event.getGuild().getConnectedVoiceChannel())+" personnes veulent passer");
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
