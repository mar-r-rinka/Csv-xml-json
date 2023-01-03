package ru.netology;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class CsvAdapterUnitTest {
    @BeforeAll
    public static void testsStarted() {
        System.out.println("Tests started");
    }

    @AfterAll
    public static void testsCompleted() {
        System.out.println("Tests completed");
    }

    @BeforeEach
    public void testStarted() {
        System.out.println("Test started");
    }

    @AfterEach
    public void testCompleted() {
        System.out.println("Test completed");}

    @Test
    public void parseCSV_validArgument_success(){
        //given
        String[] columnMapping = {"lastName", "country", "age"};
        String path = "src/test/resources/Test.csv";
        //when
        List <Employee> list = CsvAdapter.parseCSV(columnMapping,path);
        //then
        List<Employee> expected = List.of(new Employee(0,null,"Smith","USA",25));
        Assertions.assertEquals(expected,list);
    }

    @Test
    public void parseCSV_fileMissing_success(){
        //given
        String[] columnMapping = {};
        String path = "src/test/resources/Test1.csv";
        //when
        List <Employee> list = CsvAdapter.parseCSV(columnMapping,path);
        //then
        List<Employee> expected = new ArrayList<>();
        Assertions.assertEquals(expected,list);
    }
}
