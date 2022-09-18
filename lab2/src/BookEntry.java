import java.sql.Date;
import java.util.Locale;

public class BookEntry {
    public String studentName;
    public String studentSurname;
    public Date birthDate;
    public String phoneNumber;
    public Address homeAddress ;

    @Override
    public String toString() {
        return "BookEntry{" +
                "studentName='" + studentName + '\'' +
                ", studentSurname='" + studentSurname + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", homeAddress=" + homeAddress +
                '}';
    }

    public BookEntry(String studentName, String studentSurname, Date birthDate, String phoneNumber, Address address) {
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.birthDate = birthDate;
        this.homeAddress = address;
        if (phoneNumber.contains("+")) {
            this.phoneNumber = phoneNumber;
        }
        else {
            this.phoneNumber = "+" + phoneNumber;
        }
    }

    static boolean isValidSurname(String studentName) {
        if (studentName.length() > 20) {
            return false;
        }
        if (studentName.toLowerCase(Locale.ROOT).equals(studentName)) {
            return false;
        }
        return true;
    }

    static boolean isValidPhone(String phoneNumber) {
        if (phoneNumber.contains("+")) {
            phoneNumber = phoneNumber.substring(1);
        }
        for (var number:
                phoneNumber.toCharArray()) {
            if (number < '0' || number > '9') {
                return false;
            }
        }
        return true;
    }

    static boolean isValidName(String studentName) {
        if (studentName.length() > 20) {
            return false;
        }
        if (studentName.toLowerCase(Locale.ROOT).equals(studentName)) {
            return false;
        }
        return true;
    }

    static boolean isValidDate(int year, int month, int day) {
        if (year < 0  || month < 0 || day < 0) {
            return false;
        }
        return true;
    }
    static boolean isValidAddress(Address address) {
        return true;
    }
}

class Address {
    public Address(String street, int house, int room) {
        this.street = street;
        this.house = house;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", house=" + house +
                ", room=" + room +
                '}';
    }

    String street;
    int house;
    int room;
}