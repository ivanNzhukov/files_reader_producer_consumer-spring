package ru.testtask.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.testtask.Interface.Reader;
import ru.testtask.Interface.Writer;
import ru.testtask.beans.Consumer;
import ru.testtask.beans.Handler;
import ru.testtask.beans.Producer;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Main {


    public static void main(String[] args) throws InterruptedException {


        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        File file;

        for (int i = 0; i < args.length; i++) {


            file = new File(args[i]);
            final int queueSize = 10;

            if (file.exists()) {

                BlockingQueue<LinkedList> queue = new LinkedBlockingQueue(queueSize);
                List<String> typesList = new ArrayList<String>();
                List<Thread> threadList = new ArrayList<Thread>();


                typesList.addAll(ExtensionType.checkFileExtension(file.toString()));
                String extension = typesList.get(0);
                String typeFile = typesList.get(1);

                if (!extension.equals("json") && !extension.equals("csv")) {
                    System.out.println("Вы используете Демо-версию продукта и не можете читать файлы формата " + extension);
                    continue;

                } else if (!extension.equals("none")) {


                    System.out.println();
                    System.out.println();
                    System.out.println();

                    Reader reader = (Reader) context.getBean(typeFile);
                    Writer writer = (Writer) context.getBean("writer");


                    Handler handler = (Handler) context.getBean("Handler", writer, reader, queueSize);
                    Producer producer = (Producer) context.getBean("Producer", handler, queue);
                    Consumer consumer = (Consumer) context.getBean("Consumer", handler, queue);

                    handler.setFile(file, extension);


                    threadList.add(new Thread(producer, "Producer"));
                    threadList.add(new Thread(consumer, "Consumer"));


                    for (Thread thread : threadList) {
                        thread.start();
                    }

                    for (Thread thread : threadList) {
                        try {
                            thread.join();

                        } catch (Exception e) {
                            System.out.println("Interrupted Exception thrown by : " + thread.getName());
                        }
                    }
                } else continue;

            } else System.out.println("Файл '" + file.getName() + "' отсутствует!");
            System.out.println();
        }
    }


}

