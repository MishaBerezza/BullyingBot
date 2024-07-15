package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponseMessages;

public class UnknownCommandException extends RuntimeException{

    public UnknownCommandException() {
        super(DefaultResponseMessages.UNKNOWN_COMMAND.getText());
    }
}
