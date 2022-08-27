package jp.cron.sample.bot.command.impl;

import com.jagrosh.jdautilities.command.CommandEvent;
import jp.cron.sample.bot.command.Command;
import jp.cron.sample.bot.command.CommandReplier;
import jp.cron.sample.profile.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class HelpCommand extends Command {
    @Autowired
    public HelpCommand(Profile profile) {
        super(profile, "help", "ヘルプを表示します。");
    }

    @Autowired
    Profile profile;

    @Override
    protected void process(SlashCommandEvent event, CommandReplier replier) {
    }
}
