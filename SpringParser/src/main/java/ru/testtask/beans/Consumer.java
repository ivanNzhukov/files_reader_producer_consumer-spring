package ru.testtask.beans;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;


@Component
@Scope("prototype")
public class Consumer implements Runnable {

    private Handler handler;
    private BlockingQueue<LinkedList> queue;

    Consumer() {
    }
@Autowired
    public Consumer(Handler handler, BlockingQueue queue) {
        this.queue = queue;
        this.handler = handler;
    }


    @Override
    public void run() {


        while (!isEnd()) {

            try {

                if (queue.size() > 0) {

                    handler.getQueue(queue.take());
                    Thread.sleep(100);
                } else continue;
            } catch (Exception e) {
                System.out.println("Ошибка вывода: " + e);
            }
        }
    }


    private boolean isEnd() {

        if (queue.isEmpty() && handler.isFileEnd()) {
            return true;
        } else return false;
    }
}