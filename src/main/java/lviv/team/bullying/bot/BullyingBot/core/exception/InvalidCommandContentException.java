package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.ResponseMessages;

public class InvalidCommandContentException  extends BullingBotException{

    public InvalidCommandContentException(String reason) {
        super(ResponseMessages.INVALID_COMMAND_CONTENT.concat("\nReason: " + reason));

    }
}
