import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {
    protected List<Person> passengers = new ArrayList<>();

    public Vehicle(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    protected int totalSeats;
    public int getTotalSeats() {
        return totalSeats;
    }

    public int getSeatsTakenNumber() {
        return totalSeats - passengers.size();
    }
    public abstract void getIn(Person person) throws Exception;
    public abstract void getOur(Person person) throws Exception;
}

class Bus extends Vehicle {

    }
}

class Car extends Vehicle {

}
class PoliceCar extends Car {

}
class Taxi extends Car {

}
class FireTruck extends Car {

}