package lviv.team.bullying.bot.BullyingBot.core.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultCommands implements Command{
    HELP("/help", ""),
    START("/start", "Hi guys, I'm BullingBot and I can save records and return them whenever you want");

    private final String command;
    private final String message;
}
