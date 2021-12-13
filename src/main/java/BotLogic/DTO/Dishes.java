package BotLogic.DTO;

import java.util.HashMap;
import java.util.List;

public class Dishes {
    private String name;
    private HashMap<String,String> energyValues;
    private String imageUrl;
    private List<String> components;
    private String link;


    public Dishes(String name, HashMap<String, String> energyValues, String imageUrl, List<String> components, String link) {
        this.name = name;
        this.energyValues = energyValues;
        this.imageUrl = imageUrl;
        this.components = components;
        this.link = link;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getEnergyValues() {
        return energyValues;
    }

    public void setEnergyValues(HashMap<String, String> energyValues) {
        this.energyValues = energyValues;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("").append(this.name).append("]").append("(").append(link).append(")\n");
        sb.append("<a href=\"").append(link).append("\"> ").append(this.name).append("</a>\n");

        sb.append("Энергетическая ценность: \n");
        energyValues.forEach((key, value) -> sb.append(key).append(": ").append(value).append(" | "));
        sb.append("\n");

        sb.append("Ингредиенты: ");
        components.forEach(component -> sb.append("\n*").append(component));
        sb.append("\n");
        /*
        Сообщение ботом отправляется в формате HTML
        */
        //sb.append(imageUrl);
        return sb.toString();
    }
}
