import java.util.*;

// Паттерн Команда
interface Command {
    void execute();
    void undo();
}

class Light {
    void on() { System.out.println("Свет включен."); }
    void off() { System.out.println("Свет выключен."); }
}

class Door {
    void open() { System.out.println("Дверь открыта."); }
    void close() { System.out.println("Дверь закрыта."); }
}

class Thermostat {
    void increase() { System.out.println("Температура повышена."); }
    void decrease() { System.out.println("Температура понижена."); }
}

class TV {
    void on() { System.out.println("Телевизор включен."); }
    void off() { System.out.println("Телевизор выключен."); }
}

class LightOnCommand implements Command {
    private Light light;

    LightOnCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.on(); }

    @Override
    public void undo() { light.off(); }
}

class LightOffCommand implements Command {
    private Light light;

    LightOffCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.off(); }

    @Override
    public void undo() { light.on(); }
}

class DoorOpenCommand implements Command {
    private Door door;

    DoorOpenCommand(Door door) { this.door = door; }

    @Override
    public void execute() { door.open(); }

    @Override
    public void undo() { door.close(); }
}

class DoorCloseCommand implements Command {
    private Door door;

    DoorCloseCommand(Door door) { this.door = door; }

    @Override
    public void execute() { door.close(); }

    @Override
    public void undo() { door.open(); }
}

class ThermostatIncreaseCommand implements Command {
    private Thermostat thermostat;

    ThermostatIncreaseCommand(Thermostat thermostat) { this.thermostat = thermostat; }

    @Override
    public void execute() { thermostat.increase(); }

    @Override
    public void undo() { thermostat.decrease(); }
}

class TVOnCommand implements Command {
    private TV tv;

    TVOnCommand(TV tv) { this.tv = tv; }

    @Override
    public void execute() { tv.on(); }

    @Override
    public void undo() { tv.off(); }
}

class RemoteControl {
    private Stack<Command> history = new Stack<>();

    void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    void undoLastCommand() {
        if (!history.isEmpty()) {
            history.pop().undo();
        } else {
            System.out.println("Нет команд для отмены.");
        }
    }
}

// Паттерн Шаблонный метод
abstract class Beverage {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    void boilWater() { System.out.println("Кипячение воды."); }

    abstract void brew();

    void pourInCup() { System.out.println("Налить в чашку."); }

    abstract void addCondiments();

    boolean customerWantsCondiments() { return true; }
}

class Tea extends Beverage {
    @Override
    void brew() { System.out.println("Заваривание чая."); }

    @Override
    void addCondiments() { System.out.println("Добавление лимона."); }
}

class Coffee extends Beverage {
    @Override
    void brew() { System.out.println("Заваривание кофе."); }

    @Override
    void addCondiments() { System.out.println("Добавление сахара и молока."); }

    @Override
    boolean customerWantsCondiments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Хотите добавить сахар и молоко (y/n)?");
        String answer = scanner.nextLine();
        return answer.toLowerCase().startsWith("y");
    }
}

class HotChocolate extends Beverage {
    @Override
    void brew() { System.out.println("Приготовление горячего шоколада."); }

    @Override
    void addCondiments() { System.out.println("Добавление взбитых сливок."); }
}

// Паттерн Посредник
interface Mediator {
    void sendMessage(String message, User user);
    void sendPrivateMessage(String message, User fromUser, User toUser);
}

class ChatRoom implements Mediator {
    private List<User> users = new ArrayList<>();

    void addUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " присоединился к чату.");
    }

    @Override
    public void sendMessage(String message, User user) {
        for (User u : users) {
            if (u != user) {
                u.receive(message);
            }
        }
    }

    @Override
    public void sendPrivateMessage(String message, User fromUser, User toUser) {
        if (users.contains(fromUser) && users.contains(toUser)) {
            toUser.receive("Личное сообщение от " + fromUser.getName() + ": " + message);
        } else {
            System.out.println("Один из пользователей не в чате.");
        }
    }
}

abstract class User {
    protected Mediator mediator;
    protected String name;

    User(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract void send(String message);
    abstract void receive(String message);
}

class ChatUser extends User {
    ChatUser(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    void send(String message) {
        System.out.println(this.name + " отправляет сообщение: " + message);
        mediator.sendMessage(message, this);
    }

    void sendPrivate(String message, User toUser) {
        mediator.sendPrivateMessage(message, this, toUser);
    }

    @Override
    void receive(String message) {
        System.out.println(this.name + " получил сообщение: " + message);
    }
}

public class HomeTask8 {
    public static void main(String[] args) {
        // Паттерн "Команда"
        RemoteControl remote = new RemoteControl();
        Light light = new Light();
        Door door = new Door();
        Thermostat thermostat = new Thermostat();
        TV tv = new TV();

        remote.executeCommand(new LightOnCommand(light));
        remote.executeCommand(new DoorOpenCommand(door));
        remote.executeCommand(new TVOnCommand(tv));
        remote.undoLastCommand();  // Отменим включение ТВ

        // Обработка ошибки
        remote.undoLastCommand();
        remote.undoLastCommand();
        remote.undoLastCommand();  // Ошибка: нет команд для отмены

        // Паттерн "Шаблонный метод"
        Beverage tea = new Tea();
        tea.prepareRecipe();

        Beverage coffee = new Coffee();
        coffee.prepareRecipe();

        Beverage hotChocolate = new HotChocolate();
        hotChocolate.prepareRecipe();

        // Паттерн "Посредник"
        ChatRoom chatRoom = new ChatRoom();
        User user1 = new ChatUser(chatRoom, "User1");
        User user2 = new ChatUser(chatRoom, "User2");

        chatRoom.addUser(user1);
        chatRoom.addUser(user2);

        user1.send("Привет всем!");

        // Приведение типов
        ((ChatUser) user2).sendPrivate("Привет, User1", user1);

        // Ошибка: отправка личного сообщения пользователю не в чате
        User user3 = new ChatUser(chatRoom, "User3");
        ((ChatUser) user1).sendPrivate("Привет, User3", user3);  // Ошибка, т.к. user3 не в чате
    }
}

