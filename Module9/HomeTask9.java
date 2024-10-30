//-------------Задание №1 паттерн Декоратор
// Базовый интерфейс Beverage
interface Beverage {
    String getDescription();
    double cost();
}

// Реализация базового напитка Espresso
class Espresso implements  Beverage {
    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public double cost() {
        return 4.50;
    }
}

// Реализация базового напитка Tea
class Tea implements Beverage {
    @Override
    public String getDescription() {
        return "Tea";
    }

    @Override
    public double cost() {
        return 3.00;
    }
}

// Реализация базового напитка Latte
class Latte implements Beverage {
    @Override
    public String getDescription() {
        return "Latte";
    }

    @Override
    public double cost() {
        return 5.00;
    }
}

// Абстрактный класс-декоратор BeverageDecorator
abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    public  BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public  String getDescription() {
        return beverage.getDescription();
    }

    @Override
    public double cost() {
        return beverage.cost();
    }
}

// Конкретные декораторы для добавок
class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 1.30;
    }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.30;
    }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.55;
    }
}

class Syrup extends BeverageDecorator {
    public Syrup(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Syrup";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.35;
    }
}


//-----------Задание №2 паттерна Адаптер
// Интерфейс для обработки платежей
interface IPaymentProcessor {
    void processPayment(double amount);
}

// Реализация PayPalPaymentProcessor
class PayPalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}

// Сторонний платежный сервис Stripe
class StripePaymentService {
    protected void makeTransaction(double totalAmount) {
        System.out.println("Processing Stripe paymentof $" + totalAmount);
    }
}

// Адаптер для StripePaymentService
class StripePaymentAdapter implements IPaymentProcessor {
    private final StripePaymentService stripePaymentService;

    public StripePaymentAdapter(StripePaymentService stripePaymentService) {
        this.stripePaymentService = stripePaymentService;
    }

    @Override
    public void processPayment(double amount) {
        stripePaymentService.makeTransaction(amount);
    }
}

// Новый сторонний платежный сервис Square
class SquarePaymentService {
    public void executePayment(double amount) {
        System.out.println("Processing Square payment of $" + amount);
    }
}

class SquarePaymentAdapter implements IPaymentProcessor {
    private final  SquarePaymentService squarePaymentService;

    public SquarePaymentAdapter(SquarePaymentService squarePaymentService) {
        this.squarePaymentService = squarePaymentService;
    }

    @Override
    public void processPayment(double amount) {
        squarePaymentService.executePayment(amount);
    }
}

    public class HomeTask9 {
    public static void main(String[] args) {
        System.out.println("-----Реализация паттерна Декоратор-----");
        Beverage beverage1 = new Espresso();
        beverage1 = new Milk(beverage1);
        beverage1 = new Sugar(beverage1);
        System.out.println(beverage1.getDescription() + " costs $" + Math.round(beverage1.cost() * 100.0) / 100.0);

        Beverage beverage2 = new Latte();
        beverage2 = new WhippedCream(beverage2);
        beverage2 = new Syrup(beverage2);
        System.out.println(beverage2.getDescription() + " costs $" + Math.round(beverage2.cost() * 100.0) / 100.0);

        Beverage beverage3 = new Tea();
        beverage3 = new Milk(beverage3);
        beverage3 = new Sugar(beverage3);
        beverage3 = new WhippedCream(beverage3);
        System.out.println(beverage3.getDescription() + " costs $" + Math.round(beverage3.cost() * 100.0) / 100.0);
        System.out.println();

        System.out.println("-----Реализация паттерна Адаптер-----");
        // Использование PayPalPaymentProcessor
        IPaymentProcessor paymentProcessor = new PayPalPaymentProcessor();
        paymentProcessor.processPayment(100.0);

        // Использование StripePaymentService через адаптер
        StripePaymentService stripeService = new StripePaymentService();
        IPaymentProcessor stripeAdapter = new StripePaymentAdapter(stripeService);
        stripeAdapter.processPayment(200.0);

        // Использование SquarePaymentService через адаптер
        SquarePaymentService squareService = new SquarePaymentService();
        IPaymentProcessor squareAdapter =  new SquarePaymentAdapter(squareService);
        squareAdapter.processPayment(300.0);
    }
}