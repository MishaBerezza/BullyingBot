package lviv.team.bullying.bot.BullyingBot.processor;

import lviv.team.bullying.bot.BullyingBot.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.entity.EntityTypes;
import lviv.team.bullying.bot.BullyingBot.exception.CommandEntityNotFoundException;
import lviv.team.bullying.bot.BullyingBot.exception.UnknownCommandException;
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

        List<MessageEntity> mentionMessageEntityList = messageEntities.stream().filter(this::checkIsMention).toList();


        if (BullingCommands.SAVE.getCommand().equals(command)) {
            return bullingService.saveBullingRecords(chatId, mentionMessageEntityList);
        } else {
            return List.of("I don't know this command");
        }
    }

    private String getCommandFromMessageEntity(MessageEntity messageEntity) {
        return Optional.of(messageEntity).stream()
                .map(MessageEntity::getText)
                .filter(BullingCommands::isCommandExists)
                .findFirst()
                .orElseThrow(UnknownCommandException::new);
    }


    private boolean checkIsMention(MessageEntity messageEntity) {
        return messageEntity.getType().equals(EntityTypes.MENTION.getType());
    }

    private boolean checkIsBotCommand(MessageEntity messageEntity) {
        return messageEntity.getType().equals(EntityTypes.BOT_COMMAND.getType());
    }
}
