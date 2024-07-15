package lviv.team.bullying.bot.BullyingBot.core.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponseMessages;

public class UnknownExceptionFormat extends BullingBotException{

    public UnknownExceptionFormat(String message) {
        super(DefaultResponseMessages.UNKNOWN_MESSAGE_FORMAT);
    }
}
