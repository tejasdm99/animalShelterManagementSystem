// Designing an Animal Shelter Management System
import java.util.*;

abstract class Animal {
    protected String name;
    protected String species;
    protected int age;
    protected String healthStatus;
    protected boolean adoptionStatus;

    public Animal(String name, String species, int age, String healthStatus) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.healthStatus = healthStatus;
        this.adoptionStatus = false;
    }

    public abstract void displayInfo();

    public void updateHealthStatus(String status) {
        this.healthStatus = status;
    }

    public void markAdopted() {
        this.adoptionStatus = true;
    }

    public boolean isAdopted() {
        return adoptionStatus;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }
}

class Dog extends Animal {
    private String breed;
    private boolean trained;

    public Dog(String name, int age, String healthStatus, String breed, boolean trained) {
        super(name, "Dog", age, healthStatus);
        this.breed = breed;
        this.trained = trained;
    }

    public void displayInfo() {
        String status = adoptionStatus ? "Adopted" : "Available";
        System.out.println(name + " - " + species + " - Breed: " + breed + ", Trained: " + trained + ", Status: " + status);
    }
}

class Cat extends Animal {
    private String color;
    private boolean indoor;

    public Cat(String name, int age, String healthStatus, String color, boolean indoor) {
        super(name, "Cat", age, healthStatus);
        this.color = color;
        this.indoor = indoor;
    }

    public void displayInfo() {
        String status = adoptionStatus ? "Adopted" : "Available";
        System.out.println(name + " - " + species + " - Color: " + color + ", Indoor: " + indoor + ", Status: " + status);
    }
}

class Bird extends Animal {
    private double wingSpan;
    private boolean canFly;

    public Bird(String name, int age, String healthStatus, double wingSpan, boolean canFly) {
        super(name, "Bird", age, healthStatus);
        this.wingSpan = wingSpan;
        this.canFly = canFly;
    }

    public void displayInfo() {
        String status = adoptionStatus ? "Adopted" : "Available";
        System.out.println(name + " - " + species + " - Wing Span: " + wingSpan + "m, Can Fly: " + canFly + ", Status: " + status);
    }
}

class Staff {
    private int staffId;
    private String name;
    private String role;
    private List<String> tasks = new ArrayList<>();

    public Staff(int staffId, String name, String role) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
    }

    public void assignTask(String task) {
        tasks.add(task);
        System.out.println("Task assigned to " + name + ": " + task);
    }

    public void displayTasks() {
        System.out.println("\nTasks assigned to " + name + " (" + role + "):");
        if (tasks.isEmpty()) {
            System.out.println("No tasks assigned.");
        } else {
            for (String task : tasks) {
                System.out.println("- " + task);
            }
        }
    }
}

class Adopter {
    private int adopterId;
    private String name;
    private String contactInfo;
    private List<Animal> adoptedAnimals = new ArrayList<>();

    public Adopter(int adopterId, String name, String contactInfo) {
        this.adopterId = adopterId;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public void adoptAnimal(Animal animal) {
        if (!animal.isAdopted()) {
            animal.markAdopted();
            adoptedAnimals.add(animal);
            System.out.println(name + " has adopted " + animal.getName());
        } else {
            System.out.println(animal.getName() + " is already adopted.");
        }
    }

    public void displayAdoptedAnimals() {
        if (adoptedAnimals.isEmpty()) {
            System.out.println("No animals adopted yet.");
        } else {
            System.out.println("\nAdopted Animals by " + name + ":");
            for (Animal a : adoptedAnimals) {
                System.out.println(a.getName() + " (" + a.getSpecies() + ")");
            }
        }
    }

    public List<Animal> getAdoptedAnimals() {
        return adoptedAnimals;
    }
}

class Shelter {
    private List<Animal> animals = new ArrayList<>();

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void viewAnimals() {
        if (animals.isEmpty()) {
            System.out.println("No animals in the shelter.");
            return;
        }
        System.out.println("\nList of Animals in Shelter:");
        for (Animal a : animals) {
            a.displayInfo();
        }
    }

    public Animal findAnimalByName(String name) {
        for (Animal a : animals) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public void updateAnimalHealth(String name, String newStatus) {
        Animal a = findAnimalByName(name);
        if (a != null) {
            a.updateHealthStatus(newStatus);
            System.out.println("Updated " + name + "'s health status to \"" + newStatus + "\".");
        } else {
            System.out.println("Animal not found: " + name);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Shelter shelter = new Shelter();

        // Add a new Dog
        System.out.println("Add a Dog");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Breed: ");
        String breed = scanner.nextLine();
        System.out.print("Trained (true/false): ");
        boolean trained = Boolean.parseBoolean(scanner.nextLine());
        Dog dog = new Dog(name, age, "Unknown", breed, trained);
        shelter.addAnimal(dog);

        // Add a Cat
        System.out.println("\nAdd a Cat");
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Age: ");
        age = Integer.parseInt(scanner.nextLine());
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Indoor (true/false): ");
        boolean indoor = Boolean.parseBoolean(scanner.nextLine());
        Cat cat = new Cat(name, age, "Unknown", color, indoor);
        shelter.addAnimal(cat);

        // Add a Bird
        System.out.println("\nAdd a Bird");
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Age: ");
        age = Integer.parseInt(scanner.nextLine());
        System.out.print("Wing Span (in meters): ");
        double wingSpan = Double.parseDouble(scanner.nextLine());
        System.out.print("Can Fly (true/false): ");
        boolean canFly = Boolean.parseBoolean(scanner.nextLine());
        Bird bird = new Bird(name, age, "Unknown", wingSpan, canFly);
        shelter.addAnimal(bird);

        // Display animals
        System.out.println("\n--- All Animals ---");
        shelter.viewAnimals();

        // Add a staff member
        System.out.println("\nAdd a Staff Member");
        System.out.print("Staff ID: ");
        int staffId = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: ");
        String staffName = scanner.nextLine();
        System.out.print("Role: ");
        String role = scanner.nextLine();
        Staff staff = new Staff(staffId, staffName, role);

        // Assign task
        System.out.print("Enter task for staff: ");
        String task = scanner.nextLine();
        staff.assignTask(task);
        staff.displayTasks();

        // Add adopter and adopt an animal
        System.out.println("\nAdd an Adopter");
        System.out.print("Adopter ID: ");
        int adopterId = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: ");
        String adopterName = scanner.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scanner.nextLine();
        Adopter adopter = new Adopter(adopterId, adopterName, contactInfo);

        System.out.print("Enter name of animal to adopt: ");
        String animalToAdopt = scanner.nextLine();
        Animal animal = shelter.findAnimalByName(animalToAdopt);
        if (animal != null) {
            adopter.adoptAnimal(animal);
        } else {
            System.out.println("Animal not found.");
        }

        adopter.displayAdoptedAnimals();

        // Update health of adopted animal by staff
        if (!adopter.getAdoptedAnimals().isEmpty()) {
            Animal adoptedAnimal = adopter.getAdoptedAnimals().get(0);
            System.out.print("\nEnter new health status for " + adoptedAnimal.getName() + ": ");
            String newHealth = scanner.nextLine();
            shelter.updateAnimalHealth(adoptedAnimal.getName(), newHealth);
        }

        scanner.close();
    }
}
