package ru.testtask.beans;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;


@Component
@Scope("prototype")
public class Consumer implements Runnable {

    Handler handler;
    boolean isInterrupted;
    private BlockingQueue<LinkedList> queue;

    Consumer() {
    }

    public Consumer(Handler handler, BlockingQueue queue) {
        this.queue = queue;
        this.handler = handler;
    }


    @Override
    public void run() {


        while (!isEnd()) {

            try {
                System.out.println("Спит Consumer");

                System.out.println("Это поток 1----------------------" + Thread.currentThread().getName());
                System.out.println("Проснулся  Consumer");
                handler.getHandle(queue.take());
                System.out.println("Осталось в очереди "+queue.remainingCapacity());
                Thread.sleep(1000);
                System.out.println("Это поток 2----------------------" + Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.println("Ошибка вывода: " + e);
                System.out.println("Закрываем Consumer 1");
                System.out.println("Exception sss");
            }
        }
        System.out.println("Закрываем Consumer 2");
    }


    private boolean isEnd() {

        if (queue.isEmpty() && handler.isFileEnd()) {
            return true;
        } else return false;
    }
}