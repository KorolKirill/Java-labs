import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        program.start();
    }
}

class Program {
    private boolean working;
    public Journal journal;
    private Scanner scanner;

    public Program() {
        this.working = true;
        this.journal = new Journal();
        this.scanner = new Scanner(System.in);
    }
    private enum Action {
        AddEntry,
        AllEntries,
        Terminate
    }

    public void start() {
        while (working) {
            Action userNextAction = askForInput();
            switch (userNextAction) {
                case AddEntry:
                    AddEntry();
                    break;
                case AllEntries:
                    showAllEntries();
                    break;
                case Terminate:
                    terminate();
                    break;
            }
        }
    }

    private void AddEntry() {
        String studentName = askName();
        String studentSurname = askSurname();
        String phoneNumber = askPhoneNumber();
        Date date = askBirthDate();
        Address address = askAddress();
        journal.addEntry(new BookEntry(studentName,studentSurname,date, phoneNumber, address));
    }

    private Address askAddress() {
        try {
            System.out.println("What's the street?");
            String street = scanner.nextLine();
            System.out.println("What's the house number?  Enter number.");
            int house = Integer.parseInt(scanner.nextLine());
            System.out.println("What's the room? Enter number.");
            int room = Integer.parseInt(scanner.nextLine());
            Address address =  new Address(street,house, room);
            if (BookEntry.isValidAddress(address)) {
                return  address;
            }
            else {
                System.out.println("Address is not correct, try again...");
                return askAddress();
            }
        }
        catch (Exception e) {
            System.out.println("Address is not correct, try again...");
            return askAddress();
        }
    }

    private Date askBirthDate() {
        try {
            System.out.println("What's the year? Enter number.");
            int year = Integer.valueOf(scanner.nextLine());
            System.out.println("What's the month?  Enter number.");
            int month = Integer.valueOf(scanner.nextLine());
            System.out.println("What's the day? Enter number.");
            int day = Integer.valueOf(scanner.nextLine());
            return new Date(year, month, day);
        }
        catch (Exception e) {
            System.out.println("Birth is not correct, try again...");
            return askBirthDate();
        }
    }

    private String askPhoneNumber() {
        String number  ;
        boolean retry = false;
        do {
            retry = false;
            System.out.println("What the phone");
            number = scanner.nextLine();
            if (!BookEntry.isValidPhone(number)) {
                System.out.println("Phone is not valid, try again.");
                retry = true;
            }
        } while (retry);
        return number;
    }

    private String askSurname() {
        String surname  ;
        boolean retry = false;
        do {
            retry = false;
            System.out.println("What the student Surname");
            surname = scanner.nextLine();
            if (!BookEntry.isValidSurname(surname)) {
                System.out.println("Surname is not valid");
                retry = true;
            }
        } while (retry);
        return surname;
    }

    private String askName() {
        boolean retry = false;
        String name;
        do {
            retry = false;
            System.out.println("What the student Name");
            name = scanner.nextLine();
            if (!BookEntry.isValidName(name)) {
                System.out.println("Name is not valid, try again...");
                retry = true;
            }
        } while (retry);
        return name;
    }

    private void showAllEntries() {
        System.out.println("Printing list of entries");
        if (journal.getEntries().iterator().hasNext()) {
            journal.getEntries().forEach(System.out::println);
        }
        else {
            System.out.println("Empty list");
        }

    }

    private Action askForInput() {
        System.out.println("What's to do next, choose: 0 - add Entry, 1 - show all entries, 2 - finish");
        String input = scanner.nextLine();
        try {
            switch (input) {
                case "0":
                    return Action.AddEntry;
                case "1":
                    return Action.AllEntries;
                case "2":
                    return Action.Terminate;
            }
            return Action.Terminate;
        }
        catch (Exception e) {
            System.out.println("Wrong input, try again....");
            return askForInput();
        }
    }

    private void terminate() {
        working = false;
    }
}





