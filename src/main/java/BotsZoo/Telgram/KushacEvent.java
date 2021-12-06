package BotsZoo.Telgram;

import BotLogic.util.SomeResponce;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class KushacEvent {

    Long chatId;
    TgBot bot;

    public static InlineKeyboardMarkup cuisine = new InlineKeyboardMarkup() {{
        setKeyboard( TgBot.createBtnsList(SomeResponce.cuisine.keySet().toArray(new String[0]) ));
    }};

    public static InlineKeyboardMarkup dishType = new InlineKeyboardMarkup() {{
        setKeyboard( TgBot.createBtnsList(SomeResponce.dishType.keySet().toArray(new String[0]) ));
    }};

    public static InlineKeyboardMarkup ingredient = new InlineKeyboardMarkup() {{
        setKeyboard(TgBot.createBtnsList(SomeResponce.ingredient.keySet().toArray(new String[0]) ));
    }};


    public KushacEvent(Long chatId, TgBot bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    public void start() {
        SendMessage answer = new SendMessage();
        answer.setText( "1.Выберете тип кухни" );
        answer.setChatId(chatId.toString());
        answer.setReplyMarkup( cuisine );
        try {
            bot.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
