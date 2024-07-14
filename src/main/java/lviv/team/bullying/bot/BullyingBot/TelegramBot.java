package lviv.team.bullying.bot.BullyingBot;

import lviv.team.bullying.bot.BullyingBot.config.BotConfig;
import lviv.team.bullying.bot.BullyingBot.processor.BullingCommandProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final BullingCommandProcessor bullingCommandProcessor;

    public TelegramBot(BotConfig botConfig, BullingCommandProcessor bullingCommandProcessor) {
        this.botConfig = botConfig;
        this.bullingCommandProcessor = bullingCommandProcessor;
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

        bullingCommandProcessor.processCommand(chatId, update.getMessage().getEntities());

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
}
