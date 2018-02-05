package fr.thaksbots.base.listeners;

import fr.thaksbots.base.Base;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;

/**
 * Created by gwend on 15/11/2017.
 */
public class AllListener extends ThaksbotListener implements IListener<Event> {
    public AllListener(boolean enabled){
        this.setEnabled(enabled);
    }

    @Override
    public void handle(Event event) {
        if(this.getEnabled()){
            if(!Base.logged.getOurUser().getPresence().getPlayingText().isPresent())
                Base.logged.changePlayingText(Base.cm.getCommand("help").getFullCommand());


        }
    }
}
