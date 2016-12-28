package com.zyy.examples.guava.eventbus;

import com.google.common.base.Objects;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * Created by zhouyinyan on 16/12/28.
 */
public class EventBusTest {

    @Test
    public void testEventBus(){

        EventBus eventBus = new EventBus("test bus");

//        EventBus eventBus = new AsyncEventBus("test async bus", Executors.newFixedThreadPool(2));

        new EventComsumer(eventBus);
//        new DeadEventComsumer(eventBus);
        EventProducer producer = new EventProducer(eventBus);
        for (int i = 0; i < 5; i++) {
            producer.produceEvent();
        }

    }

    class EventProducer {

        EventBus eventBus;

        public EventProducer(EventBus eventBus) {
            this.eventBus = eventBus;
        }

        public void produceEvent(){
            eventBus.post(new TestEvent("id1", "desc1"));
        }

    }


    class EventComsumer{

        EventBus eventBus;

        public EventComsumer(EventBus eventBus) {
            this.eventBus = eventBus;
            this.eventBus.register(this);
        }

        @Subscribe
        @AllowConcurrentEvents
        public void comsume(TestEvent event){
            out.println("thradname:"+Thread.currentThread().getName());
            out.println("evnet:"+event);

        }

    }


    class DeadEventComsumer{

        EventBus eventBus;

        public DeadEventComsumer(EventBus eventBus) {
            this.eventBus = eventBus;
            this.eventBus.register(this);
        }

        @Subscribe
        @AllowConcurrentEvents
        public void comsume(DeadEvent event){
            out.println("deadevent:"+event.getEvent());
            out.println("source:"+event.getSource());
        }

    }


    class TestEvent {
        String id;
        String desc;

        public TestEvent(String id, String desc) {
            this.id = id;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("id", id)
                    .add("desc", desc)
                    .toString();
        }
    }
}
