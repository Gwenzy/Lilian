package fr.thaksbots.base;

import fr.thaksbots.base.commands.*;
import fr.thaksbots.base.commands.everyone.*;
import fr.thaksbots.base.commands.admin.*;
import fr.thaksbots.base.listeners.AllListener;
import fr.thaksbots.base.listeners.ReadyListener;
import fr.thaksbots.base.music.MusicManager;
import fr.thaksbots.base.notifs.ThreadNotifs;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 * Created by Shû~ on 04/02/2018.
 */
public class Base {
    public static IDiscordClient bot, test, logged;
    public static CommandManager cm;
    public static boolean BOT_LOG = false;
    public static final String GLOBAL_PREFIX = "\"";
    public static ThreadNotifs tn;
    public static void main(String[] args){

        cm = new CommandManager();

        cm.registerCommand(new DevCommand("dev", GLOBAL_PREFIX, true));
        cm.registerCommand(new DevCommand("", "", true));


        cm.registerCommand(new BlockCommand("block", GLOBAL_PREFIX, true));
        cm.registerCommand(new DisableCommand("disable", GLOBAL_PREFIX, true));
        cm.registerCommand(new EnableCommand("enable", GLOBAL_PREFIX, true));
        cm.registerCommand(new StreamCommand("stream", GLOBAL_PREFIX, true));
        cm.registerCommand(new LogoutCommand("logout", GLOBAL_PREFIX, true));


        cm.registerCommand(new JoinCommand("join", GLOBAL_PREFIX, true));
        cm.registerCommand(new ForcenextCommand("forcenext", GLOBAL_PREFIX, true));
        cm.registerCommand(new SneakyCommand("sneaky", GLOBAL_PREFIX, true));
        cm.registerCommand(new VolumeCommand("volume", GLOBAL_PREFIX, true));

        cm.registerCommand(new FastplayCommand("fastplay", GLOBAL_PREFIX, true));
        cm.registerCommand(new HelpCommand("help", GLOBAL_PREFIX, true));
        cm.registerCommand(new InfosCommand("infos", GLOBAL_PREFIX, true));
        cm.registerCommand(new NextCommand("next", GLOBAL_PREFIX, true));
        cm.registerCommand(new PlayCommand("play", GLOBAL_PREFIX, true));
        cm.registerCommand(new QueueCommand("queue", GLOBAL_PREFIX, true));
        cm.registerCommand(new RepeatCommand("repeat", GLOBAL_PREFIX, true));
        cm.registerCommand(new SearchCommand("search", GLOBAL_PREFIX, true));

        cm.registerCommand(new StatsCommand("stats", GLOBAL_PREFIX, true));


        bot = createClient(Credentials.TOKEN_BOT, BOT_LOG);
        test = createClient(Credentials.TOKEN_TEST, !BOT_LOG);
        logged = BOT_LOG?bot:test;


    }


    public static IDiscordClient createClient(String token, boolean login){
        ClientBuilder cb = new ClientBuilder();
        cb.withToken(token);
        cb = registerCommands(cb);

        cb.registerListener(new ReadyListener(true));
        cb.registerListener(new AllListener(true));
        cb.registerListener(new MusicManager());

        if(login)
            return cb.login();
        else
            return cb.build();

    }

    public static ClientBuilder registerCommands(ClientBuilder eb){
        for(Command cmd : cm.getCommands()){
            eb.registerListener(cmd);

        }

        return eb;
    }

    public static String getCommonServers(IUser author) {
        String common = "";

        for(IGuild guild : Base.logged.getGuilds()){
            if(guild.getUsers().contains(author))
                common+=guild.getName()+", ";
        }

        if(!common.equals(""))
            common = common.substring(0, common.length()-20);


        return common;
    }


    public static String inviteCodes(IUser author){
        String links = "";

        for(IGuild guild : Base.logged.getGuilds()){
            if(guild.getUsers().contains(author))
            {
                links+="https://discord.gg/"+guild.getChannels().get(0).createInvite(0, 1, false, false).getCode()+"/\n";
            }
        }
        return links;
    }
}
