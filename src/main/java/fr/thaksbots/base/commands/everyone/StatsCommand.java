package fr.thaksbots.base.commands.everyone;

import fr.thaksbots.base.Credentials;
import fr.thaksbots.base.commands.Command;
import fr.thaksbots.base.exceptions.exceptions.ThaksbotException;
import org.json.JSONArray;
import org.json.JSONObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Shû~ on 05/02/2018.
 */
public class StatsCommand extends Command{

    public StatsCommand(String name, String prefix, boolean enabled) {
        super(name, prefix, enabled);
        this.addAuthorizedGroup("@everyone");
        this.addNeededArg("Plateforme");
        this.addNeededArg("Pseudonyme");

        this.setHelpContent("Donne des statistiques sur le joueur indiqué. (Plateforme peut être pc, psn ou xbl)");
    }

    @Override
    public void handle(MessageReceivedEvent event){
        try {
            if(canBeExecuted(event)){
                String platform = getArgs(event.getMessage().getFormattedContent())[0].toLowerCase();
                String nickname = getArgs(event.getMessage().getFormattedContent())[1];
                HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.fortnitetracker.com/v1/profile/"+platform+"/"+nickname).openConnection();

                con.setRequestMethod("GET");

                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("TRN-Api-Key", Credentials.FORTINE_TRACKER_API_KEY);
                con.setDoOutput(true);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                if(json.has("error")){
                    RequestBuffer.request(()->event.getChannel().sendMessage("Une erreur est survenue : "+json.getString("error")));
                    return;
                }


                String platform1 = json.getString("platformNameLong");

                String name = json.getString("epicUserHandle");

                JSONArray stats = json.getJSONArray("lifeTimeStats");


                String played = stats.getJSONObject(7).getString("value");
                String wins = stats.getJSONObject(8).getString("value");
                String winrate = stats.getJSONObject(9).getString("value");
                String score = stats.getJSONObject(6).getString("value");
                String kills = stats.getJSONObject(10).getString("value");
                String ratio = stats.getJSONObject(11).getString("value");
                String killsPerMin = stats.getJSONObject(12).getString("value");
                String timePlayed = stats.getJSONObject(13).getString("value");
                String timeSurvived = stats.getJSONObject(14).getString("value");

                String top3 = stats.getJSONObject(0).getString("value");
                String top3s = stats.getJSONObject(1).getString("value");
                String top5s = stats.getJSONObject(2).getString("value");
                String top6s = stats.getJSONObject(3).getString("value");
                String top12s = stats.getJSONObject(4).getString("value");
                String top25s = stats.getJSONObject(5).getString("value");

                EmbedBuilder eb = new EmbedBuilder()
                        .withTitle("Statistiques de "+name+" ("+platform1+")")
                        .withUrl("https://fortnitetracker.com/profile/"+platform+"/"+name)
                        .appendField("Temps joué", timePlayed, true)
                        .appendField("Nombre de parties jouées", played, true)
                        .appendField("Temps de survie moyen", timeSurvived, true)
                        .appendField("Victoires", wins, true)
                        .appendField("Taux de victoires", winrate, true)
                        .appendField("Score total", score, true)
                        .appendField("Kills", kills, true)
                        .appendField("Kills/min", killsPerMin, true)
                        .appendField("Ratio Kills/Deaths", ratio, true)
                        .appendField("Tops 3", top3, true)
                        .appendField("Tops 3s", top3s, true)
                        .appendField("Tops 5s", top5s, true)
                        .appendField("Tops 6s", top6s, true)
                        .appendField("Tops 12s", top12s, true)
                        .appendField("Tops 25s", top25s, true);


                RequestBuffer.request(()->event.getChannel().sendMessage(eb.build()));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
