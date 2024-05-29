package com.Speedy.compbot;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;


public class Main {

    private final ShardManager shardmanager;
    public Main() throws LoginException {
        String Token = "hidden";  //bot token, will change into an env later
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Token); //Builds the shard, which is what runs multiple instances
        builder.setStatus(OnlineStatus.ONLINE); //Appears Online
        builder.setActivity(Activity.playing("IntelliJ" )); //Funsies
        shardmanager = builder.build(); //Builds it, sets all properties. Throws login exception if token incorrect

    }
    public ShardManager getShardmanager(){
        return shardmanager;
    }

    //    public Dotenv getConfig() {
//        return config;
//    }
    public static void main(String[] args) {

        try{
            Main bot = new Main();
        } catch (LoginException e) {
            System.out.println(e);
        }
    }
}