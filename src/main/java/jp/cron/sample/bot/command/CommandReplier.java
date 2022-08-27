package jp.cron.sample.bot.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class CommandReplier {
    private InteractionHook hook;
    private Message msg;

    public CommandReplier(InteractionHook hook, Message msg) {
        this.hook = hook;
        this.msg = msg;
    }

    public void reply(String message) {
        if (hook!=null) {
            hook.sendMessage(message).queue();
        }
        if (msg !=null) {
            msg.reply(message).queue();
        }
    }

    public void sendMessageEmbeds(MessageEmbed embed) {
        if (hook!=null) {
            hook.sendMessageEmbeds(embed).queue();
        }
        if (msg !=null) {
            msg.replyEmbeds(embed).queue();
        }
    }

    public Guild getGuild() {
        if (hook!=null) {
            return hook.getInteraction().getGuild();
        }
        if (msg !=null) {
            return msg.getGuild();
        }
        return null;
    }
}
