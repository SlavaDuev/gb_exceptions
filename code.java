import java.io.FileWriter;
import java.io.IOException;

class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            String inputData = "Иванов Иван Иванович 01.03.2000 1234567890 m";
            processData(inputData);
        } catch (InvalidDataFormatException e) {
            System.out.println("Ошибка формата данных: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processData(String inputData) throws InvalidDataFormatException, IOException {
        String[] parts = inputData.split(" ");
        if (parts.length != 6) {
            throw new InvalidDataFormatException("Неверное количество данных");
        }

        String surname = parts[0];
        String name = parts[1];
        String patronymic = parts[2];
        String dob = parts[3];
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(parts[4]);
        } catch (NumberFormatException e) {
            throw new InvalidDataFormatException("Неверный формат номера телефона");
        }

        char gender = parts[5].charAt(0);
        if (gender != 'f' && gender != 'm') {
            throw new InvalidDataFormatException("Неверный формат пола");
        }

        // Запись в файл
        String fileName = surname + ".txt";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(String.format("%s %s %s %s %d %c\n", surname, name, patronymic, dob, phoneNumber, gender));
        } catch (IOException e) {
            throw new IOException("Ошибка записи в файл " + fileName);
        }
    }
}
