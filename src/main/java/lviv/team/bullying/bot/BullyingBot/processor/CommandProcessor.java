package lviv.team.bullying.bot.BullyingBot.processor;

import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

public interface CommandProcessor {

    List<String> processCommand(long chatId, List<MessageEntity> messageEntities);
}
