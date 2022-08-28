package jp.cron.sample.profile;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ProfileWriter {
    public Gson gson;

    @Autowired
    public ProfileWriter(Gson gson) {
        this.gson = gson;
    }

    public void writeToFile(String profileName, Profile profile) throws IOException {
        File configFolder = new File("profiles");
        if (!configFolder.exists() || !configFolder.isDirectory()) {
            configFolder.mkdir();
        }
        File configFile = new File("profiles/"+profileName+".json");
        FileWriter writer = new FileWriter(configFile);

        gson.toJson(profile, Profile.class, writer);

        writer.close();
    }
}
