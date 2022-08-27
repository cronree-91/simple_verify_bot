package jp.cron.sample.bot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandManager {
    List<Command> commands;

    @Autowired
    public CommandManager(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
