package BotsZoo;

import BotLogic.Bot;
import BotLogic.Sendable;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class TgBot extends TelegramLongPollingCommandBot
        implements Sendable {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private final Bot PAPA;

    public TgBot(String botName, String botToken, Bot papa) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.PAPA = papa;
        //создаём вспомогательный класс для работы с сообщениями, не являющимися командами
        //register(new StartCommand("start", "Старт"));
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        PAPA.getMessage(this, chatId, msg.getText());
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param text текст ответа
     */
    @Override
    public void send(Long chatId, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {

        }
    }
}