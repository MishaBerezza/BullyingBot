package lviv.team.bullying.bot.BullyingBot;

import lviv.team.bullying.bot.BullyingBot.config.BotConfig;
import lviv.team.bullying.bot.BullyingBot.core.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.core.commands.Command;
import lviv.team.bullying.bot.BullyingBot.core.commands.DefaultCommands;
import lviv.team.bullying.bot.BullyingBot.core.entity.MessageEntityType;
import lviv.team.bullying.bot.BullyingBot.core.exception.CommandEntityNotFoundException;
import lviv.team.bullying.bot.BullyingBot.core.exception.UnknownCommandException;
import lviv.team.bullying.bot.BullyingBot.processor.BullingCommandProcessor;
import lviv.team.bullying.bot.BullyingBot.processor.CommandProcessor;
import lviv.team.bullying.bot.BullyingBot.processor.DefaultCommandProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    private final Map<Command[], CommandProcessor> commandToCommandProcessors;

    public TelegramBot(BotConfig botConfig, BullingCommandProcessor bullingCommandProcessor, DefaultCommandProcessor defaultCommandProcessor) {
        this.commandToCommandProcessors = Map.of(
                DefaultCommands.values(), defaultCommandProcessor,
                BullingCommands.values(), bullingCommandProcessor
        );
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();

        if (Objects.isNull(update.getMessage().getText())) {
            sendMessage(chatId, "something went wrong");
        }


        try {
            Optional<MessageEntity> maybeBotCommandEntity = update.getMessage().getEntities().stream()
                    .filter(this::checkIsBotCommand)
                    .findFirst();

            MessageEntity botCommandMessageEntity = maybeBotCommandEntity.orElseThrow(CommandEntityNotFoundException::new);

            CommandProcessor commandProcessorByCommand = getCommandProcessorByCommand(botCommandMessageEntity.getText());

            List<String> messagesForSend = commandProcessorByCommand.processCommand(chatId, update.getMessage().getEntities());

            messagesForSend.forEach(message -> sendMessage(chatId, message));

        } catch (RuntimeException runtimeException) {
            sendMessage(chatId, runtimeException.getMessage());
        }
    }

    private boolean checkIsBotCommand(MessageEntity messageEntity) {
        return messageEntity.getType().equals(MessageEntityType.BOT_COMMAND.getType());
    }


    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private CommandProcessor getCommandProcessorByCommand(String command) {
        return commandToCommandProcessors.entrySet()
                .stream()
                .filter(entry -> isCommandsContainsSpecificCommand(entry.getKey(), command))
                .map(Map.Entry::getValue).findFirst()
                .orElseThrow(UnknownCommandException::new);
    }

    private boolean isCommandsContainsSpecificCommand(Command[] commands, String specificCommand) {
        return Arrays
                .stream(commands)
                .anyMatch(command -> command.getCommand().equals(specificCommand));
    }
}
