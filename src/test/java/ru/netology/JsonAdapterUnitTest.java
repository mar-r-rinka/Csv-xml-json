package ru.netology;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class JsonAdapterUnitTest {
    @Test
    public void readStringValidArgument_success(){

        String path = "src/test/resources/Test.json";

        String result = JsonAdapter.readString(path);

        String expected = "[{\"id\":55,\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"country\":\"RU\",\"age\":80}]";
        Assertions.assertEquals(expected,result);
        assertThat(result, is(expected));
    }

    @Test
    public void fileMissing_success(){

        String path = "src/test/resources/Test1.json";

        String result = JsonAdapter.readString(path);

        String expected = "";
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void jsonToListValidArgument_success(){

        String json = JsonAdapter.readString("src/test/resources/Test.json");

        List <Employee> result = JsonAdapter.jsonToList(json);

        List<Employee> expected = List.of(new Employee(55,"Ivan", "Ivanov", "RU", 80));
        Assertions.assertEquals(expected,result);
        assertThat(result,Matchers.hasItems(new Employee(55,"Ivan", "Ivanov", "RU", 80)));
    }

    @Test
    public void listToJson_success(){
        List<Employee> list = List.of(new Employee(55,"Ivan", "Ivanov", "RU", 80));
    String result = JsonAdapter.listToJson(list);

    String expected = "[{\"id\":55,\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"country\":\"RU\",\"age\":80}]";
    Assertions.assertEquals(expected,result);
    }

    @ParameterizedTest
    @EmptySource
    public void stringEmptyJsonToList(String input){
       Assertions.assertTrue(input.isEmpty());
    }

}
