package fr.thaksbots.base.commands.admin;


import fr.thaksbots.base.Base;
import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.MissingArgsException;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by Shû~ on 18/11/2017.
 */
public class StreamCommand extends Command {


    public StreamCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedClient(Credentials.ID_DEVELOPPER);
        this.setHelpContent("Sets streaming mode: reserved to developer");
        this.addFacultativeArg("Twitch link");
        this.addFacultativeArg("Description");
        this.setAcceptPrivate(true);
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                String[] args = getArgs(event.getMessage().getFormattedContent());
                if(args.length==0){
                    if(!Base.logged.getOurUser().getPresence().getStreamingUrl().isPresent()) {
                        Base.logged.streaming("My developer is improving me", "https://www.twitch.tv/thaksin_");
                        Base.logged.changePlayingText("My developer is improving me");
                        event.getChannel().sendMessage("Streaming mode enabled");
                    }
                    else {
                        Base.logged.online(Base.cm.getCommand("help").getFullCommand());
                        event.getChannel().sendMessage("Streaming mode disabled");
                    }
                }
                else if(args.length>=2){
                    Base.logged.streaming(getArgMessage(event.getMessage().getFormattedContent(), 1), args[0]);
                    Base.logged.changePlayingText(getArgMessage(event.getMessage().getFormattedContent(), 1));
                    event.getChannel().sendMessage("Custom streaming mode enabled");
                }
                else
                    throw new MissingArgsException(event.getChannel());
            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }


}
