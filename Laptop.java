import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Laptop {
    public static void main(String[] args) {
        Set<Laptop> set = new HashSet<>();
        set.add(new Laptop("KW14/3", "Win10", 8, "HP", 700));
        set.add(new Laptop("BWWSH87", "No OS", 16, "Lenovo", 600));
        set.add(new Laptop("KWQ23", "No OS", 16, "Lenovo", 650));
        Operetions operation = new Operetions(set);
        operation.Start();
    }

    String Name;
    String OS;
    int RAM;
    String Brand;
    int Price;
    

    public Laptop(String Name, String OS, int RAM, String Brand, int Price){
        this.Name = Name;
        this.OS = OS;
        this.RAM = RAM;
        this.Brand = Brand;
        this.Price = Price;
    }

    public static List<String> propertiesForFilter(){
        List<String> list = new ArrayList<>();
        list.add("OS");
        list.add("RAM");
        list.add("Brand");
        list.add("Price");
        return list;
    }

    public String GetName() {
        return Name;
    }

    //public void SetName(String name) {
    //    this.Name = Name;
    //}

    public String GetOS() {
        return OS;
    }

    public void SetOS(String OS) {
        this.OS = OS;
    }
    public int GetRAM() {
        return RAM;
    }

    //public void SetAmountRAM(int amountRAM) {
    //    this.RAM = RAM;
    //}

    public String GetBrand() {
        return Brand;
    }

    //public void SetBrand(String model) {
    //     this.Brand = Brand;
    //}

    public int GetPrice() {
        return Price;
    }

    //public void SetPrice(int price) {
    //    this.Price = Price;
    //}

    Set<Laptop> laptops = new HashSet<>();
    
}

class Criterion {

    Object value;
    Double minValue;
    Double maxValue;
    boolean isQuantitative;
    String property;

    public Criterion(String property, boolean isQuantitative, Object value, Double minValue, Double maxValue) {
        this.property = property;
        this.isQuantitative = isQuantitative;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static Criterion StartGetting(Scanner scanner, String property, boolean isQuantitative) {
        if (isQuantitative) {
            String quest = "Выберите тип криетрия: " +
                    "\n 1. Значение" +
                    "\n 2. Меньше" +
                    "\n 3. Больше" +
                    "\n 4. Интервал";
            System.out.println(quest);
            String text = scanner.next();
            Criterion criterion = null;
            if (text.equals("1")) {
                System.out.println("Введите значение поиска: ");
                int getValue = scanner.nextInt();
                criterion = new Criterion(property, isQuantitative, getValue, null, null);
            } else if (text.equals("2")) {
                System.out.println("Введите максимальное предельное значение: ");
                double getValue = scanner.nextDouble();
                criterion = new Criterion(property, isQuantitative, null, null, getValue);
            } else if (text.equals("3")) {
                System.out.println("Введите минимальное предельное значение: ");
                double getValue = scanner.nextDouble();
                criterion = new Criterion(property, isQuantitative, null, getValue, null);
            } else if (text.equals("4")) {
                System.out.println("Введите минимальное предельное значение: ");
                double getMin = scanner.nextDouble();
                System.out.println("Введите максимальное предельное значение: ");
                double getMax = scanner.nextDouble();
                criterion = new Criterion(property, isQuantitative, null, getMin, getMax);
            }
            return criterion;
        }
        System.out.println("Введите значение поиска: ");
        String getValue = scanner.next();
        return new Criterion(property, isQuantitative, getValue, null, null);
    }
}

class Operetions{
    private static Scanner scanner = new Scanner(System.in);
    Set<Laptop> laptops = new HashSet<>();
    private List<Criterion> criterionList = new ArrayList<>();
    public Operetions(Set<Laptop> laptops) {
        this.scanner = new Scanner(System.in);
        this.laptops = laptops;
    }

    public Operetions(Set<Laptop> laptops, List<Criterion> criterionList) {
        this.scanner = new Scanner(System.in);
        this.laptops = laptops;
        this.criterionList = criterionList;
    }
    public boolean LaptoIsCorrect(Laptop notebook){
        for (Criterion criterion : criterionList){
            Object LaptoValue = null;
            if (criterion.property.equals("Name")){
                LaptoValue = notebook.GetName();
            }else if (criterion.property.equals("OS")){
                LaptoValue = notebook.GetOS();
            }else if (criterion.property.equals("RAM")){
                LaptoValue = notebook.GetRAM();
            }else if (criterion.property.equals("Brand")){
                LaptoValue = notebook.GetBrand();
            }else if (criterion.property.equals("Price")){
                LaptoValue = notebook.GetPrice();
            }else {
                continue;
            }
            if (criterion.value != null && !criterion.value.equals(LaptoValue)){
                return false;
            }
            if (criterion.maxValue != null && criterion.maxValue < Double.parseDouble(Objects.toString(LaptoValue))){
                return false;
            }
            if (criterion.minValue != null && criterion.minValue > Double.parseDouble(Objects.toString(LaptoValue))){
                return false;
            }
        }
        return true;
    }

    public void PrintList(){
        for (Laptop laptop : laptops){
            if (LaptoIsCorrect(laptop)){
                System.out.println(laptop);
            }
        }
    }

    public String GetPropertyDescription(String property){
        Map<String, String> descriptionsProperties = DescriptionsProperties();
        return descriptionsProperties.get(property);
    }

    public int GetCriteria(){
        String text = "Введите цифру, соответствующую необходимому критерию: ";
        List<String> properties = PropertiesForFilter();
        for (int i = 0; i < properties.size(); i++)
        {
            text += "\n" + (i + 1) + ". " + GetPropertyDescription(properties.get(i));
        }
        System.out.println(text);
        int value = scanner.nextInt();
        return value;
    }

    public Map<String, String> DescriptionsProperties(){
        Map<String, String> map = new HashMap<>();
        map.put("Name", "Наименование");
        map.put("OS", "Операционная система");
        map.put("RAM", "Объем оперативной памяти");
        map.put("Brand", "брэнд");
        map.put("Price", "цена");
        return map;
    }

    public List<String> PropertiesForFilter(){
        List<String> list = new ArrayList<>();
        list.add("Name");
        list.add("OS");
        list.add("RAM");
        list.add("Brand");
        list.add("Price");
        return list;
    }

    public String GetOperations(){
        String text = "Выберите опрерацию: \n " +
                "1. Добавить критерий \n " +
                "2. Вывести список \n " +
                "3. Завершить";

        System.out.println(text);
        String answer = scanner.next();
        return answer;
    }

    public Set<String> QuantitativeSelection(){
        Set<String> set = new HashSet<>();
        set.add("RAM");
        set.add("Price");
        return set;
    }

    public Set<String> StringSelection(){
        Set<String> set = new HashSet<>();
        set.add("Name");
        set.add("OS");
        set.add("Brand");

        return set;
    }

    public void Start(){
        boolean flag = true;
        while (flag){
            String operation = GetOperations();
            if (operation.equals("3")){
                flag = false;
                scanner.close();
                continue;
            }else if(operation.equals("1")){

                int criterion = GetCriteria();
                List<String> properties = PropertiesForFilter();
                if (criterion - 1 < 0 || criterion - 1 > properties.size() - 1){
                    System.out.println("Введено некорректное значение");
                    continue;
                }
                String property = properties.get(criterion - 1);
                Criterion criterionObject = null;
                try {
                    if (QuantitativeSelection().contains(property)){
                        criterionObject = Criterion.StartGetting(scanner, property, true);
                    }else {
                        criterionObject = Criterion.StartGetting(scanner, property, false);
                    }
                }catch (Exception e){
                    System.out.println("Ошибка при выборе критерия");
                    continue;
                }
                if (criterionObject != null){
                    System.out.println("Критерий добавлен");
                    criterionList.add(criterionObject);
                }
            }
            else if (operation.equals("2")){
                PrintList();
            }
        }
    }
}