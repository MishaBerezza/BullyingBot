package lviv.team.bullying.bot.BullyingBot.core.exception;

public class DaysParsingException extends BullingBotException {
    private static final String EXCEPTION_MESSAGE = "Days parsing exception";


    public DaysParsingException() {
        super(EXCEPTION_MESSAGE);
    }

}
