package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import BotLogic.Bot;
import BotLogic.util.SomeResponce;
import BotsZoo.TestBot;

import BotLogic.util.MainBotStrings;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    public TestBot createBot () {
        return new TestBot( new Bot());
    }
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void testParseDishFromMessage() {
        var s = SomeResponce.parseDishFromMessage("менюююю японская грузинская грузинская рандомная завтраки");
        assertEquals("yaponskaya-kuhnya", s[0]);
        assertEquals("zavtraki", s[1]);
    }

    @Test
    public void testCheckEdaRuConnection() {
        var flag = true;
        try {
            Jsoup.connect("https://eda.ru")
                    .userAgent("Mozilla")
                    .get();
        } catch (IOException e) {
            flag = false;
        }
        assertTrue(flag);
    }

    @Test
    public void testGenerateDefaultInput() {
        var bot = createBot();
        bot.mailBox(new String[]{ "default msg" });
        assertEquals(MainBotStrings.neponel(),
                bot.answers.get(0));
    }


    @Test
    public void testGenerateChoosingGenreCommandInput() {
        var bot = createBot();
        bot.mailBox(new String[] {"Хочу кушац"});
        assertEquals(MainBotStrings.kushac(), bot.answers.get(0));
    }

    @Test
    public void testGenerateStartCommandInput() {
        var bot = createBot();
        bot.mailBox(new String[]{ "/Start" });

        assertEquals(MainBotStrings.hello(), bot.answers.get(0));
    }
}
