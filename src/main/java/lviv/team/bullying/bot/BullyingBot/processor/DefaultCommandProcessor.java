package lviv.team.bullying.bot.BullyingBot.processor;

import lviv.team.bullying.bot.BullyingBot.core.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.core.commands.DefaultCommands;
import lviv.team.bullying.bot.BullyingBot.core.entity.MessageEntityType;
import lviv.team.bullying.bot.BullyingBot.core.exception.CommandEntityNotFoundException;
import lviv.team.bullying.bot.BullyingBot.response.DefaultResponseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultCommandProcessor implements CommandProcessor {

    @Autowired
    DefaultResponseManager defaultResponseManager;

    @Override
    public List<String> processCommand(long chatId, List<MessageEntity> messageEntities) {
        Optional<MessageEntity> maybeBotCommandEntity = messageEntities.stream()
                .filter(this::checkIsBotCommand)
                .findFirst();

        String command = maybeBotCommandEntity
                .orElseThrow(CommandEntityNotFoundException::new)
                .getText();

        if (DefaultCommands.START.getCommand().equals(command)){
            return List.of(defaultResponseManager.getResponseForStart());
        }

        return List.of("I don't know this command");
    }

    private boolean checkIsBotCommand(MessageEntity messageEntity) {
        return messageEntity.getType().equals(MessageEntityType.BOT_COMMAND.getType());
    }
}
