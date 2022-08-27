package jp.cron.sample.bot.command;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.SlashCommand;
import jp.cron.sample.profile.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;

public abstract class Command extends SlashCommand {
    Profile profile;
    public Command(Profile profile, String name, String help, String... aliases) {
        this.profile = profile;
        this.name = name;
        this.help = help;
        this.aliases = aliases;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply().complete();
        try {
            process(event, new CommandReplier(event.getHook(), null));
        } catch (Exception ex) {
        }
    }

    protected abstract void process(SlashCommandEvent event, CommandReplier replier);
}
