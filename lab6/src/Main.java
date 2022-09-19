import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Translator translator = new Translator();
        Program program = new Program(translator);
        translator.addPair("привет", "Hello");
        translator.addPair("пока", "Buy");
        translator.addPair("Кирилл", "Kirill");
        translator.addPair("Оля", "Olya");
        translator.addPair("ФИОТ", "FICT");
        translator.addPair("удачи", "good luck");
        program.start();
    }
}
class Program {
    private boolean working = true;
    private Scanner scanner;
    Translator translator;
    public void start() {
        while (working) {
            UserAction action = askAction();
            switch (action) {
                case Terminate -> terminate();
                case AddPair -> askAddWord();
                case Translate -> translate();
            }
        }
    }

    public void translate() {
        System.out.println("Enter a phrase you want to translate, pls don't enter [,.!] ");
        try {
            String phrase = scanner.nextLine();
            String translatedSentence = Arrays.stream(phrase.split(" "))
                    .map(x-> translator.translate(x))
                    .map(x-> {return x == null ? "*not supported*" : x;})
                    .collect(Collectors.joining(" "));
            System.out.println("Translated successfully.");
            System.out.println(translatedSentence);
        }
        catch (Throwable e) {
            System.out.println("Something went wrong, probably translation for some pair is missing.");
        }

    }

    public Program(Translator translator) {
        this.translator = translator;
        this.scanner = new Scanner(System.in);
    }
    private void askAddWord() {
        System.out.println("What word translation will be added? Pls, enter singe word.");
        String word = scanner.nextLine();
        System.out.println("Nice, enter it's translation.");
        String translation = scanner.nextLine();
        translator.addPair(word,translation);
        System.out.println( word+" - "+translation+ " pair was added successfully!");

    }
    private UserAction askAction() {
        System.out.println("What to do next? Input a number: 0 - add pair, 1- translate sentence, 2 - terminate");
        String input = scanner.nextLine();
        switch (input) {
            case "0":
                return UserAction.AddPair;
            case "1":
                return UserAction.Translate;
            case "2":
                return UserAction.Terminate;
            default:
                System.out.println("Wrong input, try again....");
                return  askAction();
        }
    }

    public void terminate() {
        working = false;
    }
    enum UserAction {
        AddPair,
        Translate,
        Terminate
    }
}

class Translator {
    HashMap<String, String > dictionary;

    public Translator() {
        dictionary = new HashMap<String, String>();
    }

    public void addPair(String word, String translation) {
        dictionary.put(word, translation);
    }
    public String translate(String word) {
        return dictionary.get(word);
    }
}
