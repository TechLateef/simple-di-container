package mine.app.dto;

public class HelloDto {
    private String name;
    private int age;

    // Getters and setters (Jackson needs them)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
