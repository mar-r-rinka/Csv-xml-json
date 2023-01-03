package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class XmlAdapterUnitTest {
    @Test
    public void parseXML_validArgument_success(){
        //given
        String path = "src/test/resources/Test.xml";
        //when
        List<Employee> list = XmlAdapter.parseXML(path);
        //then
        List<Employee> expected = List.of(new Employee(5,"Marina","Veretina","RU",37));
        Assertions.assertEquals(expected,list);
    }
    @Test
    public void parseXML_fileMissing_success(){
        //given
        String path = "src/test/resources/Test1.xml";
        //when
        List<Employee> list = XmlAdapter.parseXML(path);
        //then
        List<Employee> expected = new ArrayList<>();
        Assertions.assertEquals(expected,list);
    }
}
