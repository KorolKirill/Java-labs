import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        View view = new LabView();
        LabModel labModel = new LabModel();
        LabController controller = new LabController(view, labModel);
        controller.completeLab();
    }
}

class LabController {
    private View view;
    private LabModel model;

    public LabController(View view, LabModel model) {
        this.view = view;
        this.model = model;
    }

    public void completeLab() {
        model.generateShapes(15);
        //відображає набір даних;
        view.showData(model.getShapes()
                .stream()
                .map(x->x.toString())
                .toList());

        //обчислює сумарну площу всіх фігур набору даних;
        var totalArea =  model.getTotalArea();
        view.showMessage("Total area is: " + totalArea);
        //обчислює сумарну площу фігур заданого виду;
        var totalAreaFiltered = model.totalAreaByType(Circle.class);
        view.showMessage("Total area filtered by Circle class: " + totalAreaFiltered);
        //впорядковує набір даних щодо збільшення площі фігур, використовуючи об'єкт інтерфейсу Comparator;
        model.sortByArea();
        view.showData(model.getShapes().stream().map(x->x.toString()).toList());
        //впорядковує набір даних за кольором фігур, використовуючи об'єкт інтерфейсу Comparator.
        model.sortByColor();
        view.showData(model.getShapes().stream().map(x->x.toString()).toList());

        // completed
        view.showMessage("Lab is completed.");
    }
}

interface View {
    void showMessage(String message);
    void showData(Iterable<String> iterable);
}

class LabView implements View {
    public void showMessage(String message) {
        System.out.println("System");
    }

    @Override
    public void showData(Iterable<String> iterable) {
        iterable.forEach(System.out::println);
    }
}

class LabModel {
    List<Shape> randomShapes;

    public LabModel() {
        this.randomShapes = generateShapes(10);
    }

    public List<Shape> generateShapes(int amount) {
        return  Generator.generateRandomShapes(amount);
    }

    public void setShapesList(List<Shape> shapes) {
        this.randomShapes = shapes;
    }

    public void sortByArea() {
        //впорядковує набір даних щодо збільшення площі фігур, використовуючи об'єкт інтерфейсу Comparator;
        Comparator<Shape> compareByArea = (x,y) -> (x.calcArea() > y.calcArea()) ? 1 : 0;
        randomShapes.sort(compareByArea);
    }

    public void sortByColor() {
        // впорядковує набір даних за кольором фігур, використовуючи об'єкт інтерфейсу Comparator.
        Comparator<Shape> compareByColors = (x,y) -> x.shapeColor.compareTo(y.shapeColor);
        randomShapes.sort(compareByColors);
    }

    public double totalAreaByType(Type type) {
        // обчислює сумарну площу фігур заданого виду;
        double totalAreaByType = randomShapes.stream()
                .filter(x-> x.getClass() == Circle.class)
                .map(Shape::calcArea)
                .reduce(0d,((a, b) -> a + b ));
      return totalAreaByType;
    }

    public double getTotalArea() {
        //обчислює сумарну площу всіх фігур набору даних;
        double totalArea = randomShapes.stream()
                .map(x-> x.calcArea())
                .reduce(0d,((a, b) -> a + b ));
        return totalArea;
    }

    public List<Shape> getShapes() {
        return  randomShapes;
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
    static String getRandomColor() {
        return  colors[(int) Math.floor(Math.random()*colors.length)];
    }
}

