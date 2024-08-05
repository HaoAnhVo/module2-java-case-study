package case_study.model.cinema_manage;

import java.util.HashMap;
import java.util.Map;

public class Cinema {
    private String name;
    private final Map<String, Theater> rooms;

    public Cinema(String name) {
        this.name = name;
        this.rooms = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            rooms.put("Room " + i, new Theater("Room " + i, 30));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Theater> getRooms() {
        return rooms;
    }

    public Theater getRoom(String name) {
        return rooms.get(name);
    }

    public void addRoom(Theater theater) {
        rooms.put(theater.getName(), theater);
    }

    public void updateTheater(String oldName, Theater newRoom) {
        if (rooms.containsKey(oldName)) {
            rooms.remove(oldName);
            rooms.put(newRoom.getName(), newRoom);
        }
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ",rooms=" + rooms +
                '}';
    }

}
