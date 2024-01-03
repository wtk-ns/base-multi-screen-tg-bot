package bot;

import bot.screens.BotScreen;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utility.LimitedSizeStack;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsBot extends TelegramLongPollingBot {

    String botUsername;
    ObjectMapper mapper;
    Map<Long, Integer> chatToMessageId = new HashMap<>();
    Map<Long, LimitedSizeStack<Long>> chatToScreens = new HashMap<>();

    public NewsBot(String botToken, String botUsername) {
        super(botToken);
        this.botUsername = "@" + botUsername;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update);
        } else {
            handleUserMessage(update);
        }
    }

    private void handleUserMessage(Update update) {
        try {
            final Long chatId = update.getMessage().getChatId();
            final BotScreen screen = ScreenFactory.getInitial();
            sendScreenToChat(screen, chatId);

            final LimitedSizeStack<Long> screenStack = new LimitedSizeStack<Long>(5);
            screenStack.push(screen.getScreedId());
            chatToScreens.put(chatId, screenStack);
        } catch (Exception e) {
            log.error("Enable to handle callback");
            e.printStackTrace();
        }
    }

    private void handleCallback(Update update) {
        log.info("Callback called");
        try {
            final Long chatId = update.getCallbackQuery().getMessage().getChatId();
            final ButtonData data = mapper.readValue(update.getCallbackQuery().getData(), ButtonData.class);
            handleBackButton(data, chatId);

            final BotScreen screen = ScreenFactory.get(data.getToScreenId());
            screen.handleButtonPress(data);
            sendScreenToChat(screen, chatId);
        } catch (Exception e) {
            log.error("Unable to process callback data");
            e.printStackTrace();
        }
    }

    private void sendScreenToChat(BotScreen screen, Long chatId) throws TelegramApiException {
        Integer messageToEdit = chatToMessageId.getOrDefault(chatId, null);

        if (messageToEdit != null) {
            EditMessageText messageText = EditMessageText
                    .builder()
                    .chatId(chatId)
                    .parseMode("HTML")
                    .messageId(messageToEdit)
                    .text(screen.getScreenContent())
                    .replyMarkup(screen.getScreenMarkup())
                    .disableWebPagePreview(true)
                    .build();
            execute(messageText);
        } else {
            SendMessage message = SendMessage
                    .builder()
                    .parseMode("HTML")
                    .chatId(chatId)
                    .text(screen.getScreenContent())
                    .replyMarkup(screen.getScreenMarkup())
                    .disableWebPagePreview(true)
                    .build();
            final Message m = execute(message);
            chatToMessageId.put(chatId, m.getMessageId());
        }
    }

    private void handleBackButton(ButtonData data, Long chatId) {
        if (!chatToScreens.containsKey(chatId) || (!data.getIsBackButton() && data.getToScreenId().equals(ScreenFactory.getInitialScreenId()))) {
            chatToScreens.put(chatId, new LimitedSizeStack<>(5));
        }
        if (data.getIsBackButton()) {
            if (chatToScreens.get(chatId).size() < 3) {
                data.setToScreenId(ScreenFactory.getInitialScreenId());
            } else {
                chatToScreens.get(chatId).pop();
                data.setToScreenId(chatToScreens.get(chatId).peek());
            }
        } else {
            chatToScreens.get(chatId).push(data.getToScreenId());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
