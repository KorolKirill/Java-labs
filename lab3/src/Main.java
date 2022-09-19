import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> randomShapes = Generator.generateRandomShapes(10);

        //відображає набір даних;
        randomShapes.forEach(System.out::println);
        //обчислює сумарну площу всіх фігур набору даних;
        double totalArea = randomShapes.stream()
                .map(x-> x.calcArea())
                .reduce(0d,((a, b) -> a + b ));
        System.out.println(totalArea);
        // обчислює сумарну площу фігур заданого виду;
        double totalAreaByType = randomShapes.stream()
                .filter(x-> x.getClass() == Circle.class)
                .map(Shape::calcArea)
                .reduce(0d,((a, b) -> a + b ));
        System.out.println(totalAreaByType);
        //впорядковує набір даних щодо збільшення площі фігур, використовуючи об'єкт інтерфейсу Comparator;
        Comparator<Shape> compareByArea = (x,y) -> (x.calcArea() > y.calcArea()) ? 1 : 0;
        randomShapes.sort(compareByArea);
        System.out.println(randomShapes);
        // впорядковує набір даних за кольором фігур, використовуючи об'єкт інтерфейсу Comparator.
        Comparator<Shape> compareByColors = (x,y) -> x.shapeColor.compareTo(y.shapeColor);
        randomShapes.sort(compareByColors);
        System.out.println(randomShapes);
    }
}

class Generator {
    static String[] colors = new String[] {"red", "blue", "white", "purple", "black"};
    static List<Shape> generateRandomShapes(int amount) {
        List<Shape> randomShapes = new ArrayList<>();
        for (int i = 0; i< amount; i ++) {
            int random = (int) Math.floor(Math.random()*3);
            String color = colors[ (int) Math.floor(Math.random()*colors.length)];
            switch (random) {
                case 1:
                    randomShapes.add(new Rectangle(color, Math.random()*10, Math.random()*10));
                    break;
                case 2:
                    double sideA = Math.random()*10;
                    randomShapes.add(new Triangle(color, sideA,sideA*2, sideA*1.5));
                    break;
                case 0:
                    randomShapes.add(new Circle(color, Math.random()*10));
                    break;
            }
        }
        return randomShapes;
    }
}

