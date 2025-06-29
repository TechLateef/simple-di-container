import mine.MyInjector;
import mine.ServiceA;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

    MyInjector injector = new  MyInjector("mine");
        ServiceA a = injector.getObject(ServiceA.class);
        a.doSomething();
    }
}