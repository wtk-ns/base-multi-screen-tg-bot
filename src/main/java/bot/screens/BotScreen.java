package bot.screens;

import bot.ButtonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BotScreen {
    Long screedId;
    ObjectMapper mapper = new ObjectMapper();

    public BotScreen(Long screedId) {
        this.screedId = screedId;
    }

    public abstract String getScreenContent();

    public abstract InlineKeyboardMarkup getScreenMarkup();

    public abstract void handleButtonPress(ButtonData data);


    protected InlineKeyboardButton formButton(String text, Long buttonId, Long toScreen, boolean isBackButton) {
        final InlineKeyboardButton button = new InlineKeyboardButton(text);
        final ButtonData data = new ButtonData();
        data.setId(buttonId);
        data.setToScreenId(toScreen);
        data.setIsBackButton(isBackButton);
        button.setCallbackData(formMessage(data));
        return button;
    }

    private String formMessage(ButtonData data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Unable to show the message.";
    }
}
