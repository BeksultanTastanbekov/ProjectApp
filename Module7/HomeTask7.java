import java.util.ArrayList;
import java.util.List;

// Интерфейс для паттерна Стратегия
interface IPaymentStrategy {
    void pay(double amount);
}

// Реализация стратегии для оплаты банковской картой
class CreditCardPaymentStrategy implements IPaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Оплачено " + amount + " с помощью банковской карты.");
    }
}

// Реализация стратегии для оплаты через PayPal
class PayPalPaymentStrategy implements IPaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Оплачено " + amount + " с помощью PayPal.");
    }
}

// Реализация стратегии для оплаты криптовалютой
class CryptoPaymentStrategy implements IPaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Оплачено " + amount + " с помощью криптовалюты.");
    }
}

// Контекст для работы с разными стратегиями оплаты
class PaymentContext {
    private IPaymentStrategy paymentStrategy;

    // Устанавливаем стратегию оплаты
    public void setPaymentStrategy(IPaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Выполняем оплату с выбранной стратегией
    public void executePayment(double amount) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(amount);
        } else {
            System.out.println("Стратегия оплаты не установлена.");
        }
    }
}

// Интерфейс для паттерна Наблюдатель
interface IObserver {
    void update(String currency, double rate);
}

// Интерфейс для субъекта (наблюдаемого объекта)
interface ISubject {
    void registerObserver(IObserver observer);

    void removeObserver(IObserver observer);

    void notifyObservers();
}

// Реализация класса субъекта (обмен валют)
class CurrencyExchange implements ISubject {
    private List<IObserver> observers;
    private String currency;
    private double rate;

    public CurrencyExchange() {
        observers = new ArrayList<>();
    }

    // Устанавливаем новый курс валюты и уведомляем наблюдателей
    public void setRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
        notifyObservers();
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
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(currency, rate);
        }
    }
}

// Реализация наблюдателя 1 (инвестор)
class InvestorObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("Инвестор уведомлен: курс " + currency + " изменился на " + rate);
    }
}

// Реализация наблюдателя 2 (новостное агентство)
class NewsAgencyObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("Новостное агентство сообщает: " + currency + " теперь " + rate);
    }
}

// Реализация наблюдателя 3 (биржа)
class StockExchangeObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.println("Биржа обновляет данные: " + currency + " теперь " + rate);
    }
}

public class HomeTask7 {

    // Метод для демонстрации работы паттерна Стратегия
    public static void runStrategyPattern() {
        PaymentContext context = new PaymentContext();

        // Оплата банковской картой
        context.setPaymentStrategy(new CreditCardPaymentStrategy());
        context.executePayment(150.0);

        // Оплата через PayPal
        context.setPaymentStrategy(new PayPalPaymentStrategy());
        context.executePayment(200.0);

        // Оплата криптовалютой
        context.setPaymentStrategy(new CryptoPaymentStrategy());
        context.executePayment(300.0);
    }

    // Метод для демонстрации работы паттерна Наблюдатель
    public static void runObserverPattern() {
        CurrencyExchange currencyExchange = new CurrencyExchange();

        // Создаем наблюдателей
        IObserver investor = new InvestorObserver();
        IObserver newsAgency = new NewsAgencyObserver();
        IObserver stockExchange = new StockExchangeObserver();

        // Регистрируем наблюдателей
        currencyExchange.registerObserver(investor);
        currencyExchange.registerObserver(newsAgency);
        currencyExchange.registerObserver(stockExchange);

        // Обновляем курс валют
        currencyExchange.setRate("USD", 1.1);
        currencyExchange.setRate("EUR", 0.9);

        // Удаляем одного наблюдателя
        currencyExchange.removeObserver(stockExchange);

        // Обновляем курс после удаления
        currencyExchange.setRate("GBP", 0.8);
    }

    public static void main(String[] args) {
        System.out.println("Демонстрация работы паттерна Стратегия:");
        runStrategyPattern();

        System.out.println("\nДемонстрация работы паттерна Наблюдатель:");
        runObserverPattern();
    }
}
