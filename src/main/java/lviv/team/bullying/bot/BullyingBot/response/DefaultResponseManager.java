package lviv.team.bullying.bot.BullyingBot.response;

import lviv.team.bullying.bot.BullyingBot.core.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.core.commands.DefaultCommands;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DefaultResponseManager {

    public String getResponseForStart() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(DefaultCommands.START.getMessage());
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("COMMANDS");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        Arrays.stream(BullingCommands.values()).forEach(bullingCommand -> {
            stringBuilder.append(bullingCommand.getCommand());
            stringBuilder.append("\t");
            stringBuilder.append(bullingCommand.getMessage());
            stringBuilder.append("\n");
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }

    public String getResponseForHelp() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("COMMANDS");
        addToNextLine(stringBuilder);
        addToNextLine(stringBuilder);


        stringBuilder.append("DEFAULT COMMANDS");
        addToNextLine(stringBuilder);
        addToNextLine(stringBuilder);


        Arrays.stream(DefaultCommands.values()).forEach(bullingCommand -> {
            stringBuilder.append(bullingCommand.getCommand());
            addToNextLine(stringBuilder);
            addToNextLine(stringBuilder);

        });

        stringBuilder.append("BULLYING COMMANDS");
        addToNextLine(stringBuilder);
        addToNextLine(stringBuilder);


        Arrays.stream(BullingCommands.values()).forEach(bullingCommand -> {
            stringBuilder.append(bullingCommand.getCommand());
            stringBuilder.append("\t");
            stringBuilder.append(bullingCommand.getMessage());
            addToNextLine(stringBuilder);
            addToNextLine(stringBuilder);
        });

        return stringBuilder.toString();
    }


    private void addToNextLine(StringBuilder stringBuilder) {
        stringBuilder.append("\n");
    }
}
