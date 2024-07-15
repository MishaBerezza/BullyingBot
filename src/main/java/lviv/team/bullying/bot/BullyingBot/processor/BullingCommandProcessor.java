package lviv.team.bullying.bot.BullyingBot.processor;

import lviv.team.bullying.bot.BullyingBot.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.core.entity.MessageEntityType;
import lviv.team.bullying.bot.BullyingBot.core.exception.CommandEntityNotFoundException;
import lviv.team.bullying.bot.BullyingBot.core.exception.UnknownCommandException;
import lviv.team.bullying.bot.BullyingBot.services.BullingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.Optional;

@Component
public class BullingCommandProcessor implements CommandProcessor {

    BullingService bullingService;


    public BullingCommandProcessor(BullingService bullingService) {
        this.bullingService = bullingService;
    }

    public List<String> processCommand(long chatId, List<MessageEntity> messageEntities) {
        Optional<MessageEntity> maybeBotCommandEntity = messageEntities.stream()
                .filter(this::checkIsBotCommand)
                .findFirst();

        MessageEntity botCommandMessageEntity = maybeBotCommandEntity.orElseThrow(CommandEntityNotFoundException::new);

        String command = getCommandFromMessageEntity(botCommandMessageEntity);


        if (BullingCommands.SAVE.getCommand().equals(command)) {
            List<MessageEntity> mentionMessageEntityList = messageEntities.stream()
                    .filter(this::checkIsMention)
                    .toList();

            return bullingService.saveBullingRecords(chatId, mentionMessageEntityList);
        }

        if (BullingCommands.GET_RECORDS.getCommand().equals(command)) {
            return bullingService.getAllBullingRecords(chatId);
        }
        if (BullingCommands.GET_RECORDS_BY_NAME.getCommand().equals(command)) {
            return List.of("I don't know this command");
        }

        return List.of("I don't know this command 2");
    }

    private String getCommandFromMessageEntity(MessageEntity messageEntity) {
        return Optional.of(messageEntity).stream()
                .map(MessageEntity::getText)
                .filter(BullingCommands::isCommandExists)
                .findFirst()
                .orElseThrow(UnknownCommandException::new);
    }


    private boolean checkIsMention(MessageEntity messageEntity) {
        return messageEntity.getType().equals(MessageEntityType.MENTION.getType());
    }

    private boolean checkIsBotCommand(MessageEntity messageEntity) {
        return messageEntity.getType().equals(MessageEntityType.BOT_COMMAND.getType());
    }
}
