package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.ResponseMessages;

public class UnknownCommandException  extends BullingBotException{

    public UnknownCommandException() {
        super(ResponseMessages.UNKNOWN_COMMAND);
    }
}
