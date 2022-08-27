package jp.cron.sample.bot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.vdurmont.emoji.EmojiParser;
import jp.cron.sample.bot.command.Command;
import jp.cron.sample.bot.command.CommandManager;
import jp.cron.sample.profile.Profile;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.net.UnknownHostException;
import java.util.Arrays;

@Service
public class Bot {

    @Autowired
    Profile profile;
    @Autowired
    CommandManager commandManager;
    @Autowired
    Listener listener;

    public Bot() {
    }

    @PostConstruct
    public void start() throws LoginException, UnknownHostException {
        CommandClient client = generateCommandClientBuilder()
                .build();
        JDA jda = jdaBuilder()
                .addEventListeners(client)
                .addEventListeners(listener)
                .build();
    }

    public CommandClientBuilder generateCommandClientBuilder() {
        return new CommandClientBuilder()
                .setPrefix(profile.prefix)
                .setOwnerId(profile.ownerId)
                .setCoOwnerIds(profile.coOwnersId)
                .setEmojis(EmojiParser.parseToUnicode("o"), null, EmojiParser.parseToUnicode("x"))
                .setServerInvite(profile.serverInvite)
                .setStatus(OnlineStatus.ONLINE)
                .addCommands(commandManager.getCommands().toArray(new Command[0]))
                .addSlashCommands(commandManager.getCommands().toArray(new Command[0]))
                .useHelpBuilder(false);
    }

    public JDABuilder jdaBuilder() throws UnknownHostException {
        return JDABuilder.create(profile.token, Arrays.asList(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.DIRECT_MESSAGES))
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.EMOTE, CacheFlag.ONLINE_STATUS)
                .setBulkDeleteSplittingEnabled(true)
                .setAutoReconnect(true);
    }
}
