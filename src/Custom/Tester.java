package Custom;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tester {

    @Test
    public void givenObjectSerializedThenTrueReturned() throws Exception {
        Person person = new Person("soufiane", "cheouati", "34");
        Processor serializer = new Processor();
        String jsonString = serializer.convertToJson(person);
        assertEquals(
                "{\"personAge\":\"34\",\"firstName\":\"Soufiane\",\"lastName\":\"Cheouati\"}",
                jsonString);
    }
}
