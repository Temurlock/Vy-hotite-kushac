package BotsZoo;

import BotLogic.Bot;
import BotLogic.Sendable;

import java.util.Scanner;

public class ConsoleBot implements Sendable {

    private final Bot PAPA;

    public ConsoleBot(Bot bot) {
        PAPA = bot;
    }

    @Override
    public void send(Long chatId, String text) {
        System.out.println(text);
    }

    public void runDialoge() {
        Scanner in = new Scanner(System.in);
        while (true) {
            var t = in.nextLine();
            PAPA.getMessage(this, 0L, t);
        }
    }
}
