package jp.cron.sample.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EmbedUtil {
    public static MessageEmbed generateErrorEmbed(String message) {
        return new EmbedBuilder()
                .setTitle(":x: エラーが発生しました。")
                .setDescription(message)
                .setColor(Color.RED)
                .addField("お困りですか？", "[公式サポートサーバー](https://discord.gg/YbB4H4tRea)までご連絡ください。", true)
                .build();
    }

    public static EmbedBuilder generateSuccessEmbed(String title, String message) {
        return new EmbedBuilder()
                .setTitle(":o: "+title)
                .setDescription(message)
                .setColor(Color.GREEN);
    }

    public static MessageEmbed generateErrorInformationEmbed(Exception ex) {
        return new EmbedBuilder()
                .setTitle(ex.getClass().getCanonicalName())
                .setDescription(
                        ex.getMessage()
                )
                .addField("Stacktrace",
                        Arrays.stream(ex.getStackTrace())
                                .map(se -> se.toString()).collect(Collectors.joining("\n"))
                                .substring(0, 1023), false)
                .setColor(Color.RED)
                .build();
    }

    public static MessageEmbed generateGuildInformationEmbed(Guild guild) {
        String invites = "";
        try {
            invites =  guild.retrieveInvites().complete().stream()
//                    .filter(i -> !i.isTemporary())
//                    .map(i -> i.getUrl()+" by "+FormatUtil.formatUser(i.getInviter(), true))
                    .map(Invite::getUrl)
                    .collect(Collectors.joining("\n"));
        } catch (Exception ignored) {}

        return new EmbedBuilder()
                .setAuthor(guild.getName(), null, guild.getIconUrl())
                .setDescription(guild.getId())
                .addField("Owner", FormatUtil.formatUser(guild.getOwner().getUser(), true), true)
                .addField("Members", String.valueOf(guild.getMemberCount()), true)
                .addField("Invites", invites, false)
                .build();
    }
}