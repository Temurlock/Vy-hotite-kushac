import BotLogic.Bot;
import BotsZoo.ConsoleBot;
import BotsZoo.Telgram.TgBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Program {
    public static void main(String[] args) {
        var bot = new Bot();

        //new ConsoleBot(bot).runDialoge();
        runTg(bot);
    }

// TODO: Закинуть в другое место
    public static void runTg(Bot bot){

        String name = "Kushac_2_bot";
        String token = "";

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
