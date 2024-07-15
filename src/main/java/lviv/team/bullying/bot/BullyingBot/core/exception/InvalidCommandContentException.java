package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponseMessages;

public class InvalidCommandContentException  extends BullingBotException{

    public InvalidCommandContentException(String reason) {
        super(DefaultResponseMessages.INVALID_COMMAND_CONTENT.concat("\nReason: " + reason));

    }
}
