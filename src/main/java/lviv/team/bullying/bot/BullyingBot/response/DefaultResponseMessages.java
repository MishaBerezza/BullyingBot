package lviv.team.bullying.bot.BullyingBot.response;

import lombok.Getter;

@Getter
public enum DefaultResponseMessages {
    UNKNOWN_COMMAND("Guys, I don't know this command"),
    COMMAND_NOT_FOUND("Command not found"),
    INVALID_COMMAND_CONTENT("Invalid command content");


    final String text;


    DefaultResponseMessages(String text) {
        this.text = text;
    }

}
