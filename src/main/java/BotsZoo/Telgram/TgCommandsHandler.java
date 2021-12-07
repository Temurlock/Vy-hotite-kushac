package BotsZoo.Telgram;

import BotLogic.util.MainBotStrings;
import BotLogic.util.SomeResponce;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                          + "\n2.Выберете вид блюда";
                changeCallbackMessage(update, temp, KushacEvent.dishType);
                break;
            case "2":
                temp = text.split("\n")[0] + " "
                        + callback.getData() + "\n3.Выберете подходящий ингридиент";
                changeCallbackMessage(update, temp, KushacEvent.ingredient);
                break;
            case "3":
                if (callback.getData().equals("end")) {
                    changeCallbackMessage(update, text.split("\n")[0], null);
                    sendDishes(callback, text.split("\n")[0] + " end" );
                    break;
                }
                temp = text.split("\n")[0] + " "
                        + callback.getData() + "\n3.Выберете подходящий ингридиент";
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

        bot.send(
                callbackQuery.getMessage().getChatId(), s
        );
    }
}
