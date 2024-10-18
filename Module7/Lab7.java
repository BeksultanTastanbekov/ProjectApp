// Интерфейс "Стратегия"
interface IShippingStrategy {
    double calculateShippingCost(double weight, double distance);
}

// Стандартная доставка
class StandardShippingStrategy implements IShippingStrategy {
    @Override
    public double calculateShippingCost(double weight, double distance) {
        return weight * 0.5 + distance * 0.1;
    }
}

// Экспресс-доставка
class ExpressShippingStrategy implements IShippingStrategy {
    @Override
    public double calculateShippingCost(double weight, double distance) {
        return (weight * 0.75 + distance * 0.2) + 10; // Дополнительная плата за скорость
    }
}

// Международная доставка
class InternationalShippingStrategy implements IShippingStrategy {
    @Override
    public double calculateShippingCost(double weight, double distance) {
        return weight * 1.0 + distance * 0.5 + 15; // Дополнительные сборы
    }
}

// Контекст, который использует стратегии доставки
class DeliveryContext {
    private IShippingStrategy shippingStrategy;

    // Метод для установки стратегии
    public void setShippingStrategy(IShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }

    // Метод для расчета стоимости доставки
    public double calculateCost(double weight, double distance) {
        if (shippingStrategy == null) {
            throw new IllegalStateException("Стратегия доставки не установлена.");
        }
        return shippingStrategy.calculateShippingCost(weight, distance);
    }
}

// Интерфейс "Наблюдатель"
interface IObserver {
    void update(float temperature);
}

// Интерфейс для субъекта
interface ISubject {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}

// Субъект - погодная станция
class WeatherStation implements ISubject {
    private java.util.List<IObserver> observers = new java.util.ArrayList<>();
    private float temperature;

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(temperature);
        }
    }

    // Метод для установки температуры
    public void setTemperature(float newTemperature) {
        System.out.println("Изменение температуры: " + newTemperature + "°C");
        this.temperature = newTemperature;
        notifyObservers();
    }
}

// Наблюдатель - дисплей погоды
class WeatherDisplay implements IObserver {
    private String name;

    public WeatherDisplay(String name) {
        this.name = name;
    }

    @Override
    public void update(float temperature) {
        System.out.println(name + " показывает новую температуру: " + temperature + "°C");
    }
}

public class Lab7 {
    public static void main(String[] args) {
        // Паттерн "Стратегия"
        DeliveryContext deliveryContext = new DeliveryContext();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Выберите тип доставки: 1 - Стандартная, 2 - Экспресс, 3 - Международная");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                deliveryContext.setShippingStrategy(new StandardShippingStrategy());
                break;
            case 2:
                deliveryContext.setShippingStrategy(new ExpressShippingStrategy());
                break;
            case 3:
                deliveryContext.setShippingStrategy(new InternationalShippingStrategy());
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        System.out.println("Введите вес посылки (кг):");
        double weight = scanner.nextDouble();
        System.out.println("Введите расстояние доставки (км):");
        double distance = scanner.nextDouble();

        double cost = deliveryContext.calculateCost(weight, distance);
        System.out.println("Стоимость доставки: " + cost);

        // Паттерн "Наблюдатель"
        WeatherStation weatherStation = new WeatherStation();

        // Создаем несколько наблюдателей
        WeatherDisplay mobileApp = new WeatherDisplay("Мобильное приложение");
        WeatherDisplay digitalBillboard = new WeatherDisplay("Электронное табло");

        // Регистрируем наблюдателей
        weatherStation.registerObserver(mobileApp);
        weatherStation.registerObserver(digitalBillboard);

        // Изменяем температуру, наблюдатели получают уведомления
        weatherStation.setTemperature(25.0f);
        weatherStation.setTemperature(30.0f);

        // Убираем одного наблюдателя и снова меняем температуру
        weatherStation.removeObserver(digitalBillboard);
        weatherStation.setTemperature(28.0f);

        scanner.close();
    }
}
