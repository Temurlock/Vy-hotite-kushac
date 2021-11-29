package BotsZoo;

import BotLogic.Bot;
import BotLogic.Sendable;

import java.util.Scanner;

public class ConsoleBot implements Sendable {

    private Bot Papa;

    public ConsoleBot(Bot bot) {
        Papa = bot;
    }

    @Override
    public void send(Long chatId, String text) {
        System.out.println(text);
    }

    public void runDialoge() {
        Scanner in = new Scanner(System.in);
        while (true) {
            var t = in.nextLine();
            Papa.getMessage(this, 0L, t);
        }
    }
}
