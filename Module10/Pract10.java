import java.util.ArrayList;
import java.util.List;

//Задание №1 Фасад
// Подсистема бронирования номеров
class RoomBookingSystem {
    public void bookRoom() {
        System.out.println("Номер забронирован.");
    }

    public void checkAvailability() {
        System.out.println("Проверка доступности номера.");
    }

    public void cancelBooking() {
        System.out.println("Бронирование номера отменено.");
    }
}

//Подсистема ресторана
class RestaurantSystem {
    public void reserveTable() {
        System.out.println("Столик забронирован.");
    }

    public void orderFood() {
        System.out.println("Заказ на еду оформлен.");
    }

    public void requestTaxi() {
        System.out.println("Такси вызвано.");
    }
}

//Подсистема управления мероприятий
class EventManagementSystem {
    public void bookConferenceRoom() {
        System.out.println("Конференция-зал забронирован.");
    }

    public void arrangeEquipment() {
        System.out.println("Оборудование для мероприятия заказано.");
    }
}

//Подсистема службы уборки
class CleaningService {
    public void scheduleCleaning() {
        System.out.println("Уборка заплонирована.");
    }

    public void performCleaning() {
        System.out.println("Уборка выполнена.");
    }
}

//Класс Фасад
class HotelFacade {
    private RoomBookingSystem roomBooking;
    private RestaurantSystem restaurant;
    private EventManagementSystem eventManagement;
    private CleaningService cleaning;

    public HotelFacade() {
        roomBooking = new RoomBookingSystem();
        restaurant = new RestaurantSystem();
        eventManagement = new EventManagementSystem();
        cleaning = new CleaningService();
    }

    // Бронирование номера с заказом еды и услуг уборки
    public void bookRoomWithServices() {
        roomBooking.bookRoom();
        restaurant.orderFood();
        cleaning.scheduleCleaning();
    }

    //Организации мероприятия с бронированием оборудования и номеров
    public void arrangeEventWithRoomAndEquipment() {
        eventManagement.bookConferenceRoom();
        roomBooking.bookRoom();
        eventManagement.arrangeEquipment();
    }

    //Бронирования стола в ресторане с вызовом такси
    public void reserveTableWithTaxi() {
        restaurant.reserveTable();
        restaurant.requestTaxi();
    }

    //Отмена бронирования номера
    public void cancelRoomBooking() {
        roomBooking.cancelBooking();
    }

    //Организация уброки по запросу
    public void requestCleaningService() {
        cleaning.performCleaning();
    }
}

//Задание №2 Компоновщик
// Абстрактный класс, представляющий общий компонент организации
abstract class OrganizationComponent {
    protected String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void showStructure();
    public abstract double getBudget();
    public abstract int getEmployeeCount();
    public abstract OrganizationComponent findEmployeeByName(String employeeName);
}

// Класс для представления сотрудников
class Employee extends OrganizationComponent {
    private String position;
    private double salary;

    public Employee(String name, String position, double salary) {
        super(name);
        this.position = position;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public void showStructure() {
        System.out.println("Employee: " + name + ", Position: " + position + ", Salary: " + salary);
    }

    @Override
    public double getBudget() {
        return salary;
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }

    @Override
    public OrganizationComponent findEmployeeByName(String employeeName) {
        return this.name.equals(employeeName) ? this : null;
    }
}

// Класс для представления отделов (которые могут содержать сотрудников и другие отделы)
class Department extends OrganizationComponent {
    private List<OrganizationComponent> components = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    public void addComponent(OrganizationComponent component) {
        components.add(component);
    }

    public void removeComponent(OrganizationComponent component) {
        components.remove(component);
    }

    @Override
    public void showStructure() {
        System.out.println("Department: " + name);
        for (OrganizationComponent component : components) {
            component.showStructure();
        }
    }

    @Override
    public double getBudget() {
        double totalBudget = 0;
        for (OrganizationComponent component : components) {
            totalBudget += component.getBudget();
        }
        return totalBudget;
    }

    @Override
    public int getEmployeeCount() {
        int count = 0;
        for (OrganizationComponent component : components) {
            count += component.getEmployeeCount();
        }
        return count;
    }

    @Override
    public OrganizationComponent findEmployeeByName(String employeeName) {
        for (OrganizationComponent component : components) {
            OrganizationComponent found = component.findEmployeeByName(employeeName);
            if (found != null) return found;
        }
        return null;
    }

    public void listAllEmployees() {
        for (OrganizationComponent component : components) {
            if (component instanceof Employee) {
                component.showStructure();
            } else if (component instanceof Department) {
                ((Department) component).listAllEmployees();
            }
        }
    }
}

// Класс для представления контракторов
class Contractor extends Employee {
    public Contractor(String name, String position, double fixedPayment) {
        super(name, position, fixedPayment);
    }

    @Override
    public double getBudget() {
        return 0;
    }
}


public class Pract10 {
    public static void main(String[] args) {
        //Задание №1 Фасад
        HotelFacade hotelFacade = new HotelFacade();

        System.out.println("Сценарий 1: Бронирование номера с заказом еды и услуг уборки:");
        hotelFacade.bookRoomWithServices();

        System.out.println("\nСценарий 2: Организации мероприятия с бронированием оборудования и номеров:");
        hotelFacade.arrangeEventWithRoomAndEquipment();

        System.out.println("\nСценарий 3: Бронирование стола в ресторане с вызовом такси:");
        hotelFacade.reserveTableWithTaxi();

        System.out.println("\nОтмена бронирования номера:");
        hotelFacade.cancelRoomBooking();

        System.out.println("\nЗапрос на организация уборки:");
        hotelFacade.requestCleaningService();

        System.out.println();
        //Задание №2 Компоновщик
        // Создание сотрудников
        Employee emp1 = new Employee("Арман", "Developer", 70000);
        Employee emp2 = new Employee("Руслан", "Designer", 65000);
        Contractor contractor1 = new Contractor("Аскар", "Consultant", 30000);

        // Создание отделов и добавление сотрудников
        Department itDept = new Department("IT Department");
        itDept.addComponent(emp1);
        itDept.addComponent(contractor1);

        Department designDept = new Department("Design Department");
        designDept.addComponent(emp2);

        // Создание главного отдела и добавление подразделений
        Department headOffice = new Department("Head Office");
        headOffice.addComponent(itDept);
        headOffice.addComponent(designDept);

        // Вывод структуры организации
        System.out.println("Organization Structure:");
        headOffice.showStructure();

        // Расчет общего бюджета и количества сотрудников
        System.out.println("\nTotal Budget: $" + headOffice.getBudget());
        System.out.println("Total Employee Count: " + headOffice.getEmployeeCount());

        // Поиск сотрудника по имени
        OrganizationComponent foundEmployee = headOffice.findEmployeeByName("Alice");
        if (foundEmployee != null) {
            System.out.println("\nFound Employee:");
            foundEmployee.showStructure();
        } else {
            System.out.println("Employee not found.");
        }

        // Вывод всех сотрудников отдела IT и его подчиненных отделов
        System.out.println("\nAll Employees in IT Department:");
        itDept.listAllEmployees();
    }
}
