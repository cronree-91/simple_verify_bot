package jp.cron.sample.bot;

import com.vdurmont.emoji.EmojiParser;
import jp.cron.sample.profile.Profile;
import jp.cron.sample.profile.ProfileWriter;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Listener extends ListenerAdapter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Profile profile;
    @Autowired
    ProfileWriter profileWriter;

    Role role;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("Bot is ready");
        logger.info("BOT NAME: " + event.getJDA().getSelfUser().getName());

        TextChannel ch = event.getJDA().getTextChannelById(profile.channelId);
        if (ch==null)
            throw new RuntimeException("チャンネルIDを設定してください。");
        if (profile.messageId==null) {
            Message msg = ch.sendMessage(profile.messageText).complete();
            msg.addReaction("U+1F44D").complete();

            profile.messageId = msg.getId();
            try {
                profileWriter.writeToFile("default", profile);
            } catch (IOException e) {
                throw new RuntimeException("自動メッセージの送信に失敗しました。",e);
            }
        }

        role = ch.getGuild().getRoleById(profile.roleId);
        if (role==null)
            throw new RuntimeException("ロールの取得に失敗しました。正しいIDを設定していますか？");
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if (!event.getReactionEmote().isEmoji())
            return;
        if (event.getMessageId().equals(profile.messageId)) {
            try {
                event.getGuild().addRoleToMember(event.getUserId(), role).queue();
            } catch (InsufficientPermissionException ex) {
                throw new RuntimeException("ロール付与権限がないため失敗しました。", ex);
            }
        }
    }
}


