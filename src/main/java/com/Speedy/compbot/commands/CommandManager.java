package com.Speedy.compbot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    private ArrayList<Member> members;
    private ArrayList<Member> challenger;
    public CommandManager(){
        this.challenger = new ArrayList<>();
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equalsIgnoreCase("play")){
            List<OptionMapping> OptionMembers = event.getOptions();
            Member[] players = new Member[OptionMembers.size() + 1];
            players[0] = event.getMember();
            int i = 1;
            for(OptionMapping p: OptionMembers){
                Member u = p.getAsMember();
                    players[i] = u;
                    i++;
            }

            for(i = 0; i < players.length; i++){ //remove this later
                System.out.println(players[i]);
            }
            //check if challenged players are duplicated, reject if so.

            boolean flag = checkduplicates(players);

            if(flag){
                for(i = 0; i < players.length; i++){ //This can probably be more efficient by using challenger from the start.
                    challenger.add(players[i]);
                }
                event.reply("Invite sent.").queue();  //Accepted
            } else {
                event.reply("Invalid Arguments! Make sure members are not pinged more than once and you have not pinged a bot.").queue(); //Rejected due to duplication
            }


        } else if(command.equalsIgnoreCase("accept")){
            Member user = event.getMember();
            if(members.contains(user)){
                int i = members.indexOf(user);
                event.reply("Game has started! The players are: " + user.getAsMention() + " and " + members.get(i).getAsMention()).queue();
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) { //register commands to guild when loaded
        List<CommandData> commands = new ArrayList<>();
        OptionData user = new OptionData(OptionType.USER, "player2", "user to start a game with", true);
        OptionData user2 = new OptionData(OptionType.USER, "player3", "user to start a game with");
        OptionData user3 = new OptionData(OptionType.USER, "player4", "user to start a game with");
        commands.add(Commands.slash("play", "Start a game").addOptions(user, user2, user3));
        commands.add(Commands.slash("accept", "accept a challenge"));

        event.getGuild().updateCommands().addCommands(commands).queue();
    }

    private boolean checkduplicates(Member[] players){ //efficiency pro max
        HashSet<Member> set = new HashSet<>();

        for(Member i: players){
            if(i.getUser().isBot()){
                return false;
            }
        }

        for (Member member : players) {
            if (!set.add(member)) {
                return false; // Duplicate found
            }
        }

        return true;
    }


}
