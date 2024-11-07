import java.util.ArrayList;
import java.util.List;

//Задание №1 Фасад
// Подсистемы мультимедийного центра
class TV {
    public void on() {
        System.out.println("Телевизор включен.");
    }

    public void off() {
        System.out.println("Телевизор выключен.");
    }

    public void setChannel(int channel) {
        System.out.println("Телевизор: канал установлен на " + channel);
    }
}

class AudioSystem {
    private int volume;

    public void on() {
        System.out.println("Аудиосистема включена.");
    }

    public void off() {
        System.out.println("Аудиосистема выключена.");
    }

    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("Громкость установлена на " + volume);
    }

    public int getVolume() {
        return volume;
    }
}

class DVDPlayer {
    public void play() {
        System.out.println("DVD-проигрыватель: воспроизведение.");
    }

    public void pause() {
        System.out.println("DVD-проигрыватель: пауза.");
    }

    public void stop() {
        System.out.println("DVD-проигрыватель: остановка.");
    }
}

class GameConsole {
    public void on() {
        System.out.println("Игровая консоль включена.");
    }

    public void startGame(String game) {
        System.out.println("Запуск игры: " + game);
    }
}

// Класс-фасад для управления мультимедийной системой
class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audioSystem;
    private DVDPlayer dvdPlayer;
    private GameConsole gameConsole;

    public HomeTheaterFacade(TV tv, AudioSystem audioSystem, DVDPlayer dvdPlayer, GameConsole gameConsole) {
        this.tv = tv;
        this.audioSystem = audioSystem;
        this.dvdPlayer = dvdPlayer;
        this.gameConsole = gameConsole;
    }

    public void watchMovie() {
        System.out.println("\nПодготовка к просмотру фильма...");
        tv.on();
        audioSystem.on();
        dvdPlayer.play();
    }

    public void endMovie() {
        System.out.println("\nЗавершение просмотра фильма...");
        dvdPlayer.stop();
        tv.off();
        audioSystem.off();
    }

    public void playGame(String game) {
        System.out.println("\nПодготовка к запуску игры...");
        tv.on();
        gameConsole.on();
        gameConsole.startGame(game);
    }

    public void listenToMusic() {
        System.out.println("\nПодготовка к прослушиванию музыки...");
        tv.on();
        audioSystem.on();
        System.out.println("Аудиовход установлен на TV.");
    }

    public void setVolume(int volume) {
        System.out.println("\nРегулировка громкости через фасад...");
        audioSystem.setVolume(volume);
    }
}


//Задание №2 Компоновщик
// Абстрактный класс компонента файловой системы
abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display();
    public abstract int getSize();
}

// Класс File, представляющий файл
class File extends FileSystemComponent {
    private int size;

    public File(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("Файл: " + name + ", Размер: " + size + " KB");
    }

    @Override
    public int getSize() {
        return size;
    }
}

// Класс Directory, представляющий папку, которая может содержать файлы и другие папки
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void addComponent(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
            System.out.println(component.name + " добавлен в " + name);
        } else {
            System.out.println(component.name + " уже существует в " + name);
        }
    }

    public void removeComponent(FileSystemComponent component) {
        if (components.contains(component)) {
            components.remove(component);
            System.out.println(component.name + " удален из " + name);
        } else {
            System.out.println(component.name + " не найден в " + name);
        }
    }

    @Override
    public void display() {
        System.out.println("Папка: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}

public class HomeTask10 {
    public static void main(String[] args) {
        //Задание №1 Фасад
        // Создаем подсистемы
        TV tv = new TV();
        AudioSystem audioSystem = new AudioSystem();
        DVDPlayer dvdPlayer = new DVDPlayer();
        GameConsole gameConsole = new GameConsole();

        // Создаем фасад для управления системой
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audioSystem, dvdPlayer, gameConsole);

        // Используем фасад для выполнения различных сценариев
        homeTheater.watchMovie();
        homeTheater.setVolume(15);
        homeTheater.endMovie();

        homeTheater.listenToMusic();
        homeTheater.setVolume(20);
        homeTheater.playGame("Super Mario");  // Запуск игры
        System.out.println();
        
        //Задание №2 Компоновщик
        // Создание файлов
        File file1 = new File("file1.txt", 10);
        File file2 = new File("file2.jpg", 20);
        File file3 = new File("file3.mp3", 30);

        // Создание папок
        Directory folder1 = new Directory("Folder1");
        Directory folder2 = new Directory("Folder2");
        Directory rootFolder = new Directory("RootFolder");

        // Построение иерархии файлов и папок
        folder1.addComponent(file1);
        folder1.addComponent(file2);

        folder2.addComponent(file3);

        rootFolder.addComponent(folder1);
        rootFolder.addComponent(folder2);

        // Вывод информации о содержимом и размере
        System.out.println("\nСодержимое корневой папки:");
        rootFolder.display();
        System.out.println("\nОбщий размер корневой папки: " + rootFolder.getSize() + " KB");
    }
}
