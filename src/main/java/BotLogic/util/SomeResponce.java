package BotLogic.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SomeResponce {


    public static HashMap<String, String> dict_of_kuhnya = new HashMap<>() {
        {
            put("китайская", "kitayskaya-kuhnya");
            put("грузинская", "gruzinskaya-kuhnya");
            put("итальянская", "italyanskaya-kuhnya");
            put("русская", "russkaya-kuhnya");
            put("французская", "francuzskaya-kuhnya");
            put("японская", "yaponskaya-kuhnya");
        }
    };

    public static HashMap<String, String> dict_of_vid = new HashMap<>() {
        {
            put("завтраки", "zavtraki");
            put("закуски", "zakuski");
            put("напитки", "napitki");
            put("салаты", "salaty");
            put("супы", "supy");
        }
    };

    private static final String mainLink = "https://eda.ru";

    public static String makeLink(String kuhnya, String vid) {

        var link = "https://eda.ru/recepty";
        if (kuhnya != null) {
            link += "/" + kuhnya;
        }
        if (vid != null) {
            link += "/" + vid;
        }

        return link;

    }

    public static List<String> findLinks(String link) {

        if (link.equals("https://eda.ru/recepty"))
            return Collections.emptyList();

        Document doc = null;
        try {
            doc = Jsoup.connect(link)
                    .userAgent("Mozilla")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("a[href]");


        return filterLinks(elements);
    }

    private static List<String> filterLinks(Elements elements) {
        return elements.stream().map((el) -> el.attr("href"))
                .filter((s) -> s.matches("^.*-\\d{3,6}$"))
                .map((el) -> mainLink + el)
                .distinct()
                .toList();
    }

    public static String getTextDishes(String text) {

        var attr = parseDishFromMessage(text);
        var links = findLinks(
                makeLink(attr[0], attr[1])
        );

        return String.join("\n", links);
    }

    public static String[] parseDishFromMessage(String text) {

        String cuisine;
        String vid;

        try {
            cuisine = (String) Arrays.stream(text.split(" "))
                    .distinct()
                    .filter(dict_of_kuhnya::containsKey)
                    .map(dict_of_kuhnya::get).toArray()[0];
        } catch (IndexOutOfBoundsException e) {
            cuisine = null;
        }

        try {
            vid = (String) Arrays.stream(text.split(" "))
                    .distinct()
                    .filter(dict_of_vid::containsKey)
                    .map(dict_of_vid::get).toArray()[0];
        } catch (IndexOutOfBoundsException e) {
            vid = null;
        }

        return new String[]{cuisine, vid};
    }
}