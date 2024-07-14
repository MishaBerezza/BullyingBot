package lviv.team.bullying.bot.BullyingBot.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponses;

public class UnknownCommandException extends RuntimeException{

    public UnknownCommandException() {
        super(DefaultResponses.UNKNOWN_COMMAND.getText());
    }
}
