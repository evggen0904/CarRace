package com.mycompany.race;

// Организуем гонки
// Есть набор правил:
// Все участники должны стартовать одновременно, несмотря на то что на подготовку у каждого уходит разное время
// В туннель не может заехать одновременно больше половины участников(условность)

// Как только первая машина пересекает финиш, необходимо ее объявить победителем
// Только после того как все завершат гонку нужно выдать объявление об окончании


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
        Semaphore smp = new Semaphore(CARS_COUNT/2);
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cdl, smp);
        }

        Thread[] raceRunners = new Thread[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            raceRunners[i] = new Thread(cars[i]);
            raceRunners[i].start();
        }

        for (int i = 0; i < cars.length; i++) {
            try {
                raceRunners[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
