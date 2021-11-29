package BotsZoo;

import BotLogic.Bot;
import BotLogic.Sendable;

import java.util.ArrayList;
import java.util.List;

public class TestBot implements Sendable{

    final Bot PAPA;
    public List<String> Answers = new ArrayList<>();

    @Override
    public void send(Long chatId, String text) {
        Answers.add(text);
    }

    public TestBot (Bot papa) {
        this.PAPA = papa;
    }

    public void mailBox ( String[] messages) {
        for (var s :messages) {
            PAPA.getMessage(this, 0L, s);
        }
    }
}
