import java.util.Date;

//Задание №1
interface IReport {
    String generate();
}

// Класс SalesReport
class SalesReport implements IReport {
    @Override
    public String generate () {
        return "Sales Report Data";
    }
}

// Класс UserReport
class UserReport implements IReport {
    @Override
    public String generate () {
        return "User Report Data";
    }
}

// Абстрактный декоратор ReportDecorator
abstract class ReportDecorator implements IReport {
    protected IReport report;

    public ReportDecorator(IReport report) {
        this.report = report;
    }

    @Override
    public String generate() {
        return report.generate();
    }
}

// Декоратор DateFilterDecorator для фильтрации по дате
class DateFileDecorator extends ReportDecorator {
    private Date satartDate;
    private Date endDate;

    public DateFileDecorator(IReport report, Date startDate, Date endDate) {
        super(report);
        this.satartDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String generate() {
        return report.generate() + "\nFilered by dates: " + satartDate + " - " + endDate;
    }
}

// Декоратор SortingDecorator для сортировки
class SortingDecorator extends ReportDecorator {
    private String criterion;

    public SortingDecorator(IReport report, String criterion) {
        super(report);
        this.criterion = criterion;
    }

    @Override
    public String generate() {
        return report.generate() + "\nSorted by: " + criterion;
    }
}

// Декоратор для фильтрации пользователей по характеристике
class UserCharacteristicFilterDecorator extends ReportDecorator {
    private String characteristic;

    public UserCharacteristicFilterDecorator(IReport report, String characteristic) {
        super(report);
        this.characteristic = characteristic;
    }

    @Override
    public String generate() {
        return report.generate() + "\nFiltered by user characteristic: " + characteristic;
    }
}

// Декоратор CsvExportDecorator для экспорта в CSV
class CsvExportDecorator extends  ReportDecorator {
    public CsvExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return super.generate() + "\nExported as CSV";
    }
}

// Декоратор PdfExportDecorator для экспорта в PDF
class PdfExportDecorator extends  ReportDecorator {
    public PdfExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return super.generate() + "\nExported as PDF";
    }
}

//Задание №2
// Интерфейс внутренней службы доставки
interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
    double calculateDeliveryCost(String orderId);
}

// Класс внутренней службы доставки
class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        System.out.println("[Internal] Заказ " + orderId + " доставлен.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return "[Internal] Статус заказа " + orderId + ": Доставлено";
    }
    @Override
    public double calculateDeliveryCost(String orderId) {
        return 50.0;
    }
}

// Сторонняя логистическая служба A
class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("[Service A] Отправка товара " + itemId);
    }

    public String trackShipment(int shipmentId) {
        return "[Service A] Статус отправвки " + shipmentId;
    }

    public double getShippingCost(int itemId) {
        return 100.0;
    }
}

// Сторонняя логистическая служба B
class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("[Service B] Отправка посылки " + packageInfo);
    }

    public String checkPackageStatus(String trackingCode) {
        return "[Service B] Статус посылки " + trackingCode;
    }

    public double estimateCost(String packageInfo) {
        return 120.0;
    }
}

// Новая сторонняя логистическая служба C
class ExternalLogisticsServiceC {
    public void deliverParcel(String parcelId) {
        System.out.println("[Service C] Доставка посылки " + parcelId);
    }

    public String getParcelStatus(String parcelId) {
        return "[Service C] Статус посылки " + parcelId;
    }

    public double calculateFee(String parcelId) {
        return 80.0;
    }
}

// Адаптер для ExternalLogisticsServiceA
class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA externalServiceA;

    public LogisticsAdapterA(ExternalLogisticsServiceA service) {
        this.externalServiceA = service;
    }

    @Override
    public void deliverOrder(String orderId) {
        try {
            int itemId = Integer.parseInt(orderId);
            externalServiceA.shipItem(itemId);
        } catch (Exception e) {
            System.err.println("Ошибка доставки через Service A: " + e.getMessage());
        }
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        try {
            int shipmentId = Integer.parseInt(orderId);
            return externalServiceA.trackShipment(shipmentId);
        } catch (Exception e) {
            System.err.println("Ошибка при отслеживании заказа через Service A: " + e.getMessage());
            return "Не удалось получить статус";
        }
    }

    @Override
    public double calculateDeliveryCost(String orderId) {
        try {
            int itemId = Integer.parseInt(orderId);
            return externalServiceA.getShippingCost(itemId);
        } catch (Exception e) {
            System.err.println("Ошибка расчета стоимости для Service A: " + e.getMessage());
            return 0.0;
        }
    }
}

// Адаптер для ExternalLogisticsServiceB
class LogisticsAdapterB implements IInternalDeliveryService {
    private ExternalLogisticsServiceB externalServiceB;

    public LogisticsAdapterB(ExternalLogisticsServiceB service) {
        this.externalServiceB = service;
    }

    @Override
    public void deliverOrder(String orderId) {
        try {
            externalServiceB.sendPackage(orderId);
        } catch (Exception e) {
            System.err.println("Ошибка доставки через Service B: " + e.getMessage());
        }
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        try {
            return externalServiceB.checkPackageStatus(orderId);
        } catch (Exception e) {
            System.err.println("Ошибка при отслеживании заказа через Service B: " + e.getMessage());
            return "Не удалось получить статус";
        }
    }

    @Override
    public double calculateDeliveryCost(String orderId) {
        try {
            return externalServiceB.estimateCost(orderId);
        } catch (Exception e) {
            System.err.println("Ошибка расчета стоимости для Service B: " + e.getMessage());
            return 0.0;
        }
    }
}

// Адаптер для ExternalLogisticsServiceC
class LogisticsAdapterC implements IInternalDeliveryService {
    private ExternalLogisticsServiceC externalServiceC;

    public LogisticsAdapterC(ExternalLogisticsServiceC service) {
        this.externalServiceC = service;
    }

    @Override
    public void deliverOrder(String orderId) {
        try {
            externalServiceC.deliverParcel(orderId);
        } catch (Exception e) {
            System.err.println("Ошибка доставки через Service C: " + e.getMessage());
        }
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        try {
            return externalServiceC.getParcelStatus(orderId);
        } catch (Exception e) {
            System.err.println("Ошибка при отслеживании заказа через Service C: " + e.getMessage());
            return "Не удалось получить статус";
        }
    }

    @Override
    public double calculateDeliveryCost(String orderId) {
        try {
            return externalServiceC.calculateFee(orderId);
        } catch (Exception e) {
            System.err.println("Ошибка расчета стоимости для Service C: " + e.getMessage());
            return 0.0;
        }
    }
}

class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String serviceType) {
        switch (serviceType.toLowerCase()) {
            case "internal":
                return new InternalDeliveryService();
            case "externala":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "externalb":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            case "externalc":
                return new LogisticsAdapterC(new ExternalLogisticsServiceC());
            default:
                throw new IllegalArgumentException("Неизвестный тип службы доставки: " + serviceType);
        }
    }
}


public class Pract9 {
    public static void main(String[] args) {
        // Задание №1
        System.out.println("---------------- паттерн Декоратор");
        IReport report = new SalesReport();
        report = new DateFileDecorator(report, new Date(2023, 1, 1), new Date(2023, 12, 31));
        report = new SortingDecorator(report, "date");
        report = new CsvExportDecorator(report);
        System.out.println(report.generate());

        IReport userReport = new UserReport();
        userReport = new DateFileDecorator(userReport, new Date(2023, 5, 1), new Date(2023, 12, 31));
        userReport = new UserCharacteristicFilterDecorator(userReport, "VIP"); // Фильтрация по характеристике пользователя
        userReport = new SortingDecorator(userReport, "userName");
        userReport = new PdfExportDecorator(userReport);
        System.out.println("\n" + userReport.generate());

        //Задание №2
        System.out.println("---------------- паттерн Адаптер");
        // Внутренняя служба
        IInternalDeliveryService internalService = DeliveryServiceFactory.getDeliveryService("internal");
        internalService.deliverOrder("101");
        System.out.println(internalService.getDeliveryStatus("101"));
        System.out.println("Стоимость доставки: " + internalService.calculateDeliveryCost("101"));
        System.out.println();
        // Сторонняя служба A
        IInternalDeliveryService externalServiceA = DeliveryServiceFactory.getDeliveryService("externala");
        externalServiceA.deliverOrder("102");
        System.out.println(externalServiceA.getDeliveryStatus("102"));
        System.out.println("Стоимость доставки: " + externalServiceA.calculateDeliveryCost("102"));
        System.out.println();
        // Сторонняя служба B
        IInternalDeliveryService externalServiceB = DeliveryServiceFactory.getDeliveryService("externalb");
        externalServiceB.deliverOrder("203");
        System.out.println(externalServiceB.getDeliveryStatus("203"));
        System.out.println("Стоимость доставки: " + externalServiceB.calculateDeliveryCost("203"));
        System.out.println();
        // Сторонняя служба C
        IInternalDeliveryService externalServiceC = DeliveryServiceFactory.getDeliveryService("externalc");
        externalServiceC.deliverOrder("301");
        System.out.println(externalServiceC.getDeliveryStatus("301"));
        System.out.println("Стоимость доставки: " + externalServiceC.calculateDeliveryCost("301"));
    }
}