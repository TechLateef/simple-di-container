package mine;

@Component
public class ServiceA {
    @Inject
    private ServiceB serviceB;

    public void doSomething() {
        System.out.println("ServiceA is working with " + serviceB.getClass().getSimpleName());
    }
}


