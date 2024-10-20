import java.util.ArrayList;
import java.util.List;

// Паттерн Команда
interface Command {
    void execute();
    void undo();
}

// Устройство: Свет
class Light {
    public void on() {
        System.out.println("Свет включен.");
    }

    public void off() {
        System.out.println("Свет выключен.");
    }
}

// Команды для управления светом
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}

// Устройство: Телевизор
class Television {
    public void on() {
        System.out.println("Телевизор включен.");
    }

    public void off() {
        System.out.println("Телевизор выключен.");
    }
}

// Команды для управления телевизором
class TelevisionOnCommand implements Command {
    private Television television;

    public TelevisionOnCommand(Television television) {
        this.television = television;
    }

    @Override
    public void execute() {
        television.on();
    }

    @Override
    public void undo() {
        television.off();
    }
}

class TelevisionOffCommand implements Command {
    private Television television;

    public TelevisionOffCommand(Television television) {
        this.television = television;
    }

    @Override
    public void execute() {
        television.off();
    }

    @Override
    public void undo() {
        television.on();
    }
}

// Пульт дистанционного управления
class RemoteControl {
    private Command onCommand;
    private Command offCommand;

    public void setCommands(Command onCommand, Command offCommand) {
        this.onCommand = onCommand;
        this.offCommand = offCommand;
    }

    public void pressOnButton() {
        if (onCommand != null) {
            onCommand.execute();
        } else {
            System.out.println("Ошибка: Команда включения не назначена.");
        }
    }

    public void pressOffButton() {
        if (offCommand != null) {
            offCommand.execute();
        } else {
            System.out.println("Ошибка: Команда выключения не назначена.");
        }
    }

    public void pressUndoButton() {
        if (onCommand != null) {
            onCommand.undo();
        }
    }
}

// Паттерн Шаблонный метод
abstract class Beverage {
    // Шаблонный метод
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    private void boilWater() {
        System.out.println("Кипячение воды...");
    }

    private void pourInCup() {
        System.out.println("Наливание в чашку...");
    }

    protected abstract void brew();
    protected abstract void addCondiments();
}

// Приготовление чая
class Tea extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Заваривание чая...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление лимона...");
    }
}

// Приготовление кофе
class Coffee extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Заваривание кофе...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление сахара и молока...");
    }
}

// Паттерн Посредник
interface Mediator {
    void sendMessage(String message, User user);
}

abstract class User {
    protected Mediator mediator;

    public User(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void receiveMessage(String message);
}

// Конкретный посредник
class ChatMediator implements Mediator {
    private List<User> users;

    public ChatMediator() {
        users = new ArrayList<>();
    }

    public void registerUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receiveMessage(message);
            }
        }
    }
}

// Участник чата
class ChatUser extends User {
    private String name;

    public ChatUser(Mediator mediator, String name) {
        super(mediator);
        this.name = name;
    }

    public void send(String message) {
        System.out.println(name + " отправляет сообщение: " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println(name + " получил сообщение: " + message);
    }
}

public class Lab8 {
    public static void main(String[] args) {
        // Тестирование паттерна Команда
        Light livingRoomLight = new Light();
        Television tv = new Television();

        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        Command tvOn = new TelevisionOnCommand(tv);
        Command tvOff = new TelevisionOffCommand(tv);

        RemoteControl remote = new RemoteControl();

        // Управление светом
        remote.setCommands(lightOn, lightOff);
        System.out.println("Управление светом:");
        remote.pressOnButton();
        remote.pressOffButton();
        remote.pressUndoButton();

        // Управление телевизором
        remote.setCommands(tvOn, tvOff);
        System.out.println("\nУправление телевизором:");
        remote.pressOnButton();
        remote.pressOffButton();
        remote.pressUndoButton();

        // Тестирование паттерна Шаблонный метод
        System.out.println("\nПриготовление напитков:");
        Beverage tea = new Tea();
        tea.prepareRecipe();

        System.out.println();

        Beverage coffee = new Coffee();
        coffee.prepareRecipe();

        // Тестирование паттерна Посредник
        ChatMediator chatMediator = new ChatMediator();

        ChatUser user1 = new ChatUser(chatMediator, "Арман");
        ChatUser user2 = new ChatUser(chatMediator, "Асель");
        ChatUser user3 = new ChatUser(chatMediator, "Ермек");

        chatMediator.registerUser(user1);
        chatMediator.registerUser(user2);
        chatMediator.registerUser(user3);

        user1.send("Привет всем!");
        user2.send("Привет!");
        user3.send("Всем привет!");
    }
}
