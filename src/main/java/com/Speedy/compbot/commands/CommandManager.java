package com.Speedy.compbot.commands;

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
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equalsIgnoreCase("play")){
            event.reply("Tagged: " +  event.getOption("user").getAsMember().getAsMention()).queue();
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) { //register commands to guild when loaded
        List<CommandData> commands = new ArrayList<>();
        OptionData user = new OptionData(OptionType.USER, "user", "user to start a game with", true);
        commands.add(Commands.slash("play", "Start a game").addOptions(user));

        event.getGuild().updateCommands().addCommands(commands).queue();
    }
}
