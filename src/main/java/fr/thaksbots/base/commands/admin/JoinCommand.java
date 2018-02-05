package fr.thaksbots.base.commands.admin;


import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 16/11/2017.
 */
public class JoinCommand extends Command {

    public JoinCommand(String name, String prefix, boolean enable) {
        super(name, prefix, enable);
        this.addAuthorizedGroup("Music operator");
        this.setHelpContent("Fait venir le bot dans votre salon");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().join();
                event.getChannel().sendMessage(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().getName()+" rejoint avec succès");
            }
        } catch (NullPointerException e){
            event.getChannel().sendMessage("Vous devez être dans un salon vocal pour effectuer cette commande");
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }

    }
}
