package ru.testtask.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.testtask.Interface.Reader;
import ru.testtask.Interface.Writer;
import ru.testtask.beans.Consumer;
import ru.testtask.beans.ExtensionType;
import ru.testtask.beans.Handler;
import ru.testtask.beans.Producer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Main {



    public static void main(String[] args) throws InterruptedException {


        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


        for (int i = 0; i < args.length; i++) {
            List<Thread> threadList = new ArrayList<Thread>();
            String file = args[i];
            int queueSize = 10;
            BlockingQueue queue = new LinkedBlockingQueue(queueSize);
            List<String> extension = new ArrayList<String>();
            extension.addAll(ExtensionType.checkFileExtension(file));

            if (!extension.get(0).equals("none")) {
                Reader reader = (Reader) context.getBean(extension.get(1));
                Writer writer = (Writer) context.getBean("writer");


                Handler handler = (Handler) context.getBean("Handler", writer, reader, queueSize);
                Producer producer = (Producer) context.getBean("Producer", handler, queue);
                Consumer consumer = (Consumer) context.getBean("Consumer", handler, queue);

                handler.setFile(file, extension.get(0));


                threadList.add(new Thread(producer, "Producer"));
                threadList.add(new Thread(consumer, "Consumer"));


                for (Thread thread : threadList) {
                    thread.start();
                }

                for (Thread thread : threadList) {
                    try {
                        thread.join();
                        System.out.println("Это поток              " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted Exception thrown by : " + thread.getName());
                    }
                }
            } else break;

        }
        System.out.println("Это поток              " + Thread.currentThread().getName());
    }

}

