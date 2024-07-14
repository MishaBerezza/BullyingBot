package lviv.team.bullying.bot.BullyingBot.exception;

import lviv.team.bullying.bot.BullyingBot.response.DefaultResponses;

public class CommandEntityNotFoundException extends RuntimeException {

    public CommandEntityNotFoundException() {
        super(DefaultResponses.COMMAND_NOT_FOUND.getText());
    }
}
