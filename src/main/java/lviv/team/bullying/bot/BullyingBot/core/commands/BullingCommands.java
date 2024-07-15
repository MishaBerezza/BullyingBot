package lviv.team.bullying.bot.BullyingBot.core.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum BullingCommands implements Command {
    SAVE("/save", "This command create record for person. \t You need to write \"/save @userMention\""),
    GET_RECORDS("/getAll", "This command get all records for each person in current chat"),
    GET_RECORDS_BY_NAME("/get", "This command get records for each person that was mentioned in message. \t You need to write \"/get @userMention\""),;

    private final String command;
    private final String message;

    public static boolean isCommandExists(String command) {
        return Arrays.stream(BullingCommands.values())
                .anyMatch(bullingCommands ->
                        bullingCommands.getCommand().equals(command)
                );
    }
}
