package lviv.team.bullying.bot.BullyingBot.response;

import lombok.Getter;

@Getter
public class ResponseMessages {
    private static final  String HINT = " You can use command \"/help\" to see commands that I can process";

    public static final String UNKNOWN_COMMAND =  "Guys, I don't know this command." + HINT;
    public static final String COMMAND_NOT_FOUND = "Command not found." + HINT;
    public static final String INVALID_COMMAND_CONTENT = "Invalid command content. Delete or add some data.";
    public static final String UNKNOWN_MESSAGE_FORMAT = "I can't process such messages.";
    public static final String DEFAULT_EXCEPTION_RESPONSE = "Something went wrong while processing your message.";


    final String text;


    ResponseMessages(String text) {
        this.text = text;
    }

}
