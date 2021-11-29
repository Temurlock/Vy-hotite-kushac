import BotLogic.Bot;
import BotsZoo.ConsoleBot;
import BotsZoo.TgBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Program {


    static String Name = "";
    static String Token = "";


    // много мусора потом почистим

    public static void main (String args[]){

        var bot = new Bot();

        //new ConsoleBot(bot).runDialoge(); - консольный

        //runTg(bot); - телеграмный


    }


    public static void runTg (Bot bot) {

        TgBot A = new TgBot(Name, Token, bot);
        try {
            TelegramBotsApi botapi = new TelegramBotsApi(DefaultBotSession.class);
            botapi.registerBot(A);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        System.out.println("Tg running!");
    }
}
