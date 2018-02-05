package fr.thaksbots.base.commands.everyone;


import fr.thaksbots.base.Base;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import fr.thaksbots.base.music.MusicManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by gwend on 15/11/2017.
 */
public class PlayCommand extends Command {
    public PlayCommand(String name, String prefix, boolean enable) {
        super(name, prefix, enable);
        this.setHelpContent("Joue une musique : URL peut être une URL Youtube, un ID de vidéo et beaucoup d'autres choses");
        this.addNeededArg("URL");
        this.addAuthorizedGroup("@everyone");
        this.setNeedVoice(true);


    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)) {
                String url = getArgMessage(event.getMessage().getFormattedContent(), 0);
                if(url.startsWith("#")){
                    int ID = Integer.parseInt(url.split("#")[1]);
                    if(ID>0 && ID<6) {
                        url = MusicManager.getGuildAudioPlayer(event.getGuild()).getLastSearches().get(event.getAuthor().getLongID()).get(ID+1).split("!;;!")[1];

                    }
                    else
                        throw new NumberFormatException();

                }
                MusicManager.loadAndPlay(event.getChannel(), url, event.getAuthor().getLongID(), event.getGuild());
                if(MusicManager.getGuildAudioPlayer(event.getGuild()).isSneakyMode())
                    event.getMessage().delete();
            }
        }  catch (ThaksbotException e) {
            e.printStackTrace();
        } catch(NumberFormatException e){
            event.getChannel().sendMessage("Pas de vidéo trouvée avec cet ID, essayez "+ Base.cm.getCommand("search").getFullCommand()+" avant");
        }
    }
}
