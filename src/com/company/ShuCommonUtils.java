package com.company;

import java.util.Scanner;

public class ShuCommonUtils {

    public static Boolean check(Boolean run) {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        System.out.println("Y to continue else N to end");
        input = scanner.nextLine();
        run = input.equals("N") ? false : true;

        return run;
    }
}
