package BotsZoo;

import BotLogic.Bot;
import BotLogic.Sendable;

import java.util.ArrayList;
import java.util.List;

public class TestBot implements Sendable {

    final Bot PAPA;
    public List<String> answers = new ArrayList<>();

    @Override
    public void send(Long chatId, String text) {
        answers.add(text);
    }

    public TestBot(Bot papa) {
        this.PAPA = papa;
    }

    public void mailBox(String[] messages) {
        for (var s : messages) {
            PAPA.getMessage(this, 0L, s);
        }
    }
}
