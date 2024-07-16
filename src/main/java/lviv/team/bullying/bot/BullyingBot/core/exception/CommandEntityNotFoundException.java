package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.ResponseMessages;

public class CommandEntityNotFoundException extends BullingBotException {

    public CommandEntityNotFoundException() {
        super(ResponseMessages.COMMAND_NOT_FOUND);
    }
}
