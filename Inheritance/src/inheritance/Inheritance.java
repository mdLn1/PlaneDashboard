package inheritance;

public class Inheritance {

    
    
    public Inheritance()
    {
        RegularUser reg = new SpecialisedUser(14, "dasds", 20.5);
        
        if (reg instanceof SpecialisedUser)
        {
            System.out.println("Mark: " + ((SpecialisedUser) reg).getMark());
        }
    }
    
    
    
    public static void main(String[] args) {
        Inheritance inher = new Inheritance();
    }
    
}

class RegularUser {
    
    private int age;
    private String name;
    
    public RegularUser(int age, String name)
    {
        this.age = age;
        this.name = name;
    }
    
}

class SpecialisedUser extends RegularUser {
    
    private double mark;
    
    public SpecialisedUser(int age, String name, double mark) {
        super(age, name);
        this.mark = mark;
    }
    
    public double getMark()
    {
        return mark;
    }
    
}