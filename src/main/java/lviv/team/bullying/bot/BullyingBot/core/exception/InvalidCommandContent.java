package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponseMessages;

public class InvalidCommandContent extends RuntimeException{

    public InvalidCommandContent(String reason) {
        super(DefaultResponseMessages.INVALID_COMMAND_CONTENT.getText().concat("\nReason: " + reason));

    }
}
