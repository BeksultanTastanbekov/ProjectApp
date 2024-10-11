import java.util.Scanner;

interface Document {
    void open();
}

class Report implements Document {
    @Override
    public void open() {
        System.out.println("Открытие отчета...");
    }
}

class Resume implements Document {
    @Override
    public void open() {
        System.out.println("Открытие резюме...");
    }
}

class Letter implements Document {
    @Override
    public void open() {
        System.out.println("Открытие письма...");
    }
}

class Invoice implements Document {
    @Override
    public void open() {
        System.out.println("Открытие счета...");
    }
}

abstract class DocumentCreator {
    public abstract Document createDocument();
}

class ReportCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Report();
    }
}

class ResumeCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Resume();
    }
}

class LetterCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Letter();
    }
}

class InvoiceCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Invoice();
    }
}

public class Pract4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип документа: Report, Resume, Letter, Invoice");
        String documentType = scanner.nextLine();

        DocumentCreator creator = null;

        switch (documentType.toLowerCase()) {
            case "report":
                creator = new ReportCreator();
                break;
            case "resume":
                creator = new ResumeCreator();
                break;
            case "letter":
                creator = new LetterCreator();
                break;
            case "invoice":
                creator = new InvoiceCreator();
                break;
            default:
                System.out.println("Неверный тип документа.");
                break;
        }

        // Если фабрика создана, вызываем метод для открытия документа
        if (creator != null) {
            Document document = creator.createDocument();
            document.open();
        }
    }
}
