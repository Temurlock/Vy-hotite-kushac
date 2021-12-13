package BotsZoo.Telgram;

import BotLogic.DTO.Dishes;
import BotLogic.util.MainBotStrings;
import BotLogic.util.SomeResponce;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TgCommandsHandler {

    TgBot bot;

    public  TgCommandsHandler(TgBot bot) {
        this.bot = bot;
    }
    public void handleCommand(Update update) {

        var msg = update.getMessage();
        switch (msg.getText()) {
            case "/Start", "/start", "/help" -> bot.send(msg.getChatId(), MainBotStrings.hello());
            case "/kushac" -> new KushacEvent(msg.getChatId(), bot).start();
            default -> bot.send(msg.getChatId(), MainBotStrings.neponel());
        }

    }

    public void handleCallback(Update update) {
        var callback = update.getCallbackQuery();
        var msg = callback.getMessage();
        var text = msg.getText();
        var lines = text.lines().toArray(String[]::new);
        var stage = lines[lines.length - 1].split("\\.")[0];
        switch (stage) {
            case "1":
                var temp = callback.getData()
                          + "\n2.Выберите вид блюда";
                changeCallbackMessage(update, temp, KushacEvent.dishType);
                break;
            case "2":
                temp = text.split("\n")[0] + " "
                        + callback.getData() + "\n3.Выберите подходящий ингредиент";
                changeCallbackMessage(update, temp, KushacEvent.ingredient);
                break;
            case "3":
                if (callback.getData().equals("end")) {
                    changeCallbackMessage(update, text.split("\n")[0], null);
                    sendDishes(callback, text.split("\n")[0] + " end" );
                    break;
                }
                temp = text.split("\n")[0] + " "
                        + callback.getData() + "\n3.Выберите подходящий ингредиент";
                changeCallbackMessage(update, temp, KushacEvent.ingredient);
                break;
        }
    }

    public void changeCallbackMessage(Update update, String text, InlineKeyboardMarkup keyboardMarkup) {
        var callback = update.getCallbackQuery();
        var msg = callback.getMessage();
        try {
            bot.execute(EditMessageText
                    .builder()
                    .chatId(msg.getChatId().toString())
                    .messageId(msg.getMessageId())
                    .text(text)
                    .replyMarkup(keyboardMarkup)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendDishes(CallbackQuery callbackQuery, String line) {

        var s = SomeResponce.getTextDishes(line);
        List<String> urlList = List.of(s.split("\\n"));
        parseDishesUrlToDishes(urlList.get(0));
        urlList.parallelStream().forEach(url -> bot.send(
                callbackQuery.getMessage().getChatId(),
                parseDishesUrlToDishes(url).toString())
        );

        //bot.send(callbackQuery.getMessage().getChatId(), s);
    }

    public Dishes parseDishesUrlToDishes(String dishesUrl){
        try {
            Document document = Jsoup.connect(dishesUrl).get();
            String name = "";
            String imageUrl = "";
            HashMap<String, String> energyValues = new HashMap<>();
            List<String> components = new ArrayList<>();
            name = document.select("[itemprop=name]").text();
            document.select("[itemprop=recipeIngredient]").
                    forEach( item -> components.add(item.text()));
            imageUrl =  document.select("[alt=Изображение материала]")
                    .first()
                    .attr("src");
            Elements elements = document.select("[itemprop]");
            energyValues.put("Калории", elements.select("[itemprop=calories]").text());
            energyValues.put("Белки", elements.select("[itemprop=proteinContent]").text());
            energyValues.put("Жиры", elements.select("[itemprop=fatContent]").text());
            energyValues.put("Углеводы", elements.select("[itemprop=carbohydrateContent]").text());
            return new Dishes(name,energyValues,imageUrl,components, dishesUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
