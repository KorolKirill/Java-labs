public class Main {
    public static void main(String[] args) throws MaxSitsException, PassengerIsNotSupportedException, PassengerAlreadySeatingException {
    ModuleTests tests = new ModuleTests();
    tests.globalTest();
    }
}

class ModuleTests {
    public void globalTest() {
        test1();
        test2();
        test3();
        test4();
        test5();
    }
    // міліцейська машина – тільки міліціонерів.
    public void test1() {
        var policeCar = new PoliceCar();
        var fireman = new Fireman();
        try {
            policeCar.getIn(fireman);
            System.out.println("Test 1 hasn't passed, exception should have appeared.");
        } catch (MaxSitsException | PassengerAlreadySeatingException e) {
            System.out.println("Wrong exception");
            e.printStackTrace();
        } catch (PassengerIsNotSupportedException e) {
            System.out.println("Test 1 passed successfully,  PassengerIsNotSupportedException was thrown");
        }
    }
    //Кожний транспортний засіб має обмежену кількість місць.
    // Реалізувати функцію для отримання максимальної кількість місць та функцію для отримання кількості зайнятих місць.
    public void test2() {
        System.out.println("Test 2 started");
        var vehicle = new Bus(10);
        for (int i = 0; i < 7; i++) {
            try {
                vehicle.getIn(new Person());
            } catch (MaxSitsException | PassengerIsNotSupportedException | PassengerAlreadySeatingException e) {
                e.printStackTrace();
                System.out.println("Exception occurred in test2");
            }
        }
        System.out.println("Max seats: "+ vehicle.getTotalSeats());
        System.out.println("Taken seats: "+ vehicle.getSeatsTakenNumber());
        System.out.println("Test 2 passed successfully.");
    }
    public void test3() {
        System.out.println("Test 3 started");
        var vehicle = new Bus(4);
        for (int i = 0; i < 5; i++) {
            try {
                vehicle.getIn(new Person());
            }
            catch (MaxSitsException e) {
                System.out.println("Test 3 passed successfully, MaxSitsException was thrown.");
                break;
            } catch (PassengerIsNotSupportedException | PassengerAlreadySeatingException e) {
                System.out.println("Wrong exception");
                e.printStackTrace();
            }
        }
    }
    public void test4() {
        System.out.println("Test 4 started");
        var person1 = new Person();
        var person2 = new Person();
        var vehicle = new Car();

        try {
            vehicle.getIn(person1);
            vehicle.getOut(person2);
        } catch (PassengerNotFoundException e) {
            System.out.println("Test 4 passed successfully, PassengerNotFoundException was thrown.");
        } catch (MaxSitsException | PassengerIsNotSupportedException | PassengerAlreadySeatingException e) {
            System.out.println("Wrong exception");
            e.printStackTrace();
        }
    }
    public void test5() {
        System.out.println("Test 5 started");
        Road<Car> road = new Road<>();
        int maxCars = (int) Math.floor(Math.random()*30);
        for (int i = 0; i < maxCars; i++) {
            road.addCarToRoad(getRandomCar());
        }
        System.out.println("Test 5, cars on road:" + road.carsInRoad.size());
        System.out.println("Test 5, people on road:" + road.getCountOfHumans());
        System.out.println("Test 5 passed successfully");
    }

    private Car getRandomCar() {
        var car = new Car((int) Math.floor(Math.random()*5+1));
        for (int i = 0; i < Math.floor(Math.random()*car.getTotalSeats()+1); i++) {
            try {
                car.getIn(new Person());
            } catch (MaxSitsException | PassengerIsNotSupportedException | PassengerAlreadySeatingException e) {
                e.printStackTrace();
            }
        }
        return car;
    }
}