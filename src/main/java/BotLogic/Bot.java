package BotLogic;

import BotLogic.util.MainBotStrings;
import BotLogic.util.SomeResponce;

public class Bot {

    DataBase DB = new DataBase();


    public void getMessage( Sendable messenger,Long chatId, String text) {
        if (text.isEmpty()) {
            return;
        }
        resolveMessage(
                new Message( messenger, chatId, text)
        );
    }

    private void sendMessage(Sendable messenger, Long chatId, String Text) {
        messenger.send(chatId, Text);
    }

    private void resolveMessage(Message msg) {
        switch (msg.text) {
            case "/Start":
                sendMessage( msg.messenger, msg.id, MainBotStrings.hello());
                break;
            case "Хочу кушац":
                kushac(msg);
                break;
            default:
                resolveStates(msg);
                break;
        }
    }

    private void kushac(Message msg) {
        DB.changeState(msg.id, 1);
        sendMessage(msg.messenger, msg.id, MainBotStrings.kushac());
    }

    private void resolveStates(Message msg) {
        switch (
                DB.getState(msg.id)
        ) {
            case 0:
                sendMessage(msg.messenger, msg.id, MainBotStrings.neponel());
                break;
            case 1:
                var s = SomeResponce.getTextDishes(msg.text);
                sendMessage(
                        msg.messenger, msg.id,
                        s
                );
                DB.changeState(msg.getId(), 0);
                break;
        }
    }
}

class Message {

    Sendable messenger;
    Long id;
    String text;

    public Message( Sendable messenger, Long id , String text) {
        this.id = id;
        this.messenger = messenger;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public  Sendable getMessenger() {
        return  messenger;
    }

    public String getText() {
        return text;
    }

    public  void setText(String text) {
        this.text = text;
    }
}


