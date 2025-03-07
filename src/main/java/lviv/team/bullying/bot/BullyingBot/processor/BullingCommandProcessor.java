package lviv.team.bullying.bot.BullyingBot.processor;

import lviv.team.bullying.bot.BullyingBot.core.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.core.entity.MessageEntityType;
import lviv.team.bullying.bot.BullyingBot.core.exception.CommandEntityNotFoundException;
import lviv.team.bullying.bot.BullyingBot.core.exception.InvalidCommandContentException;
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

        String command = maybeBotCommandEntity.orElseThrow(CommandEntityNotFoundException::new).getText();


        if (BullingCommands.SAVE.getCommand().equals(command)) {
            List<MessageEntity> mentionMessageEntityList =
                    getMessageEntitiesWithMentionType(messageEntities);


            return bullingService.saveBullingRecords(chatId, mentionMessageEntityList);
        }

        if (BullingCommands.GET_RECORDS.getCommand().equals(command)) {
            return bullingService.getAllBullingRecordsByChatId(chatId);
        }
        if (BullingCommands.GET_RECORDS_BY_NAME.getCommand().equals(command)) {
            List<MessageEntity> mentionTypeEntities =
                    getMessageEntitiesWithMentionType(messageEntities);

            return bullingService.getBullingRecordsByUserTag(chatId, mentionTypeEntities);
        }

        return List.of("I don't know this command");
    }

    private List<MessageEntity> getMessageEntitiesWithMentionType(List<MessageEntity> messageEntities) {
        List<MessageEntity> mentionMessageEntities = messageEntities.stream()
                .filter(this::checkIsMention)
                .toList();

        if (mentionMessageEntities.isEmpty()) {
            throw new InvalidCommandContentException("This command have to contain user mention");
        }
        return mentionMessageEntities;
    }

    private boolean checkIsMention(MessageEntity messageEntity) {
        return messageEntity.getType().equals(MessageEntityType.MENTION.getType());
    }

    private boolean checkIsBotCommand(MessageEntity messageEntity) {
        return messageEntity.getType().equals(MessageEntityType.BOT_COMMAND.getType());
    }
}
