package fr.thaksbots.base.commands.everyone;


import fr.thaksbots.base.Base;
import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.commands.admin.BlockCommand;
import fr.thaksbots.base.exceptions.exceptions.AliasAlreadyExistsException;
import fr.thaksbots.base.exceptions.exceptions.CommandMissingPermissionException;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by gwend on 15/11/2017.
 */
public class DevCommand extends Command {
    public DevCommand(String name, String prefix, boolean enable) {
        super(name, prefix, enable);
        this.setHelpContent("Vous voulez poser une question au développeur ? Cette commande est pour vous :p");
        this.addFacultativeArg("message");
        this.addAuthorizedGroup("@everyone");
        try {
            this.addAlias("contact");
            this.addAlias("developer");
        } catch (AliasAlreadyExistsException e) {
            e.printStackTrace();
        }
        if(name.equals("")) {
            this.setAcceptChannel(false);
            this.setAcceptPrivate(true);
        }

    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                if(!event.getAuthor().getStringID().equals(Credentials.ID_DEVELOPPER))
                if(BlockCommand.isBlocked(event.getAuthor()))
                    throw new CommandMissingPermissionException(this.getName(), event.getChannel());

                if(getArgs(event.getMessage().getFormattedContent()).length==0) {
                    if (Base.getCommonServers(event.getAuthor()).equals(""))
                        event.getChannel().sendMessage("Vous devez avoir au moins un serveur en commun avec le développeur");
                    else {
                        Base.logged.getUserByID(Long.parseLong(Credentials.ID_DEVELOPPER)).getOrCreatePMChannel().sendMessage(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " sur le(s) serveur(s) " + Base.getCommonServers(event.getAuthor()) + " voudrait vous contacter, vous pouvez le retrouver ici : \n" +
                                Base.inviteCodes(event.getAuthor()));
                        event.getChannel().sendMessage("Message envoyé !");
                    }
                }
                else{
                    if (Base.getCommonServers(event.getAuthor()).equals(""))
                        event.getChannel().sendMessage("Vous devez avoir au moins un serveur en commun avec le développeur");
                    else {
                        Base.logged.getUserByID(Long.parseLong(Credentials.ID_DEVELOPPER)).getOrCreatePMChannel().sendMessage(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " sur le(s) serveur(s) " + Base.getCommonServers(event.getAuthor()) + " voudrait vous contacter, vous pouvez le retrouver ici : \n" +
                                        Base.inviteCodes(event.getAuthor())
                        + "\n\n**"+(getName().equals("")?event.getMessage().getFormattedContent():getArgMessage(event.getMessage().getFormattedContent(), 0))+"**"

                        );
                        event.getChannel().sendMessage("Message envoyé !");
                    }
                }

            }
        } catch (ThaksbotException e) {
            e.printStackTrace();
        }
    }
}
