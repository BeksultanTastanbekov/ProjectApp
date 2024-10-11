import java.util.Scanner;

interface Transport {
    void move();
    void fuelUp();
}

class Car implements Transport {
    private String model;
    private int speed;

    public Car(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }

    @Override
    public void move() {
        System.out.println("Автомобиль " + model + " движется со скоростью " + speed + " км/ч.");
    }

    @Override
    public void fuelUp() {
        System.out.println("Заправка автомобиля " + model + " бензином.");
    }
}

class Motorcycle implements Transport {
    private String model;
    private int speed;

    public Motorcycle(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }

    @Override
    public void move() {
        System.out.println("Мотоцикл " + model + " движется со скоростью " + speed + " км/ч.");
    }

    @Override
    public void fuelUp() {
        System.out.println("Заправка мотоцикла " + model + " бензином.");
    }
}

class Plane implements Transport {
    private String model;
    private int speed;

    public Plane(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }

    @Override
    public void move() {
        System.out.println("Самолет " + model + " летит со скоростью " + speed + " км/ч.");
    }

    @Override
    public void fuelUp() {
        System.out.println("Заправка самолета " + model + " авиационным топливом.");
    }
}

class Bicycle implements Transport {
    private String model;
    private int speed;

    public Bicycle(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }

    @Override
    public void move() {
        System.out.println("Велосипед " + model + " движется со скоростью " + speed + " км/ч.");
    }

    @Override
    public void fuelUp() {
        System.out.println("Велосипед не требует заправки.");
    }
}

abstract class TransportFactory {
    public abstract Transport createTransport(String model, int speed);
}

class CarFactory extends TransportFactory {
    @Override
    public Transport createTransport(String model, int speed) {
        return new Car(model, speed);
    }
}

class MotorcycleFactory extends TransportFactory {
    @Override
    public Transport createTransport(String model, int speed) {
        return new Motorcycle(model, speed);
    }
}

class PlaneFactory extends TransportFactory {
    @Override
    public Transport createTransport(String model, int speed) {
        return new Plane(model, speed);
    }
}

class BicycleFactory extends TransportFactory {
    @Override
    public Transport createTransport(String model, int speed) {
        return new Bicycle(model, speed);
    }
}

public class Lab4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип транспорта: Car, Motorcycle, Plane, Bicycle");
        String transportType = scanner.nextLine();

        System.out.println("Введите модель транспорта:");
        String model = scanner.nextLine();

        System.out.println("Введите максимальную скорость транспорта (в км/ч):");
        int speed = scanner.nextInt();

        TransportFactory factory = null;

        switch (transportType.toLowerCase()) {
            case "car":
                factory = new CarFactory();
                break;
            case "motorcycle":
                factory = new MotorcycleFactory();
                break;
            case "plane":
                factory = new PlaneFactory();
                break;
            case "bicycle":
                factory = new BicycleFactory();
                break;
            default:
                System.out.println("Неверный тип транспорта.");
                break;
        }

        if (factory != null) {
            Transport transport = factory.createTransport(model, speed);
            transport.move();
            transport.fuelUp();
        }
    }
}
