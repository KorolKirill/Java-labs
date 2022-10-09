import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        View view = new LabView();
        LabModel labModel = new LabModel();
        LabController controller = new LabController(view, labModel);
        controller.lab5();
//        controller.lab5();
//        WorkingWithFiles workingWithFiles = new WorkingWithFiles();
//        System.out.println(workingWithFiles.cypherPhrase("abcde"));
   //     var a =    labModel.grabWebTags("https://javarush.ru/groups/posts/1963-kak-ispoljhzovatjh-klass-enum");
//        var a =    labModel.grabWebTags("https://cpto.dp.ua/public_html/posibnyky/basic_html/urok2.html");
////        a.forEach((s, integer) -> System.out.println(s + " " + integer));
//        a.entrySet().stream()
//                .sorted(Comparator.comparing(Map.Entry::getValue))
//                .forEach((x) -> System.out.println(x.getKey()+" - " + x.getValue()));

    }
}

class LabController {
    private View view;
    private LabModel model;

    public LabController(View view, LabModel model) {
        this.view = view;
        this.model = model;
    }


    public void lab5() {
        view.showMessage("Welcome to lab5");
        boolean exit = false;
        while (!exit) {
            view.showMessage("What you want to do next?");
            view.showMessage("Enter your choose:");
            view.showMessage("0 - Exit, 1 - Files, 2 - Web, 3 -Shapes, 4- decypher");
            int choose = view.askNumber();
            switch (choose) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    workWithFiles();
                    break;
                case 2:
                    workWithWeb();
                    break;
                case 3:
                    workWithShapes();
                    break;
                case 4:
                    decode();
                    break;
                default:
                    view.showMessage("Wrong input.");
                    break;
            }
        }
    }

    private void workWithShapes() {
        view.showMessage("Enter a path to the file.");
        String filePath =  view.askString();
        boolean canBeProceed =  model.verifyFile(filePath);
        if (!canBeProceed) {
            view.showMessage("Something went wrong with the file....");
            return;
        }
        view.showMessage("Writing 5 shapes...");
        var newShapes =  model.generateShapes(5);
        model.saveShapes(newShapes, filePath);

        view.showMessage("Reading shapes from file:");
        var shapesInFile = model.getShapesFromFile(filePath);
        shapesInFile.forEach(System.out::println);
    }

    private void workWithWeb() {
        view.showMessage("Enter a web link");
        String webLink = view.askString();
        var map = model.grabWebTags(webLink);
        view.showMessage("");
        view.showMessage("");
        view.showMessage("******** TASK - 1 *************");
        view.showMessage("");
        view.showMessage("");
        map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach((x) -> System.out.println(x.getKey()+" - " + x.getValue()));

        view.showMessage("");
        view.showMessage("");
        view.showMessage("******** TASK - 2 *************");
        view.showMessage("");
        view.showMessage("");
        map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach((x) -> System.out.println(x.getKey()+" - " + x.getValue()));
    }

    private void decode() {
        view.showMessage("Enter a string to decode.");
        var str = view.askString();
        var decoded =  model.decodeString(str);
        view.showMessage("String was decoded.");
        view.showMessage(decoded);
    }


    private void workWithFiles() {
        view.showMessage("Enter a path to the file.");
        String filePath =  view.askString();
       boolean canBeProceed =  model.verifyFile(filePath);
       if (!canBeProceed) {
           view.showMessage("Something went wrong with the file....");
           return;
       }
        view.showMessage("What do you want to do with the file?");
        view.showMessage("0 - Get max length row");
        view.showMessage("1 - open with Cypher");
        int userChoose = view.askNumber();
        switch (userChoose) {
            case 0:
                var row = model.maxLengthRowFromFile(filePath);
                view.showMessage("Max row is length is: " + row.length());
                view.showMessage(row);
                break;
            case 1:
                var text = model.openFileWithCoder(filePath);
                view.showMessage("Opened with Cypher");
                view.showMessage(text);
                break;
            default:
                view.showMessage("Wrong input, returning.");
        }
    }

    public void completeLab3() {
        model.generateShapes(15);
        //відображає набір даних;
        view.showData(model.getShapes()
                .stream()
                .map(x->x.toString())
                .toList());
//
//        //обчислює сумарну площу всіх фігур набору даних;
//        var totalArea =  model.getTotalArea();
//        view.showMessage("Total area is: " + totalArea);
//        //обчислює сумарну площу фігур заданого виду;
        var totalAreaFiltered = model.totalAreaByType(Triangle.class);
        view.showMessage("Total area filtered by Triangle class: " + totalAreaFiltered);
//        //впорядковує набір даних щодо збільшення площі фігур, використовуючи об'єкт інтерфейсу Comparator;
//        model.sortByArea();
//        view.showData(model.getShapes().stream().map(x->x.toString()).toList());
//        //впорядковує набір даних за кольором фігур, використовуючи об'єкт інтерфейсу Comparator.
//        model.sortByColor();
//        view.showData(model.getShapes().stream().map(x->x.toString()).toList());
//
//        // completed
        view.showMessage("Lab is completed.");
    }
}

interface View {
    void showMessage(String message);
    void showData(Iterable<String> iterable);
    String askString();
    int askNumber();
}

class LabModel {
    List<Shape> randomShapes;

    public List<Shape> getShapesFromFile(String filePath) {
        WorkingWithFiles workingWithFiles = new WorkingWithFiles();
        try {
            var list = workingWithFiles.getShapesSerialized(filePath);
            return list;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Map<String, Integer> grabWebTags(String urlAddress) {
        var dictionary = new HashMap<String, Integer>();
        try {
            URL url = new URL(urlAddress);
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
            String string = reader.readLine();
            while (string != null) {

//                System.out.println(string);

                var lastTag = new StringBuilder();
                boolean writing = false;
                for (char chr : string.toCharArray()) {
                    if (chr == '/' || chr == '>' || chr == ' ') {
                        writing = false;
                        var value = dictionary.get(lastTag.toString());
                        if (value == null) {
                            dictionary.put(lastTag.toString(),1);
                        }
                        else {
                            dictionary.put(lastTag.toString(),++value);
                        }
                        lastTag = new StringBuilder();
                    }
                    if (writing) {
                        lastTag.append(chr);
                    }
                    if (chr == '<')
                    {
                        writing = true;
                    }

                }

                string = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't connect");
        }
        dictionary.remove(" ");
        dictionary.remove("");
       return dictionary;
    }

    public String decodeString(String str) {
        WorkingWithFiles workingWithFiles = new WorkingWithFiles();
        return workingWithFiles.deCypherPhrase(str);
    }

    public boolean verifyFile(String filePath) {
        try {
            WorkingWithFiles workingWithFiles = new WorkingWithFiles();
            workingWithFiles.getFile(filePath);
            return true;
        }
        catch (CustomException exception) {
            return false;
        }
    }

    public String openFileWithCoder(String filePath){
        File file = new File(filePath);
        try {
            MySecretReader reader = new MySecretReader(new BufferedReader(new FileReader(file)));
            var stringBuilder = new StringBuilder();
            int chrN;
            while ((chrN = reader.read() ) != -1) {
                stringBuilder.append((char) chrN);
            }
            return stringBuilder.toString();
        }
        catch (Throwable throwable) {
          return  "";
        }

    }

    public String maxLengthRowFromFile(String filePath) {
        File file = new File(filePath);
        try
        {
            var reader =  new BufferedReader(new FileReader(file));
            final String[] result = {""};
            reader.lines()
                    .forEach(line-> {
                        if (line.length() > result[0].length()) {
                            result[0] = line;
                        }
                    });
            return result[0];
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

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
                .filter(x-> x.getClass() == type)
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

    public void saveShapes(List<Shape> newShapes, String filePath) {
        WorkingWithFiles workingWithFiles = new WorkingWithFiles();
        workingWithFiles.saveShapesToFile(newShapes, filePath);
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


class LabView implements View {
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showData(Iterable<String> iterable) {
        iterable.forEach(System.out::println);
    }

    public String askString() {
        showMessage("Pls enter a string.");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int askNumber() {
        showMessage("Pls enter a number.");
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.nextLine());
        }
        catch (Throwable e) {
            showMessage("Input is not correct, try again...");
            return askNumber();
        }
    }
}