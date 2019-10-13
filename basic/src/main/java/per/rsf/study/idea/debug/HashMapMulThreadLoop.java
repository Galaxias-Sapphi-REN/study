/**
 * per.rsf.idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. "sapphire" create me at 2019-09-30 05:28.
 * 2.
 * ****************************************************************************
 * Copyright (c) 2019, sapphire All Rights Reserved.O(∩_∩)O
 */

package per.rsf.study.idea.debug;

import java.util.HashMap;

/**
 * @program: java
 * @description: <p>HashMap多线程断点测试</p>
 * <br/>
 * @author: sapphire
 */
public class HashMapMulThreadLoop{

    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(2);

    public static void main(String[] args) throws InterruptedException {
        map.put(null, null);
        map.put(null, 10);
        map.put(10, null);

        new Thread("Thread1-Name") {
            public void run() {
                System.out.println("Thread1-Name Start");
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(1, 1);//断点位置 1
                System.out.println("Thread1-Name End" + map);
            }

        }.start();

        new Thread("Thread2-Name") {
            public void run() {
                try {
                    System.out.println("Thread2-Name Start");
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(2, 2);// 断点位置2
                System.out.println("Thread2-Name End" + map);
            }

        }.start();

        new Thread("Thread3-Name") {
            public void run() {
                try {
                    System.out.println("Thread3-Name Start");
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(3, 3);// 断点位置3
                System.out.println("Thread3-Name End" + map);
            }

        }.start();


        // 断点位置 4
        System.out.println("Thread-Main-Name End");
        System.out.println("Thread-Main-Name " + map);

        // 断点位置 5
        Thread.sleep(1000);

    }
}
