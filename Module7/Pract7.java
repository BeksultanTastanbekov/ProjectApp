import java.util.ArrayList;
import java.util.List;

// Интерфейс стратегии
interface ICostCalculationStrategy {
    double calculateCost(double distance, String serviceClass, int passengers, boolean hasDiscount);
}

// Стратегия для самолета
class AirplaneCostStrategy implements ICostCalculationStrategy {
    public double calculateCost(double distance, String serviceClass, int passengers, boolean hasDiscount) {
        double basePrice = 0.1 * distance;
        if (serviceClass.equalsIgnoreCase("business")) basePrice *= 1.5;
        if (hasDiscount) basePrice *= 0.8;
        return basePrice * passengers;
    }
}

// Стратегия для поезда
class TrainCostStrategy implements ICostCalculationStrategy {
    public double calculateCost(double distance, String serviceClass, int passengers, boolean hasDiscount) {
        double basePrice = 0.05 * distance;
        if (serviceClass.equalsIgnoreCase("business")) basePrice *= 1.2;
        if (hasDiscount) basePrice *= 0.85;
        return basePrice * passengers;
    }
}

// Стратегия для автобуса
class BusCostStrategy implements ICostCalculationStrategy {
    public double calculateCost(double distance, String serviceClass, int passengers, boolean hasDiscount) {
        double basePrice = 0.03 * distance;
        if (serviceClass.equalsIgnoreCase("business")) basePrice *= 1.1;
        if (hasDiscount) basePrice *= 0.9;
        return basePrice * passengers;
    }
}

// Класс контекста
class TravelBookingContext {
    private ICostCalculationStrategy strategy;

    public void setStrategy(ICostCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTripCost(double distance, String serviceClass, int passengers, boolean hasDiscount) {
        return strategy.calculateCost(distance, serviceClass, passengers, hasDiscount);
    }
}

// Интерфейс наблюдателя
interface IObserver {
    void update(String stockName, double newPrice);
}

// Интерфейс субъекта
interface ISubject {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers(String stockName, double newPrice);
}

// Класс биржи
class StockExchange implements ISubject {
    private List<IObserver> observers = new ArrayList<>();
    private double stockPrice;

    public void setStockPrice(String stockName, double newPrice) {
        this.stockPrice = newPrice;
        notifyObservers(stockName, newPrice);
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String stockName, double newPrice) {
        for (IObserver observer : observers) {
            observer.update(stockName, newPrice);
        }
    }
}

// Класс трейдера
class Trader implements IObserver {
    private String name;

    public Trader(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println(name + " получил уведомление: Цена акции " + stockName + " изменилась на " + newPrice);
    }
}

// Класс торгового робота
class TradingRobot implements IObserver {
    @Override
    public void update(String stockName, double newPrice) {
        if (newPrice > 100) {
            System.out.println("Робот: Акция " + stockName + " превышает 100. Покупка!");
        } else {
            System.out.println("Робот: Акция " + stockName + " ниже 100. Продажа!");
        }
    }
}

public class Pract7 {
    public static void main(String[] args) {
        // Стратегия
        runTravelBookingSystem();

        // Наблюдатель
        runStockExchangeSystem();
    }

    // Метод для (TravelBookingSystem)
    public static void runTravelBookingSystem() {
        TravelBookingContext context = new TravelBookingContext();

        // Выбор типа транспорта и расчета
        context.setStrategy(new AirplaneCostStrategy());
        System.out.println("Airplane cost: " + context.calculateTripCost(1000, "economy", 2, true));

        context.setStrategy(new TrainCostStrategy());
        System.out.println("Train cost: " + context.calculateTripCost(1000, "business", 3, false));

        context.setStrategy(new BusCostStrategy());
        System.out.println("Bus cost: " + context.calculateTripCost(1000, "economy", 1, true));
    }

    // Метод для (StockExchangeSystem)
    public static void runStockExchangeSystem() {
        StockExchange stockExchange = new StockExchange();

        // Создаем наблюдателей
        Trader trader1 = new Trader("Трейдер Арман");
        TradingRobot robot = new TradingRobot();

        // Регистрируем наблюдателей
        stockExchange.registerObserver(trader1);
        stockExchange.registerObserver(robot);

        // Изменяем цены акций
        stockExchange.setStockPrice("APPLE", 120);
        stockExchange.setStockPrice("GOOGLE", 90);
    }
}
