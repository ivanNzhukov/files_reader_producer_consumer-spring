package ru.testtask.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.testtask.Interface.Reader;
import ru.testtask.Interface.Writer;
import ru.testtask.beans.Consumer;
import ru.testtask.beans.Handler;
import ru.testtask.beans.Producer;
import ru.testtask.readers.OtherReader;
import ru.testtask.readers.JsonReader;
import ru.testtask.writer.JsonWriter;

import java.util.concurrent.BlockingQueue;


@Configuration
@ComponentScan
public class AppConfig {


    @Bean(name = "Handler")
    @Scope("prototype")
    public Handler handler(Writer writer, Reader reader, int count) {
        return new Handler(writer, reader, count);
    }

    @Bean(name = "Producer")
    @Scope("prototype")
    public Producer producer(Handler handler, BlockingQueue queue) {
        return new Producer(handler,queue);
    }

    @Bean(name = "Consumer")
    @Scope("prototype")
    public Consumer consumer(Handler handler, BlockingQueue queue) {
        return new Consumer(handler,queue);
    }

    @Bean(name = "jsonReader")
    Reader jsonReader() {
        return new JsonReader();
    }

    @Bean(name = "otherReader")
    Reader otherReader() {
        return new OtherReader();
    }

    @Bean(name = "writer")
    Writer writer() {
        return new JsonWriter();
    }


}
