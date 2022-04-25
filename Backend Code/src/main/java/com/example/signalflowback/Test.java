package com.example.signalflowback;

import java.util.ArrayList;
import java.util.ListIterator;

import static java.lang.Thread.sleep;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> yes = new ArrayList<>();
        yes.add("hello");


        ListIterator<String> aItr = yes.listIterator();
        while (aItr.hasNext()) {
            String a = aItr.next();
            ListIterator<String> bIter = yes.listIterator(aItr.nextIndex());
            while (bIter.hasNext()) {
                System.out.println("I am in");
            }

            yes.add("potato");
            System.out.println(yes);
            sleep(1000);
        }
    }
}
