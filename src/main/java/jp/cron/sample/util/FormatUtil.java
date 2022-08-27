package jp.cron.sample.util;

import com.google.common.net.InternetDomainName;
import net.dv8tion.jda.api.entities.User;

public class FormatUtil {
    public static String formatUser(User user, boolean id) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append("#").append(user.getDiscriminator());
        if (id)
            sb.append(" (").append(user.getId()).append(")");
        return sb.toString();
    }

    public static String getDomainMainName(String url) {
        InternetDomainName internetDomainName = InternetDomainName.from(url)
                .topPrivateDomain();
        String publicSuffix = internetDomainName.publicSuffix()
                .toString();
        String domainName = internetDomainName.toString();
        return domainName.substring(0, domainName.lastIndexOf("." + publicSuffix));
    }
}