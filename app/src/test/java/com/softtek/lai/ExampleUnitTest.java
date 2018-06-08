package com.softtek.lai;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        List<String> parentList = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            parentList.add(String.valueOf(i));
        }
        List<String> subList = parentList.subList(1, 3);
        System.out.println("raw="+parentList);
        System.out.println("subList="+subList);

        subList.set(0, "new 1");
        System.out.println("set after parentList="+parentList);//output: 0, new 1, 2, 3, 4
        System.out.println("set after subList="+subList);//output: 0, new 1, 2, 3, 4

        //structural modification by sublist, reflect parentList
        subList.add(String.valueOf(2.5));
        System.out.println("add after parentList="+parentList);//output:0, new 1, 2,    2.5, 3,    4
        System.out.println("set after subList="+subList);//output: 0, new 1, 2, 3, 4

        //non-structural modification by parentList, reflect sublist
        parentList.set(2, "new 2");
        System.out.println("set after parentList="+parentList);//output: 0, new 1, 2, 3, 4
        System.out.println("set after subList="+subList);//output: new 1, new 2
    }

}