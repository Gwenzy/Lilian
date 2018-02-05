package fr.thaksbots.base.commands.everyone;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.thaksbots.base.Base;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

/**
 * Created by Shû~ on 17/11/2017.
 */
public class InfosCommand extends Command {

    public InfosCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedGroup("@everyone");
        this.setHelpContent("Donne des informations sur la musique en cours");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                try {
                    AudioTrackInfo infos = MusicManager.getGuildAudioPlayer(event.getGuild()).getPlayer().getPlayingTrack().getInfo();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.withColor(Color.CYAN);
                    eb.withTitle("Informations");
                    eb.withDesc("------------------------------------------------------------------");
                    eb.appendField("Titre", infos.title, false);
                    eb.appendField("Chaîne YouTube", infos.author, false);
                    eb.appendField("Durée", (infos.length/1000)+"s", false);
                    eb.appendField("URL", infos.uri, false);
                    long startingTimestamp = MusicManager.getGuildAudioPlayer(event.getGuild()).getStartingTimestamp();
                    eb.appendField("Progression", ""+((System.currentTimeMillis()-startingTimestamp)/1000)+"/"+(infos.length/1000)+"s - "+Math.round(((double)System.currentTimeMillis()-(double)startingTimestamp)/(double)infos.length*100)+"% - "+((infos.length-System.currentTimeMillis()+startingTimestamp)/1000)+"s left" , false);
                    eb.appendField("Utilisateur source", Base.logged.getUserByID(MusicManager.getGuildAudioPlayer(event.getGuild()).getCurrentAuthor()).mention()+"", false);
                    event.getChannel().sendMessage(eb.build());
                    event.getMessage().delete();



                }catch(Exception e){
                    event.getChannel().sendMessage("Pas de musique en cours");
                    e.printStackTrace();
                }
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
