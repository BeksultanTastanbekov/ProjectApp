
import java.util.ArrayList;
import java.util.List;
//Задание №1 Паттерн Фасад
// Подсистема для управления аудиосистемой
class AudioSystem {
    public void turnOn() {
        System.out.println("Audio system is turned on.");
    }

    public void setVolume(int level) {
        System.out.println("Audio volume is set to " + level + ".");
    }

    public void turnoff() {
        System.out.println("Audio system is turned off.");
    }
}

// Подсистема для управления видеопроектором
class VideoProjector {
    public void turnOn() {
        System.out.println("Video projector is turned on.");
    }

    public void setResolution(String resolution) {
        System.out.println("Video projector is set to " + resolution + ".");
    }

    public void turnOff() {
        System.out.println("Video projector is turned off.");
    }
}

// Подсистема для управления освещением
class LightingSystem {
    public void turnOn() {
        System.out.println("Lights are turned on.");
    }

    public void setBrightness(int level) {
        System.out.println("Lights brightness is set to " + level + ".");
    }

    public void turnOff() {
        System.out.println("Lights are turned off.");
    }
}

// Класс Facade для управления домашним кинотеатром
class HomeTheaterFacade {
    private AudioSystem audioSystem;
    private VideoProjector videoProjector;

    private LightingSystem lightingSystem;

    public  HomeTheaterFacade(AudioSystem audioSystem, VideoProjector videoProjector, LightingSystem lightingSystem) {
        this.audioSystem = audioSystem;
        this.videoProjector = videoProjector;
        this.lightingSystem = lightingSystem;
    }

    public void startMovie() {
        System.out.println("Preparating to start the movie...");
        lightingSystem.turnOn();
        lightingSystem.setBrightness(5);
        audioSystem.turnOn();
        audioSystem.setVolume(8);
        videoProjector.turnOn();
        videoProjector.setResolution("HD");
        System.out.println("Movie started.");
    }

    public void endMovie() {
        System.out.println("Shutting down movie...");
        videoProjector.turnOff();
        audioSystem.turnoff();
        lightingSystem.turnOff();
        System.out.println("Movie ended.");
    }
}

//Задание №2 Паттерн Компоновщик
// Абстрактный компонент файловой системы
abstract class FileSystemComponent {
    protected String name;
    public FileSystemComponent(String name) {
        this.name = name;
    }

    public  abstract void display(int depth);

    public void add(FileSystemComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException();
    }

    public FileSystemComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }
}

// Класс File, представляющий файл (листовой элемент)
class File extends FileSystemComponent {
    public File(String name) {
        super(name);
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth) + "- File: " + name);
    }
}

//Класс Directory, представляющий директорию (контейнер)
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    @Override
    public void add(FileSystemComponent component) {
        children.add(component);
    }

    @Override
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public  FileSystemComponent getChild(int index) {
        return children.get(index);
    }

    @Override
    public  void display(int depth) {
        System.out.println(" ".repeat(depth) + " - Directory: " + name);
        for (FileSystemComponent component : children) {
            component.display(depth + 2);
        }
    }
}

public class Lab10 {
    public static void main(String[] args) {
        //Задание №1 Фасад
        // Создание подсистем
        AudioSystem audio = new AudioSystem();
        VideoProjector video = new VideoProjector();
        LightingSystem lights = new LightingSystem();

        // Создание фасада
        HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade(audio, video, lights);

        // Начало и завершение просмотра фильма
        homeTheaterFacade.startMovie();
        System.out.println();
        homeTheaterFacade.endMovie();

        System.out.println();
        //Задание №2 Компоновщик
        // Создание файлов и директорий
        Directory root = new Directory("Root");
        File file1 = new File("File1.txt");
        File file2 = new File("File2.txt");

        Directory subDir = new Directory("SubDirectory");
        File subFile1 = new File("SubFile1.txt");

        // Формирование структуры компоновщика
        root.add(file1);
        root.add(file2);
        subDir.add(subFile1);
        root.add(subDir);

        // Отображение структуры файловой системы
        root.display(1);
    }
}