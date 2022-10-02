import java.util.ArrayList;
import java.util.List;

public class Road<T extends Vehicle> {
    public List<T> carsInRoad = new ArrayList<>();
    public int getCountOfHumans(){
        int total = 0;
        for (var car: carsInRoad) {
            total += car.getSeatsTakenNumber();
        }
        return total;
    }
    public void addCarToRoad(T vehicle){
        if (!carsInRoad.contains(vehicle)) {
            carsInRoad.add(vehicle);
        }
    }
}
