import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {
    private List<Person> passengers = new ArrayList<>();

    public Vehicle(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    private int totalSeats;

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getSeatsTakenNumber() {
        return passengers.size();
    }

    public void getIn(Person person) throws MaxSitsException, PassengerIsNotSupportedException, PassengerAlreadySeatingException {
        if (passengers.contains(person)) {
            throw new PassengerAlreadySeatingException();
        }
        if (getTotalSeats() - getSeatsTakenNumber() < 1) {
            throw new MaxSitsException();
        }
        passengers.add(person);
    }
    public void getOut(Person person) throws PassengerNotFoundException {
        if (!passengers.contains(person)) {
            throw new PassengerNotFoundException();
        }
        passengers.remove(person);
    }
}

class Bus extends Vehicle {

    public Bus(int totalSeats) {
        super(totalSeats);
    }

}

class Car extends Vehicle {

    public Car() {
        super(4);
    }

    public Car(int totalSeats) {
        super(totalSeats);
    }
}
class PoliceCar extends Car {

    @Override
    public void getIn(Person person) throws MaxSitsException, PassengerIsNotSupportedException, PassengerAlreadySeatingException {
        if (!(person instanceof Policeman)) {
            throw new PassengerIsNotSupportedException();
        }

        super.getIn(person);
    }
}
class Taxi extends Car {

}
class FireTruck extends Car {
    @Override
    public void getIn(Person person) throws MaxSitsException, PassengerIsNotSupportedException, PassengerAlreadySeatingException {
        if (!(person instanceof Fireman)) {
            throw new PassengerIsNotSupportedException();
        }

        super.getIn(person);
    }
}