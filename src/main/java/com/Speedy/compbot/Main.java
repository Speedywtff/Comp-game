package com.Speedy.compbot;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;


public class Main {

    private final Dotenv config;
    private final ShardManager shardmanager;
    public Main() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token); //Builds the shard, which is what runs multiple instances
        builder.setStatus(OnlineStatus.ONLINE); //Appears Online
        builder.setActivity(Activity.playing("IntelliJ")); //Funsies
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGES); //On discords app dev portal
        shardmanager = builder.build(); //Builds it, sets all properties. Throws login exception if token incorrect
        //shardmanager.addEventListener(new eventlistener(), new CommandManager()); for listening to future events and commands
    }
    public ShardManager getShardmanager(){
        return shardmanager;
    }
    public Dotenv getConfig() { return config; }
    public static void main(String[] args) {
        try{
            Main bot = new Main();
        } catch (LoginException e) {
            System.out.println("Incorrect Token");
        }
    }
}