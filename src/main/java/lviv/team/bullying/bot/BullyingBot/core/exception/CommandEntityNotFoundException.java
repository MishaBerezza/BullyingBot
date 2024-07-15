package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponseMessages;

public class CommandEntityNotFoundException extends RuntimeException {

    public CommandEntityNotFoundException() {
        super(DefaultResponseMessages.COMMAND_NOT_FOUND.getText());
    }
}
