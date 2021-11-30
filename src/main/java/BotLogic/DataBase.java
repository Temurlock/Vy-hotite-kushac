package BotLogic;

import java.util.HashMap;


// Потом это станет интерфейсом
public class DataBase {

    HashMap<Long, Integer> data = new HashMap<>();

    public Integer getState(Long id) {

        if (!data.containsKey(id))
            return 0;
        return data.get(id);
    }

    public void changeState(Long id, Integer state) {
        if (data.containsKey(id)) {
            data.replace(id, state);
        } else {
            addUser(id, state);
        }
    }

    private void addUser(Long id, Integer state) {
        data.put(id, state);
    }

}
