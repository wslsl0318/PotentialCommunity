package com.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class Test implements Comparator<Test> {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int id;
    public int value;

    public Test() {

    }
    @Override
    public int compare(Test t1, Test t2) {
        Test test1 = t1;
        Test test2 = t2;
        if(t1.value < t2.value) {
            return 1;
        } else if(t1.value == t2.value) {
            if(t1.id > t2.id) {
                return 1;
            } else if(t1.id == t2.id) {
                return 0;
            } else if(t1.id > t2.id) {
                return -1;
            }
        } else if(t1.value > t2.value){
            return -1;
        }
            return 0;
    }

    public static void main(String[] args) {
        for(int z =10; z<30; z++) {
            System.out.println("z=" + z);
            int x = 0;
            int y = 0;
            double tt = Math.ceil((double)z/10);
            System.out.println("tt=" + tt);
            int left = (int) (z / tt) % 10;
            if(left > 0 && left < 5) {

            }
            for (y = 0; y < z; y++) {
                x = z - y;
                if (x % 2 == 0 && (x / tt + y == 10)) {
                    break;
                } else {
                    x = -1;
                }
            }
            System.out.println("x=" + x);
            System.out.println("y=" + y);
            System.out.println(x + "/" + tt +" +" + y + "= 10");
            System.out.println("");
        }


        System.out.println((float)99/100);

        double range1 = Math.ceil(11.9);
        double range2 = Math.floor(11.9);
        double range3 = Math.ceil(11.1);
        double range4 = Math.floor(11.1);

        TreeSet<Integer> awer = new TreeSet<>();
        awer.add(6777);
        awer.add(123);
        awer.add(1235);
        awer.add(13);
        for(int asdf : awer) {
            System.out.println(asdf);
        }
        System.out.println(awer.first());
        System.out.println(awer.last());
        for(int asdf : awer) {
            System.out.println(asdf);
        }

        int a = 0x7fffffff;
        int b = 0x3fffffff;
        System.out.println(b+b);
        System.out.println((b+b)==Integer.MAX_VALUE-1);

        DecimalFormat df = new DecimalFormat("0.0000");
        double num = 123123.4363245743567476;
        df.format(num);
        System.out.println(num);


        /* test addAll meathod that if it will copy the address */
        ArrayList<Test> tests1 = new ArrayList<>();
        ArrayList<Test> tests2 = new ArrayList<>();
        ArrayList<Test> tests3 = new ArrayList<>();
        Test test1 = new Test();
        test1.id = 100;
        test1.value = 100;
        tests1.add(test1);
        tests2.addAll(tests1);


        Test test2 = new Test();
        test2.id = 12;
        test2.value = 200;
        tests2.add(test2);
        Test test3 = new Test();
        test3.id = 3;
        test3.value = 300;
        tests2.add(test3);
//        tests2.get(0).setId(33);
//        tests2.remove(test1);
//        tests2.remove(0);
        Collections.sort(tests2, new Comparator<Test>() {
           public int compare(Test t1, Test t2) {
               if(t1.id < t2.id) {
                   return -1;
               } else if(t1.id > t2.id) {
                   return 1;
               }
               return 0;
           }
        });

        /* test TreeSet add the same object */
        /*TreeSet<Test> May27th = new TreeSet<>();
        //Exception in thread "main" java.lang.ClassCastException: com.test.Test cannot be cast to java.lang.Comparable
        //override comparator
        May27th.add(test1);
        System.out.println(May27th.add(test1));*/
        TreeSet<Integer> May27th = new TreeSet<>();
        May27th.add(3);
        System.out.println(May27th.add(3));
        System.out.println(May27th.add(4));

        /* addAll that will delete both obj */
        ArrayList<ArrayList<Integer>> lists1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> lists2 = new ArrayList<>();
        TreeSet<Integer> lists3 = new TreeSet<>();
        ArrayList<Integer> lists4 = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> lists5 = new ArrayList<>();
        list1.add(0);
        list1.add(8);
        list1.add(9);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        lists1.add(list1);
        lists1.add(list2);
        lists2.addAll(lists1);
        lists3.addAll(lists1.get(0));
        lists4.addAll(lists1.get(0));
//        lists2.get(0).remove((Integer)8);
//        lists2.remove(list1);
//        lists3.remove((Integer)8);
        lists4.remove((Integer)8);

        for(int i=0;i<5;i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            lists5.add(temp);
        }
        lists5.get(3).add(1);
        if(!lists5.get(3).isEmpty()) {
            System.out.println("1");
        }

    }
}
