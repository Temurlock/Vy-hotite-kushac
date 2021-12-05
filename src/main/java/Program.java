import BotLogic.Bot;
import BotsZoo.ConsoleBot;
import BotsZoo.TgBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Program {
    public static void main(String[] args) {
        var bot = new Bot();
        //new ConsoleBot(bot).runDialoge(); - консольный
        runTg(bot);
    }

// TODO: Закинуть в другое место
    public static void runTg(Bot bot){
        String name = "";
        String token ="";
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/properties.properties"));
            name = properties.getProperty("botName");
            token = properties.getProperty("botToken");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        TgBot a = new TgBot(name, token, bot);
        try {
            TelegramBotsApi botapi = new TelegramBotsApi(DefaultBotSession.class);
            botapi.registerBot(a);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        System.out.println("Tg running!");
    }
}
