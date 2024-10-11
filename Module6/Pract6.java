import java.io.*;
import java.util.Date;
import java.util.*;

// Уровни логирования
enum LogLevel {
    INFO,
    WARNING,
    ERROR
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

    // Метод для установки уровня логирования
    public void setLogLevel(LogLevel level) {
        currentLogLevel = level;
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

    // Метод для чтения настроек логгера из файла
    public void loadConfiguration(String configFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts[0].equals("LogLevel")) {
                setLogLevel(LogLevel.valueOf(parts[1]));
            } else if (parts[0].equals("LogFilePath")) {
                logFilePath = parts[1];
            }
        }
        reader.close();
    }
}

// Чтение логов с фильтрацией
class LogReader {
    private String logFilePath;

    public LogReader(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    // Метод для чтения и фильтрации логов по уровню
    public void readLogs(LogLevel filterLevel) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("[" + filterLevel + "]")) {
                System.out.println(line);
            }
        }
        reader.close();
    }
}

// Интерфейс строителя отчетов
interface IReportBuilder {
    IReportBuilder setHeader(String header);
    IReportBuilder setContent(String content);
    IReportBuilder setFooter(String footer);
    IReportBuilder addSection(String sectionName, String sectionContent);
    IReportBuilder setStyle(ReportStyle style);
    Report getReport();
}

// Класс для хранения стилей отчета
class ReportStyle {
    private String backgroundColor;
    private String fontColor;
    private int fontSize;

    public ReportStyle(String backgroundColor, String fontColor, int fontSize) {
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public int getFontSize() {
        return fontSize;
    }
}

// Класс отчета
class Report {
    private String header;
    private String content;
    private String footer;
    private List<String> sections;
    private ReportStyle style;

    public Report() {
        sections = new ArrayList<>();
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void addSection(String sectionName, String sectionContent) {
        sections.add(sectionName + ": " + sectionContent);
    }

    public void setStyle(ReportStyle style) {
        this.style = style;
    }

    @Override
    public String toString() {
        StringBuilder reportString = new StringBuilder();
        reportString.append("Отчет (стиль: фон ")
                .append(style.getBackgroundColor())
                .append(", цвет текста ")
                .append(style.getFontColor())
                .append(", размер шрифта ")
                .append(style.getFontSize())
                .append(")\n")
                .append("Заголовок: ").append(header).append("\n")
                .append("Содержание: ").append(content).append("\n");

        for (String section : sections) {
            reportString.append("Раздел: ").append(section).append("\n");
        }

        reportString.append("Подвал: ").append(footer).append("\n");
        return reportString.toString();
    }
}

// Реализация строителя текстовых отчетов
class TextReportBuilder implements IReportBuilder {
    private Report report;

    public TextReportBuilder() {
        this.report = new Report();
    }

    public IReportBuilder setHeader(String header) {
        report.setHeader(header);
        return this;
    }

    public IReportBuilder setContent(String content) {
        report.setContent(content);
        return this;
    }

    public IReportBuilder setFooter(String footer) {
        report.setFooter(footer);
        return this;
    }

    public IReportBuilder addSection(String sectionName, String sectionContent) {
        report.addSection(sectionName, sectionContent);
        return this;
    }

    public IReportBuilder setStyle(ReportStyle style) {
        report.setStyle(style);
        return this;
    }

    public Report getReport() {
        return report;
    }
}

// Реализация строителя HTML отчетов
class HtmlReportBuilder implements IReportBuilder {
    private Report report;

    public HtmlReportBuilder() {
        this.report = new Report();
    }

    public IReportBuilder setHeader(String header) {
        report.setHeader("<h1>" + header + "</h1>");
        return this;
    }

    public IReportBuilder setContent(String content) {
        report.setContent("<p>" + content + "</p>");
        return this;
    }

    public IReportBuilder setFooter(String footer) {
        report.setFooter("<footer>" + footer + "</footer>");
        return this;
    }

    public IReportBuilder addSection(String sectionName, String sectionContent) {
        report.addSection("<section><h2>" + sectionName + "</h2>", "<p>" + sectionContent + "</p>");
        return this;
    }

    public IReportBuilder setStyle(ReportStyle style) {
        report.setStyle(style);
        return this;
    }

    public Report getReport() {
        return report;
    }
}

// Класс директора для создания отчетов
class ReportDirector {
    public void constructReport(IReportBuilder builder, ReportStyle style) {
        builder.setHeader("Отчет за октябрь")
                .setContent("Общие результаты работы за месяц.")
                .addSection("Продажи", "Увеличение продаж на 15%")
                .addSection("Производство", "Снижение затрат на 5%")
                .setFooter("Конец отчета")
                .setStyle(style);
    }
}

// Интерфейс для клонирования объектов
interface Cloneable<T> {
    T clone();
}

// Класс оружия
class Weapon implements Cloneable<Weapon> {
    private String name;
    private int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public Weapon clone() {
        return new Weapon(this.name, this.damage);
    }

    @Override
    public String toString() {
        return "Оружие: " + name + ", Урон: " + damage;
    }
}

// Класс брони
class Armor implements Cloneable<Armor> {
    private String name;
    private int defense;

    public Armor(String name, int defense) {
        this.name = name;
        this.defense = defense;
    }

    public Armor clone() {
        return new Armor(this.name, this.defense);
    }

    @Override
    public String toString() {
        return "Броня: " + name + ", Защита: " + defense;
    }
}

// Класс навыков
class Skill implements Cloneable<Skill> {
    private String name;
    private String type;

    public Skill(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Skill clone() {
        return new Skill(this.name, this.type);
    }

    @Override
    public String toString() {
        return "Навык: " + name + ", Тип: " + type;
    }
}

// Класс персонажа
class Character implements Cloneable<Character> {
    private String name;
    private int health;
    private int strength;
    private int agility;
    private int intelligence;
    private Weapon weapon;
    private Armor armor;
    private List<Skill> skills;

    public Character(String name, int health, int strength, int agility, int intelligence, Weapon weapon, Armor armor) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.weapon = weapon;
        this.armor = armor;
        this.skills = new ArrayList<>();
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    @Override
    public Character clone() {
        Character clonedCharacter = new Character(this.name, this.health, this.strength, this.agility, this.intelligence, this.weapon.clone(), this.armor.clone());
        for (Skill skill : this.skills) {
            clonedCharacter.addSkill(skill.clone());
        }
        return clonedCharacter;
    }

    @Override
    public String toString() {
        return "Персонаж: " + name + "\n" +
                "Здоровье: " + health + ", Сила: " + strength + ", Ловкость: " + agility + ", Интеллект: " + intelligence + "\n" +
                weapon + "\n" +
                armor + "\n" +
                "Навыки: " + skills;
    }
}

public class Pract6 {
    public static void main(String[] args) throws Exception {
        // Тестирование паттерна Singleton
        Logger logger = Logger.getInstance();
        logger.setLogLevel(LogLevel.INFO);
        logger.log("Запуск программы", LogLevel.INFO);

        // Тестирование паттерна Builder
        ReportDirector director = new ReportDirector();
        IReportBuilder textBuilder = new TextReportBuilder();
        IReportBuilder htmlBuilder = new HtmlReportBuilder();
        ReportStyle style = new ReportStyle("Белый", "Черный", 12);

        // Текстовый отчет
        director.constructReport(textBuilder, style);
        Report textReport = textBuilder.getReport();
        System.out.println("\nТекстовый отчет:");
        System.out.println(textReport);

        // HTML отчет
        director.constructReport(htmlBuilder, style);
        Report htmlReport = htmlBuilder.getReport();
        System.out.println("\nHTML отчет:");
        System.out.println(htmlReport);

        // Тестирование паттерна Prototype
        Weapon sword = new Weapon("Меч", 50);
        Armor shield = new Armor("Щит", 30);
        Character warrior = new Character("Воин", 100, 80, 50, 30, sword, shield);
        warrior.addSkill(new Skill("Удар мечом", "Физический"));
        warrior.addSkill(new Skill("Блок щитом", "Защитный"));

        System.out.println("\nОригинальный персонаж:");
        System.out.println(warrior);

        // Клонирование персонажа
        Character clonedWarrior = warrior.clone();
        System.out.println("\nКлонированный персонаж:");
        System.out.println(clonedWarrior);

        // Логирование завершения
        logger.log("Программа завершена", LogLevel.INFO);
    }
}
