package fr.thaksbots.base.exceptions.exceptions;

/**
 * Created by gwend on 14/11/2017.
 */
public class AliasAlreadyExistsException extends ThaksbotException {

    public AliasAlreadyExistsException(String name, String alias){
        super("Alias "+alias+" déjà existant pour la commande "+name);
    }
}
