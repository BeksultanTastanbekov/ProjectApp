import java.util.Scanner;

interface IVehicle {
    void drive();
    void refuel();
}

class Car implements IVehicle {
    private String brand;
    private String model;
    private String fuelType;

    public Car(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public void drive() {
        System.out.println(brand + " " + model + " едет.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка " + brand + " " + model + " топливом " + fuelType + ".");
    }
}

class Motorcycle implements IVehicle {
    private String type;
    private int engineCapacity;

    public Motorcycle(String type, int engineCapacity) {
        this.type = type;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public void drive() {
        System.out.println(type + " мотоцикл с объемом двигателя " + engineCapacity + " куб.см едет.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка " + type + " мотоцикла.");
    }
}

class Truck implements IVehicle {
    private int loadCapacity;
    private int axles;

    public Truck(int loadCapacity, int axles) {
        this.loadCapacity = loadCapacity;
        this.axles = axles;
    }

    @Override
    public void drive() {
        System.out.println("Грузовик грузоподъемностью " + loadCapacity + " тонн и количеством осей " + axles + " едет.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка грузовика.");
    }
}

class Bus implements IVehicle {
    private int passengerCapacity;

    public Bus(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public void drive() {
        System.out.println("Автобус с вместимостью " + passengerCapacity + " пассажиров едет.");
    }

    @Override
    public void refuel() {
        System.out.println("Заправка автобуса.");
    }
}

abstract class VehicleFactory {
    public abstract IVehicle createVehicle();
}

class CarFactory extends VehicleFactory {
    private String brand;
    private String model;
    private String fuelType;

    public CarFactory(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public IVehicle createVehicle() {
        return new Car(brand, model, fuelType);
    }
}

class MotorcycleFactory extends VehicleFactory {
    private String type;
    private int engineCapacity;

    public MotorcycleFactory(String type, int engineCapacity) {
        this.type = type;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public IVehicle createVehicle() {
        return new Motorcycle(type, engineCapacity);
    }
}

class TruckFactory extends VehicleFactory {
    private int loadCapacity;
    private int axles;

    public TruckFactory(int loadCapacity, int axles) {
        this.loadCapacity = loadCapacity;
        this.axles = axles;
    }

    @Override
    public IVehicle createVehicle() {
        return new Truck(loadCapacity, axles);
    }
}

class BusFactory extends VehicleFactory {
    private int passengerCapacity;

    public BusFactory(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public IVehicle createVehicle() {
        return new Bus(passengerCapacity);
    }
}

public class HomeTask4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип транспорта: Car, Motorcycle, Truck, Bus");
        String type = scanner.nextLine();

        VehicleFactory factory = null;

        switch (type.toLowerCase()) {
            case "car":
                System.out.println("Введите марку автомобиля:");
                String brand = scanner.nextLine();
                System.out.println("Введите модель автомобиля:");
                String model = scanner.nextLine();
                System.out.println("Введите тип топлива:");
                String fuelType = scanner.nextLine();
                factory = new CarFactory(brand, model, fuelType);
                break;
            case "motorcycle":
                System.out.println("Введите тип мотоцикла (спорт или туристический):");
                String motorcycleType = scanner.nextLine();
                System.out.println("Введите объем двигателя (в куб.см):");
                int engineCapacity = scanner.nextInt();
                factory = new MotorcycleFactory(motorcycleType, engineCapacity);
                break;
            case "truck":
                System.out.println("Введите грузоподъемность грузовика (в тоннах):");
                int loadCapacity = scanner.nextInt();
                System.out.println("Введите количество осей:");
                int axles = scanner.nextInt();
                factory = new TruckFactory(loadCapacity, axles);
                break;
            case "bus":
                System.out.println("Введите пассажировместимость автобуса:");
                int passengerCapacity = scanner.nextInt();
                factory = new BusFactory(passengerCapacity);
                break;
            default:
                System.out.println("Неверный тип транспорта.");
                break;
        }

        if (factory != null) {
            IVehicle vehicle = factory.createVehicle();
            vehicle.drive();
            vehicle.refuel();
        }
    }
}