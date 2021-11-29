package BotLogic;

import java.util.HashMap;


// Потом это станет интерфейсом
public class DataBase {

    HashMap<Long, Integer> DB = new HashMap<>();

    public Integer getState(Long id) {

        if (!DB.containsKey(id))
            return 0;
        return DB.get(id);
    }

    public void changeState(Long id, Integer state) {
        if (DB.containsKey(id)) {
            DB.replace(id, state);
        } else {
            addUser(id, state);
        }
    }

    private void addUser(Long id, Integer state) {
        DB.put(id, state);
    }

}
