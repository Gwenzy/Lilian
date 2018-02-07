package fr.thaksbots.base.commands.admin;

import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Shû~ on 07/02/2018.
 */
public class EnableCommand extends Command{

    public static boolean MUSIC = true;
    public static boolean NOTIFS = true;
    public static boolean STATS = true;

    public EnableCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);

        this.setHelpContent("Activé une fonctionnalité du bot");
        this.addAuthorizedClient("310400283224178688");
        this.addAuthorizedClient(Credentials.ID_DEVELOPPER);
        this.addNeededArg("Musique/Notifs/Stats");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                String choix = getArgs(event.getMessage().getFormattedContent())[0];
                if(choix.equalsIgnoreCase("Musique")){
                    MUSIC = true;
                } else if(choix.equalsIgnoreCase("Notifs")){
                    NOTIFS = true;
                } else if(choix.equalsIgnoreCase("Stats")){
                    STATS = true;
                } else {
                    RequestBuffer.request(()->event.getChannel().sendMessage("Fonctionnalité "+choix+" inconnue"));
                    return;
                }

                RequestBuffer.request(()->event.getChannel().sendMessage("La fonctionnalité "+choix+" a été activée"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
