package lviv.team.bullying.bot.BullyingBot;

import lviv.team.bullying.bot.BullyingBot.config.BotConfig;
import lviv.team.bullying.bot.BullyingBot.core.commands.BullingCommands;
import lviv.team.bullying.bot.BullyingBot.core.commands.Command;
import lviv.team.bullying.bot.BullyingBot.core.commands.DefaultCommands;
import lviv.team.bullying.bot.BullyingBot.core.entity.MessageEntityType;
import lviv.team.bullying.bot.BullyingBot.core.exception.BullingBotException;
import lviv.team.bullying.bot.BullyingBot.core.exception.CommandEntityNotFoundException;
import lviv.team.bullying.bot.BullyingBot.core.exception.UnknownCommandException;
import lviv.team.bullying.bot.BullyingBot.processor.BullingCommandProcessor;
import lviv.team.bullying.bot.BullyingBot.processor.CommandProcessor;
import lviv.team.bullying.bot.BullyingBot.processor.DefaultCommandProcessor;
import lviv.team.bullying.bot.BullyingBot.response.ResponseMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
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
    public void onUpdateReceived(Update update){
        if (isCommandForIgnore(update)){
            return;
        }

        Long chatId = update.getMessage().getChatId();

        System.out.println(update.getMessage());

        try {
        List<MessageEntity> messageEntities = update.getMessage().getEntities();

        if (Objects.isNull(messageEntities) || messageEntities.isEmpty()){
            throw new UnknownCommandException();
        }

            Optional<MessageEntity> maybeBotCommandEntity = messageEntities.stream()
                    .filter(this::checkIsBotCommand)
                    .findFirst();

            MessageEntity botCommandMessageEntity = maybeBotCommandEntity.orElseThrow(CommandEntityNotFoundException::new);

            CommandProcessor commandProcessorByCommand = getCommandProcessorByCommand(botCommandMessageEntity.getText());

            List<String> messagesForSend = commandProcessorByCommand.processCommand(chatId, messageEntities);

            messagesForSend.forEach(message -> sendMessage(chatId, message));

        } catch (BullingBotException bullingBotException) {
            sendMessage(chatId, bullingBotException.getMessage());
        }catch (RuntimeException runtimeException){
            sendMessage(chatId, ResponseMessages.DEFAULT_EXCEPTION_RESPONSE);
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

    private boolean isCommandForIgnore(Update update){
        boolean isCommandWithoutMessage = isCommandWithoutMessage(update.getMessage());

        if (isCommandWithoutMessage) {
            return true;
        }

        boolean addingChatMember = isAddingChatMember(update.getMessage().getNewChatMembers());

        if (addingChatMember) {
            return true;
        }

        boolean isChatMemberLeft = isChatMemberLeft(update.getMessage().getLeftChatMember());
        if (isChatMemberLeft){
            return true;
        }

        return false;
    }

    private boolean isCommandWithoutMessage(Message message){
        return Objects.isNull(message);
    }


    private boolean isAddingChatMember(List<User> users){
        return !users.isEmpty();
    }

    private boolean isChatMemberLeft(User user){
        return !Objects.isNull(user);
    }
}
