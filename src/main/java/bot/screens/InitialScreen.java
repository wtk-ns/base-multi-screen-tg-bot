package bot.screens;

import bot.ButtonData;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import utility.ResourceTemplateReader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InitialScreen extends BotScreen {

    public InitialScreen(Long screenId) {
        super(screenId);
    }

    @Override
    public String getScreenContent() {
        String content = "Unable to show this content.";
        try {
            content = ResourceTemplateReader.getScreen(this.getClass());
        } catch (Exception e) {
            log.info("Error while loading screen from the resource folder by name {}", this.getClass().getSimpleName());
        }
        return content;
    }

    @Override
    public InlineKeyboardMarkup getScreenMarkup() {
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        final List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(formButton("To screen 2", 1L, 2L, false));
        row.add(formButton("To screen 3", 2L, 3L, false));
        list.add(row);
        return new InlineKeyboardMarkup(list);
    }

    @Override
    public void handleButtonPress(ButtonData data) {
        if (!getScreedId().equals(data.getToScreenId())) {
            log.info("Screen change button");
        } else {
            log.info("Business logic button");
        }
    }
}
