package fr.thaksbots.base.exceptions;

import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;

/**
 * Created by gwend on 14/11/2017.
 */
public class CommandConstructionException extends ThaksbotException {

    public CommandConstructionException(String name){
        super("Error while attempting to create "+name+" command");
    }
}
