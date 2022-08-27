package jp.cron.sample.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jp.cron.sample.profile.Profile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Autowired
    Profile profile;


    @NotNull
    @Override
    protected String getDatabaseName() {
        return profile.mongoDbName;
    }

    @NotNull
    @Override
    public MongoClient mongoClient() {

        ConnectionString connectionString = new ConnectionString(profile.mongoDbUri);
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        SSLContext finalSc = sc;
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToSslSettings(builder -> {
                    if (profile.isSsl) {
                        builder.enabled(true);
                        builder.invalidHostNameAllowed(true);
                        builder.context(finalSc);
                    }
                })
                .build();
        return MongoClients.create(mongoClientSettings);
    }
}
