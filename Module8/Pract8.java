import java.util.*;

// Интерфейс ICommand
interface ICommand {
    void execute();
    void undo();
}

// Классы устройств
class Light {
    public void on() {
        System.out.println("Свет включен.");
    }
    public void off() {
        System.out.println("Свет выключен.");
    }
}

class AirConditioner {
    public void on() {
        System.out.println("Кондиционер включен.");
    }
    public void off() {
        System.out.println("Кондиционер выключен.");
    }
}

class Television {
    public void on() {
        System.out.println("Телевизор включен.");
    }
    public void off() {
        System.out.println("Телевизор выключен.");
    }
}

class SmartCurtains {
    public void open() {
        System.out.println("Шторы открыты.");
    }
    public void close() {
        System.out.println("Шторы закрыты.");
    }
}

class MusicPlayer {
    public void play() {
        System.out.println("Музыка играет.");
    }
    public void stop() {
        System.out.println("Музыка остановлена.");
    }
}

// Классы команд
class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

class AirConditionerOnCommand implements ICommand {
    private AirConditioner ac;

    public AirConditionerOnCommand(AirConditioner ac) {
        this.ac = ac;
    }

    public void execute() {
        ac.on();
    }

    public void undo() {
        ac.off();
    }
}

class TelevisionOnCommand implements ICommand {
    private Television tv;

    public TelevisionOnCommand(Television tv) {
        this.tv = tv;
    }

    public void execute() {
        tv.on();
    }

    public void undo() {
        tv.off();
    }
}

class CurtainsOpenCommand implements ICommand {
    private SmartCurtains curtains;

    public CurtainsOpenCommand(SmartCurtains curtains) {
        this.curtains = curtains;
    }

    public void execute() {
        curtains.open();
    }

    public void undo() {
        curtains.close();
    }
}

class MusicPlayerPlayCommand implements ICommand {
    private MusicPlayer player;

    public MusicPlayerPlayCommand(MusicPlayer player) {
        this.player = player;
    }

    public void execute() {
        player.play();
    }

    public void undo() {
        player.stop();
    }
}

// Класс RemoteControl
class RemoteControl {
    private final Map<String, ICommand> commandMap = new HashMap<>();
    private final Stack<ICommand> commandHistory = new Stack<>();

    public void setCommand(String slot, ICommand command) {
        commandMap.put(slot, command);
    }

    public void pressButton(String slot) {
        ICommand command = commandMap.get(slot);
        if (command != null) {
            command.execute();
            commandHistory.push(command);
        } else {
            System.out.println("Ошибка: команда не назначена на слот " + slot);
        }
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            ICommand command = commandHistory.pop();
            command.undo();
        } else {
            System.out.println("Нет команд для отмены.");
        }
    }
}

// Абстрактный класс для генерации отчетов
abstract class ReportGenerator {
    // Шаблонный метод
    public final void generateReport() {
        gatherData();
        formatData();
        createReport();
        if (customerWantsSave()) {
            saveReport();
        }
        postProcess();
    }

    protected abstract void formatData();
    protected abstract void createReport();

    private void gatherData() {
        System.out.println("Сбор данных для отчета...");
    }

    protected boolean customerWantsSave() {
        return true; // По умолчанию сохранить
    }

    private void saveReport() {
        System.out.println("Отчет сохранен.");
    }

    protected void postProcess() {
        System.out.println("Обработка отчета завершена.");
    }
}

// Классы отчетов
class PdfReport extends ReportGenerator {
    protected void formatData() {
        System.out.println("Форматирование данных в PDF.");
    }

    protected void createReport() {
        System.out.println("Создание PDF отчета.");
    }
}

class ExcelReport extends ReportGenerator {
    protected void formatData() {
        System.out.println("Форматирование данных в Excel.");
    }

    protected void createReport() {
        System.out.println("Создание Excel отчета.");
    }

    @Override
    protected boolean customerWantsSave() {
        return false; // Пример, когда не сохраняем
    }
}

class HtmlReport extends ReportGenerator {
    protected void formatData() {
        System.out.println("Форматирование данных в HTML.");
    }

    protected void createReport() {
        System.out.println("Создание HTML отчета.");
    }
}

// Интерфейс IMediator
interface IMediator {
    void send(String message, User user);
    void addUser(User user);
}

// Класс ChatMediator
class ChatMediator implements IMediator {
    private final List<User> users = new ArrayList<>();

    public void send(String message, User user) {
        for (User u : users) {
            if (u != user) {
                u.receive(message);
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
    }
}

// Интерфейс IUser
interface IUser {
    void send(String message);
    void receive(String message);
}

// Класс User
class User implements IUser {
    private final IMediator mediator;
    protected final String name;  // Изменили на protected

    public User(IMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
        mediator.addUser(this);
    }

    public void send(String message) {
        System.out.println(name + " отправляет: " + message);
        mediator.send(message, this);
    }

    public void receive(String message) {
        System.out.println(name + " получил: " + message);
    }
}

public class Pract8 {
    public static void main(String[] args) {
        System.out.println("=== Тестирование паттерна Команда ===");
        Light light = new Light();
        AirConditioner ac = new AirConditioner();
        Television tv = new Television();
        SmartCurtains curtains = new SmartCurtains();
        MusicPlayer player = new MusicPlayer();

        RemoteControl remote = new RemoteControl();
        remote.setCommand("1", new LightOnCommand(light));
        remote.setCommand("2", new AirConditionerOnCommand(ac));
        remote.setCommand("3", new TelevisionOnCommand(tv));
        remote.setCommand("4", new CurtainsOpenCommand(curtains));
        remote.setCommand("5", new MusicPlayerPlayCommand(player));

        remote.pressButton("1");
        remote.pressButton("2");
        remote.pressButton("3");
        remote.pressButton("4");
        remote.pressButton("5");

        remote.undoLastCommand();
        remote.undoLastCommand();
        remote.undoLastCommand();
        remote.undoLastCommand();
        remote.undoLastCommand();
        remote.undoLastCommand();

        System.out.println("\n=== Тестирование паттерна Шаблонный метод ===");
        ReportGenerator pdfReport = new PdfReport();
        pdfReport.generateReport();

        ReportGenerator excelReport = new ExcelReport();
        excelReport.generateReport();

        ReportGenerator htmlReport = new HtmlReport();
        htmlReport.generateReport();

        System.out.println("\n=== Тестирование паттерна Посредник ===");
        ChatMediator chatMediator = new ChatMediator();
        User user1 = new User(chatMediator, "Пользователь 1");
        User user2 = new User(chatMediator, "Пользователь 2");

        user1.send("Привет, как дела?");
        user2.send("Все отлично, спасибо!");

        System.out.println("\n=== Обработка ошибок ===");
        try {
            remote.pressButton("6"); // Команда не назначена
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\n=== Добавление нового пользователя ===");
        User user3 = new User(chatMediator, "Пользователь 3");
        user3.send("Привет всем, я новый здесь!");

        System.out.println("\n=== Кросс-канальная отправка сообщений ===");
        user1.send("Это сообщение от Пользователя 1 для всех в чате!");

        System.out.println("\n=== Блокировка пользователя ===");
        BlockedUser blockedUser = new BlockedUser(chatMediator, "Заблокированный Пользователь");
        blockedUser.send("Я не должен быть заблокирован."); // Этот пользователь не должен отправлять сообщения
    }

    static class BlockedUser extends User {
        public BlockedUser(IMediator mediator, String name) {
            super(mediator, name);
        }

        @Override
        public void send(String message) {
            System.out.println("Пользователь " + name + " заблокирован и не может отправлять сообщения.");
        }
    }
}
