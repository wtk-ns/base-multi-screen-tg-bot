
import bot.NewsBot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import utility.Properties;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Main {

    public static void main(String[] args) {
        log.info("Trying to start the bot");
        try {
            final TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(
                    new NewsBot(
                            Properties.getProperty("bot.token", String.class),
                            Properties.getProperty("bot.username", String.class)
                    )
            );
            log.info("Bot started");
        } catch (TelegramApiRequestException e) {
            log.error("Unable to start the bot");
            e.printStackTrace();
        } catch (TelegramApiException e) {
            log.error("Unable to start the bot. ");
            e.printStackTrace();
        }
    }
}
