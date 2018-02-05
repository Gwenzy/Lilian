package fr.thaksbots.base.notifs;

import com.google.common.collect.Lists;
import fr.thaksbots.base.Base;
import fr.thaksbots.base.Credentials;
import org.json.JSONObject;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Shû~ on 04/02/2018.
 */
public class ThreadNotifs extends TimerTask {
    List<String> scopes;

    private String lastVideo;
    private boolean isStreaming;

    private String ChannelId;
    private List<Long> channelNotif;
    private String url;
    private String twitchChannelID;

    public ThreadNotifs() {
        lastVideo = "";
        ChannelId = "UCZcrXZuopxn9NSPBFbIDF5g";
        twitchChannelID = "189039716";
        isStreaming = false;
        scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        url="https://www.googleapis.com/youtube/v3/search?key="+ Credentials.YOUTUBE_API_KEY+"&channelId="+ChannelId+"&part=snippet,id&order=date&maxResults=1";
        channelNotif = new ArrayList<>();
        channelNotif.add(406783026635210752L);
        //channelNotif.add(409742576418291732L);
    }

    public boolean isStreaming() throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.twitch.tv/kraken/streams/"+twitchChannelID).openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
        con.setRequestProperty("Authorization", "OAuth "+ Credentials.TWITCH_OAUTH);
        con.setRequestProperty("Client-ID", Credentials.TWITCH_ID);

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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

        return !json.isNull("stream");

    }

    public JSONObject getTwitchJSON() throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.twitch.tv/kraken/streams/"+twitchChannelID).openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
        con.setRequestProperty("Authorization", "OAuth "+ Credentials.TWITCH_OAUTH);
        con.setRequestProperty("Client-ID", Credentials.TWITCH_ID);

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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

        return json.getJSONObject("stream");
    }

    public boolean addChannelId(long id){
        if(channelNotif.contains(id))
            return false;
        channelNotif.add(id);
        return true;
    }

    private String getLastVideoId() throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

        JSONObject json = new JSONObject(response.toString());

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");

    }

    private String getThumbnail() throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

        JSONObject json = new JSONObject(response.toString());

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
    }
    private String getDescription() throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

        JSONObject json = new JSONObject(response.toString());

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description");
    }


    private String getTitle() throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

        JSONObject json = new JSONObject(response.toString());

        return json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
    }
    @Override
    public void run() {
        try {
            if(lastVideo.equals(""))
                lastVideo = getLastVideoId();

            else if(!lastVideo.equals(getLastVideoId())){
                EmbedBuilder eb = new EmbedBuilder()
                        .withTitle("Nouvelle vidéo sur la chaîne !")
                        .withThumbnail(getThumbnail())
                        .withUrl("https://www.youtube.com/watch?v="+getLastVideoId())
                        .appendField(getTitle(), getDescription(), false);

                for(long l : channelNotif){
                    RequestBuffer.request(()-> Base.logged.getChannelByID(l).sendMessage(eb.build()));
                }

                lastVideo = getLastVideoId();

            }


            boolean streaming = isStreaming();
            if(!isStreaming)
                if(streaming){
                    //A envoyer un message
                    JSONObject channel = getTwitchJSON();

                    EmbedBuilder eb = new EmbedBuilder()
                            .withTitle("Je suis en stream !")
                            .withThumbnail(channel.getJSONObject("preview").getString("large"))
                            .withUrl(channel.getJSONObject("channel").getString("url"))
                            .appendField(channel.getJSONObject("channel").getString("status"), channel.getJSONObject("channel").getString("description"), false);


                    for(long l : channelNotif){
                        RequestBuffer.request(()-> Base.logged.getChannelByID(l).sendMessage(eb.build()));
                    }
                }



            isStreaming = streaming;



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
