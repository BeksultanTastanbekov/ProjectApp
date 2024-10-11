import java.io.*;
import java.util.*;

// Паттерн "Одиночка" (Singleton)
class ConfigurationManager {
    private static ConfigurationManager instance;
    private static Object lock = new Object();
    private Map<String, String> settings;

    // Приватный конструктор
    private ConfigurationManager() {
        settings = new HashMap<>();
    }

    // Метод для получения единственного экземпляра
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    // Метод для загрузки настроек из файла
    public void loadFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                settings.put(parts[0], parts[1]);
            }
        }
        reader.close();
    }

    // Метод для получения настройки по ключу
    public String getSetting(String key) {
        return settings.getOrDefault(key, "Настройка не найдена");
    }

    // Метод для изменения настройки
    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    // Метод для сохранения настроек в файл
    public void saveToFile(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            writer.write(entry.getKey() + "=" + entry.getValue());
            writer.newLine();
        }
        writer.close();
    }
}

// Паттерн "Строитель" (Builder)
interface IReportBuilder {
    IReportBuilder setHeader(String header);
    IReportBuilder setContent(String content);
    IReportBuilder setFooter(String footer);
    Report getReport();
}

class Report {
    private String header;
    private String content;
    private String footer;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public String toString() {
        return header + "\n" + content + "\n" + footer;
    }
}

class TextReportBuilder implements IReportBuilder {
    private Report report;

    public TextReportBuilder() {
        this.report = new Report();
    }

    public IReportBuilder setHeader(String header) {
        report.setHeader("=== " + header + " ===");
        return this;
    }

    public IReportBuilder setContent(String content) {
        report.setContent(content);
        return this;
    }

    public IReportBuilder setFooter(String footer) {
        report.setFooter("--- " + footer + " ---");
        return this;
    }

    public Report getReport() {
        return report;
    }
}

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

    public Report getReport() {
        return report;
    }
}

class ReportDirector {
    public void constructReport(IReportBuilder builder) {
        builder.setHeader("Заголовок отчета")
                .setContent("Это содержимое отчета")
                .setFooter("Подвал отчета");
    }
}

// Паттерн "Прототип" (Prototype)
interface Cloneable<T> {
    T clone();
}

class Product implements Cloneable<Product> {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product clone() {
        return new Product(this.name, this.price);
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }
}

class Order implements Cloneable<Order> {
    private List<Product> products;
    private double deliveryCost;
    private double discount;
    private String paymentMethod;

    public Order() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void setDeliveryCost(double cost) {
        this.deliveryCost = cost;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPaymentMethod(String method) {
        this.paymentMethod = method;
    }

    public Order clone() {
        Order clonedOrder = new Order();
        for (Product product : products) {
            clonedOrder.addProduct(product.clone());
        }
        clonedOrder.setDeliveryCost(this.deliveryCost);
        clonedOrder.setDiscount(this.discount);
        clonedOrder.setPaymentMethod(this.paymentMethod);
        return clonedOrder;
    }

    @Override
    public String toString() {
        return "Заказ с товарами: " + products + "\nСтоимость доставки: " + deliveryCost + "\nСкидка: " + discount + "\nМетод оплаты: " + paymentMethod;
    }
}

public class HomeTask6 {
    public static void main(String[] args) throws Exception {
        // Тестирование паттерна Singleton
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.setSetting("URL", "http://example.com");
        config.saveToFile("config.txt");

        // Загружаем настройки из файла
        config.loadFromFile("config.txt");
        System.out.println("Singleton Настройка URL: " + config.getSetting("URL"));

        // Тестирование паттерна Builder
        ReportDirector director = new ReportDirector();
        IReportBuilder textBuilder = new TextReportBuilder();
        IReportBuilder htmlBuilder = new HtmlReportBuilder();

        director.constructReport(textBuilder);
        System.out.println("\nТекстовый отчет:");
        System.out.println(textBuilder.getReport());

        director.constructReport(htmlBuilder);
        System.out.println("\nHTML отчет:");
        System.out.println(htmlBuilder.getReport());

        // Тестирование паттерна Prototype
        Order order1 = new Order();
        order1.addProduct(new Product("Ноутбук", 1500));
        order1.addProduct(new Product("Мышь", 50));
        order1.setDeliveryCost(15);
        order1.setDiscount(10);
        order1.setPaymentMethod("Кредитная карта");

        Order clonedOrder = order1.clone();
        System.out.println("\nОригинальный заказ: " + order1);
        System.out.println("\nКлонированный заказ: " + clonedOrder);
    }
}
