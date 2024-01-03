package bot;

import bot.screens.BotScreen;
import bot.screens.InitialScreen;
import bot.screens.SettingScreen;
import bot.screens.TestScreen;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ScreenFactory {
    private static final Map<Long, BotScreen> staticScreens = new HashMap<>();
    static {
        staticScreens.put(1L, new InitialScreen(1L));
        staticScreens.put(2L, new SettingScreen(2L));
        staticScreens.put(3L, new TestScreen(3L));
    }

    public static BotScreen get(Long screenId) {
        BotScreen result = null;
        if (staticScreens.containsKey(screenId)) {
            result = staticScreens.get(screenId);
            log.info("Screen {}: {}", screenId, result.hashCode());
        }
        return result;
    }

    public static BotScreen getInitial() {
        return staticScreens.get(1L);
    }

    public static Long getInitialScreenId() {
        return 1L;
    }

}
