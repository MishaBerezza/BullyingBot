package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponses;

public class InvalidCommandContent extends RuntimeException{

    public InvalidCommandContent(String reason) {
        super(DefaultResponses.INVALID_COMMAND_CONTENT.getText().concat("\nReason: " + reason));

    }
}
