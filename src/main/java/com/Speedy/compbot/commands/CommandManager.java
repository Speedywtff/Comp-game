package com.Speedy.compbot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    private ArrayList<Member> members;
    private ArrayList<Member> challenger;
    public CommandManager(){
        this.members = new ArrayList<>();
        this.challenger = new ArrayList<>();
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equalsIgnoreCase("play")){
            event.reply(event.getOption("user").getAsMember().getAsMention() + ", you are challenged to a game. Use the accept command to accept.").queue();
            members.add(event.getOption("user").getAsMember());
            challenger.add(event.getMember());
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
        OptionData user = new OptionData(OptionType.USER, "Player 2", "user to start a game with", true);
        OptionData user2 = new OptionData(OptionType.USER, "Player 3", "user to start a game with");
        OptionData user3 = new OptionData(OptionType.USER, "Player 4", "user to start a game with");
        commands.add(Commands.slash("play", "Start a game").addOptions(user, user2, user3));
        commands.add(Commands.slash("accept", "accept a challenge"));

        event.getGuild().updateCommands().addCommands(commands).queue();
    }

}
