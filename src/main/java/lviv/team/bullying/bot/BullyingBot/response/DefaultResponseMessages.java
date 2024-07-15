package lviv.team.bullying.bot.BullyingBot.response;

import lombok.Getter;

@Getter
public class DefaultResponseMessages {
    private static final  String HINT = " You can use command \"/help\" to see commands that I can process";

    public static final String UNKNOWN_COMMAND =  "Guys, I don't know this command." + HINT;
    public static final String COMMAND_NOT_FOUND = "Command not found." + HINT;
    public static final String INVALID_COMMAND_CONTENT = "Invalid command content. Delete or add some data." + HINT;
    public static final String UNKNOWN_MESSAGE_FORMAT = "I can't process such messages." + HINT;


    final String text;


    DefaultResponseMessages(String text) {
        this.text = text;
    }

}
