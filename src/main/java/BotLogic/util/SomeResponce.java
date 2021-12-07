package BotLogic.util;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SomeResponce {

    public static HashMap<String, String> dishType = new LinkedHashMap<>() {
        {
            put("завтраки", "zavtraki");
            put("закуски", "zakuski");
            put("напитки", "napitki");
            put("салаты", "salaty");
            put("супы", "supy");
            put("skip", "null");
        }
    };

    public static HashMap<String, String> cuisine = new LinkedHashMap<>() {
        {
            put("китайская", "kitayskaya-kuhnya");
            put("грузинская", "gruzinskaya-kuhnya");
            put("итальянская", "italyanskaya-kuhnya");
            put("русская", "russkaya-kuhnya");
            put("французская", "francuzskaya-kuhnya");
            put("японская", "yaponskaya-kuhnya");
            put("skip", "null");
        }
    };

    public static HashMap<String, String> ingredient = new LinkedHashMap<>() {
        {
            put("яйцо", "13418");
            put("капуста", "15096");
            put("морковь", "13449");
            put("тыква", "13774");
            put("кабачки", "13539");
            put("свинина", "13480");
            put("сливы", "13762");
            put("картофель", "13431");
            put("рис", "13435");
            put("макароны", "13484");
            put("паста", "14380");
            put("лапша", "13747");
            put("баранина", "13470");
            put("сыр", "13426");
            put("end", "null");
        }
    };

    private static final String mainLink = "https://eda.ru";

    public static String makeLink(Queue<String> request) {

        var link = "https://eda.ru/recepty";


        if (!"null".equals(request.peek())) {
            link += "/" + request.peek();
        }
        request.poll();

        if (!"null".equals(request.peek())) {
            link += "/" + request.peek();
        }
        request.poll();

        if (!"null".equals(request.peek())) {
            link += "/ingredienty";
        }

        while (!request.isEmpty() && !"null".equals(request.peek())) {
            link += "/" + request.poll();
        }
        System.out.println(link);
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
                makeLink(attr)
        );

        return String.join("\n", links);
    }


    public static Queue<String> parseDishFromMessage(String text) {


        Queue<String> resultQueue = new ArrayDeque<>();

        try {
            resultQueue.add(
                    Arrays.stream(text.split(" "))
                            .distinct()
                            .filter(cuisine::containsKey)
                            .map(cuisine::get).toArray(String[]::new)[0]
            );
        } catch (IndexOutOfBoundsException e) {
            resultQueue.add("null");
        }


        try {
            resultQueue.add(
                    Arrays.stream(text.split(" "))
                            .distinct()
                            .filter(dishType::containsKey)
                            .map(dishType::get).toArray(String[]::new)[0]
            );
        } catch (IndexOutOfBoundsException e) {
            resultQueue.add("null");
        }


        resultQueue.addAll(Arrays.stream(text.split(" "))
                .distinct()
                .filter(ingredient::containsKey)
                .map(ingredient::get).toList()
        );

        return resultQueue;
    }

}