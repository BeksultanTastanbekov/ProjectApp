import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

// Уровни логирования
enum LogLevel {
    INFO, WARNING, ERROR
}

// Паттерн "Одиночка" для логгера
class Logger {
    private static Logger instance;
    private static Object lock = new Object();
    private LogLevel currentLogLevel;
    private String logFilePath;

    // Приватный конструктор
    private Logger() {
        currentLogLevel = LogLevel.INFO;
        logFilePath = "log.txt"; // Путь к файлу логов по умолчанию
    }

    // Метод для получения единственного экземпляра
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // Метод для изменения уровня логирования
    public void setLogLevel(LogLevel level) {
        currentLogLevel = level;
    }

    // Метод для установки пути к файлу логов
    public void setLogFilePath(String path) {
        logFilePath = path;
    }

    // Метод для логирования сообщений
    public void log(String message, LogLevel level) throws IOException {
        if (level.ordinal() >= currentLogLevel.ordinal()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));
            writer.write(new Date() + " [" + level + "] " + message);
            writer.newLine();
            writer.close();
        }
    }
}

// Класс продукта - Компьютер
class Computer {
    private String CPU;
    private String RAM;
    private String storage;
    private String GPU;
    private String OS;

    public void setCPU(String CPU) { this.CPU = CPU; }
    public void setRAM(String RAM) { this.RAM = RAM; }
    public void setStorage(String storage) { this.storage = storage; }
    public void setGPU(String GPU) { this.GPU = GPU; }
    public void setOS(String OS) { this.OS = OS; }

    @Override
    public String toString() {
        return "Компьютер: CPU - " + CPU + ", RAM - " + RAM + ", Хранилище - " + storage + ", GPU - " + GPU + ", ОС - " + OS;
    }
}

// Интерфейс строителя для компьютеров
interface IComputerBuilder {
    void setCPU();
    void setRAM();
    void setStorage();
    void setGPU();
    void setOS();
    Computer getComputer();
}

// Строитель офисных компьютеров
class OfficeComputerBuilder implements IComputerBuilder {
    private Computer computer = new Computer();

    public void setCPU() { computer.setCPU("Intel i3"); }
    public void setRAM() { computer.setRAM("8GB"); }
    public void setStorage() { computer.setStorage("1TB HDD"); }
    public void setGPU() { computer.setGPU("Integrated"); }
    public void setOS() { computer.setOS("Windows 10"); }

    public Computer getComputer() { return computer; }
}

// Строитель игровых компьютеров
class GamingComputerBuilder implements IComputerBuilder {
    private Computer computer = new Computer();

    public void setCPU() { computer.setCPU("Intel i9"); }
    public void setRAM() { computer.setRAM("32GB"); }
    public void setStorage() { computer.setStorage("1TB SSD"); }
    public void setGPU() { computer.setGPU("NVIDIA RTX 3080"); }
    public void setOS() { computer.setOS("Windows 11"); }

    public Computer getComputer() { return computer; }
}

// Директор для управления процессом сборки компьютеров
class ComputerDirector {
    private IComputerBuilder builder;

    public ComputerDirector(IComputerBuilder builder) {
        this.builder = builder;
    }

    public void constructComputer() {
        builder.setCPU();
        builder.setRAM();
        builder.setStorage();
        builder.setGPU();
        builder.setOS();
    }

    public Computer getComputer() {
        return builder.getComputer();
    }
}
// Интерфейс клонирования
interface IPrototype {
    IPrototype clone();
}

// Класс для секций документа
class Section implements IPrototype {
    private String title;
    private String content;

    public Section(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }

    @Override
    public IPrototype clone() {
        return new Section(title, content);
    }

    @Override
    public String toString() {
        return "Раздел: " + title + "\nСодержание: " + content;
    }
}

// Класс для изображений в документе
class Image implements IPrototype {
    private String url;

    public Image(String url) {
        this.url = url;
    }

    public void setURL(String url) { this.url = url; }

    @Override
    public IPrototype clone() {
        return new Image(url);
    }

    @Override
    public String toString() {
        return "Изображение: " + url;
    }
}

// Класс для документа
class Document implements IPrototype {
    private String title;
    private String content;
    private List<Section> sections;
    private List<Image> images;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
        this.sections = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public IPrototype clone() {
        Document cloneDoc = new Document(title, content);
        for (Section section : sections) {
            cloneDoc.addSection((Section) section.clone());
        }
        for (Image image : images) {
            cloneDoc.addImage((Image) image.clone());
        }
        return cloneDoc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Документ: ").append(title).append("\nСодержание: ").append(content).append("\n");
        for (Section section : sections) {
            sb.append(section).append("\n");
        }
        for (Image image : images) {
            sb.append(image).append("\n");
        }
        return sb.toString();
    }
}
public class Lab6 {
    public static void main(String[] args) throws IOException {
        // Тестирование паттерна Singleton (логгер)
        Logger logger = Logger.getInstance();
        logger.setLogLevel(LogLevel.INFO);
        logger.log("Программа запущена", LogLevel.INFO);

        // Тестирование паттерна Builder (сборка компьютеров)
        IComputerBuilder officeBuilder = new OfficeComputerBuilder();
        ComputerDirector director = new ComputerDirector(officeBuilder);
        director.constructComputer();
        Computer officeComputer = director.getComputer();
        System.out.println("\nОфисный компьютер:\n" + officeComputer);

        IComputerBuilder gamingBuilder = new GamingComputerBuilder();
        director = new ComputerDirector(gamingBuilder);
        director.constructComputer();
        Computer gamingComputer = director.getComputer();
        System.out.println("\nИгровой компьютер:\n" + gamingComputer);

        // Тестирование паттерна Prototype (клонирование документов)
        Document originalDoc = new Document("Технический отчет", "Это основной текст отчета.");
        originalDoc.addSection(new Section("Введение", "Введение в отчет."));
        originalDoc.addImage(new Image("https://example.com/image1.png"));

        Document clonedDoc = (Document) originalDoc.clone();
        clonedDoc.addSection(new Section("Заключение", "Это заключение клонированного отчета."));

        System.out.println("\nОригинальный документ:\n" + originalDoc);
        System.out.println("\nКлонированный документ:\n" + clonedDoc);

        // Логирование завершения программы
        logger.log("Программа завершена", LogLevel.INFO);
    }
}
