package com.mycompany.race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static boolean RACE_BEGIN;
    private static boolean RACE_END;
    private static Lock lock;

    static {
        CARS_COUNT = 0;
        RACE_BEGIN = false;
        RACE_END = false;
        lock = new ReentrantLock();
    }

    private Race race;
    private int speed;
    private String name;
    private CountDownLatch cdl;
    private  Semaphore semaphore;
    private RaceEnd raceEnd;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Semaphore getSemaphore () {
        return this.semaphore;
    }

    public Car(Race race, int speed, CountDownLatch cdl, Semaphore  smp) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cdl = cdl;
        this.semaphore = smp;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            this.cdl.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cdl.await();
            if (!RACE_BEGIN) {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                RACE_BEGIN = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

//       победить может только кто-то один, поэтому сделал заглушку
        lock.lock();
        if (!RACE_END) {
            System.out.println(this.name + " - WIN!!!");
            RACE_END = true;
        }
        lock.unlock();
    }
}

