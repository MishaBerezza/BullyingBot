package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.ResponseMessages;

public class UnknownFormatException extends BullingBotException{

    public UnknownFormatException(String message) {
        super(ResponseMessages.UNKNOWN_MESSAGE_FORMAT);
    }
}
