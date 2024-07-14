package lviv.team.bullying.bot.BullyingBot.processor;

import lviv.team.bullying.bot.BullyingBot.entity.EntityTypes;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

public interface CommandProcessor {

    List<String> processCommand(long chatId, List<MessageEntity> messageEntities);
}
