package BotsZoo.Telgram;

import BotLogic.Bot;
import BotLogic.Sendable;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public final class TgBot extends TelegramLongPollingBot  implements Sendable {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final Bot PAPA;

    private final TgCommandsHandler commandsHandler;

    private InlineKeyboardMarkup mainKeyboardMarkup = new InlineKeyboardMarkup();
    private InlineKeyboardMarkup secondKeyboardMarkup = new InlineKeyboardMarkup();


    public TgBot(String botName, String botToken, Bot papa) {

        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.PAPA = papa;

        this.commandsHandler = new TgCommandsHandler(this);
        var mainBtnsList = createBtnsList( new String[]{"Завтраки","Супы","Напитки","Закуски","Салаты"});
        mainKeyboardMarkup.setKeyboard(mainBtnsList);
        var secondBtnsList = createBtnsList( new String[]{"Грузинская","Русская","Итальянская","Японская","Французская","Китайская"});
        secondKeyboardMarkup.setKeyboard(secondBtnsList);

    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /*
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        PAPA.getMessage(this, chatId, msg.getText());
    }
    */

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void send(Long chatId, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
                e.printStackTrace();
        }
    }

    //Чтобы добавлять кнопки
    public static InlineKeyboardButton createButton(String btnName, String callBackData){
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText(btnName);
        btn.setCallbackData(callBackData);
        //добавить функционал
        return btn;
    }

    public static List<List<InlineKeyboardButton>> createBtnsList(String[] btns){
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for(String btnName: btns){
            keyboard.add(List.of(createButton(btnName, btnName)));
        }
        return keyboard;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                commandsHandler.handleCommand(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (update.hasCallbackQuery()) {
            try {
                commandsHandler.handleCallback(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}