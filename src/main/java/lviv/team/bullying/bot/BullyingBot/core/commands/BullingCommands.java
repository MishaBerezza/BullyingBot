package lviv.team.bullying.bot.BullyingBot.core.commands;

import lombok.Getter;

import java.util.Arrays;


@Getter
public enum BullingCommands {
    SAVE("/save"),
    GET_RECORDS("/getAll"),
    GET_RECORDS_BY_NAME("/get");


    private final String command;

    BullingCommands(String command) {
        this.command = command;
    }

    public static boolean isCommandExists(String command) {
        return Arrays.stream(BullingCommands.values())
                .anyMatch(bullingCommands ->
                        bullingCommands.getCommand().equals(command)
                );
    }
}
