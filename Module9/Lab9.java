//Задание №1
interface IBeverage {
    double getCost();
    String getDescription();
}

class Coffee implements IBeverage {
    @Override
    public double getCost()
    {
        return 50.0;  // Стоимость кофе
    }

    @Override
    public String getDescription()
    {
        return "Coffee";
    }
}

// Абстрактный декоратор, который будет основой для добавок
abstract class BeverageDecorator implements  IBeverage {
    protected IBeverage beverage;

    public BeverageDecorator(IBeverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getCost() {
        return beverage.getCost();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }
}

// Декоратор для молока
class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 10.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}

// Декоратор для сахара
class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 5.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }
}

// Декоратор для шоколада
class ChocolateDecorator extends BeverageDecorator {
    public ChocolateDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 15.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Chocolate";
    }
}

// Декоратор для ванили
class VanillaDecorator extends BeverageDecorator {
    public VanillaDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 7.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Vanilla";
    }
}

// Декоратор для корицы
class CinnamonDecorator extends BeverageDecorator {
    public CinnamonDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 8.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Cinnamon";
    }
}

//Задание №2
// Интерфейс для работы с платежной системой
interface IPaymentProcessor {
    void processPayment(double amount);
    void refundPayment(double amount);
}

// Класс для внутренней платежной системы
class InternalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment of " + amount + " via internal system.");
    }

    @Override
    public void refundPayment(double amount) {
        System.out.println("Refunding payment of " + amount + " via internal system.");
    }
}

// Сторонняя платежная система A
class ExternalPaymentSystemA {
    public void makePayment(double amount) {
        System.out.println("Making payment of " + amount + " via External Payment System A.");
    }

    public void makeRefund(double amount) {
        System.out.println("Making refund of " + amount + " via External Payment System A.");
    }
}

// Сторонняя платежная система B
class ExternalPaymentSystemB {
    public void sendPayment(double amount) {
        System.out.println("Sending payment of " + amount + " via External Payment System B.");
    }

    public void processRefund(double amount) {
        System.out.println("Processing refund of " + amount + " via External Payment System B.");
    }
}

// Адаптер для ExternalPaymentSystemA
class PaymentAdapterA implements IPaymentProcessor {
    private ExternalPaymentSystemA externalSystemA;

    public PaymentAdapterA(ExternalPaymentSystemA externalSystemA) {
        this.externalSystemA = externalSystemA;
    }

    @Override
    public void processPayment(double amount) {
        externalSystemA.makePayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalSystemA.makeRefund(amount);
    }
}

// Адаптер для ExternalPaymentSystemB
class PaymentAdapterB implements IPaymentProcessor {
    private ExternalPaymentSystemB externalSystemB;

    public PaymentAdapterB(ExternalPaymentSystemB externalSystemB) {
        this.externalSystemB = externalSystemB;
    }

    @Override
    public void processPayment(double amount) {
        externalSystemB.sendPayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalSystemB.processRefund(amount);
    }
}

// Класс для выбора платежной системы в зависимости от валюты
class PaymentProcessorFactory {
    public static IPaymentProcessor getPaymentProcessor(String currency) {
        switch (currency) {
            case "USD":
                return new InternalPaymentProcessor();
            case "EUR":
                return new PaymentAdapterA(new ExternalPaymentSystemA());
            case "JPY":
                return new PaymentAdapterB(new ExternalPaymentSystemB());
            default:
                throw new IllegalArgumentException("Currency not supported");
        }
    }
}

public class Lab9 {
    public static void main(String[] args) {
        //Задание №1
        // Создаем базовый напиток — кофе
        IBeverage beverage = new Coffee();
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        // Добавляем молоко
        beverage = new MilkDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        // Добавляем сахар
        beverage = new SugarDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        // Добавляем шоколад
        beverage = new ChocolateDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        // Добавляем ваниль
        beverage = new VanillaDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        // Добавляем корицу
        beverage = new CinnamonDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        //Задание №2
        System.out.println();
        // Выбираем платежную систему в зависимости от валюты
        IPaymentProcessor processorUSD = PaymentProcessorFactory.getPaymentProcessor("USD");
        processorUSD.processPayment(100.0);
        processorUSD.refundPayment(50.0);

        IPaymentProcessor processorEUR = PaymentProcessorFactory.getPaymentProcessor("EUR");
        processorEUR.processPayment(200.0);
        processorEUR.refundPayment(100.0);

        IPaymentProcessor processorJPY = PaymentProcessorFactory.getPaymentProcessor("JPY");
        processorJPY.processPayment(300.0);
        processorJPY.refundPayment(150.0);
    }
}