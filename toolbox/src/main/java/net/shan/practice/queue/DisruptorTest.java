package net.shan.practice.queue;


import com.lmax.disruptor.*;

import static com.lmax.disruptor.RingBuffer.createMultiProducer;

/**
 * Created by sam.js on 14-3-5.
 */
public class DisruptorTest {

    private final RingBuffer<ValueEvent> ringBuffer =
            createMultiProducer(ValueEvent.EVENT_FACTORY, 1024, new YieldingWaitStrategy());

    private void test(){


        // 构造拥有两个WorkProcessor的WorkerPool
        final WorkHandler[] handlers = new WorkHandler[2];
        for (int i = 0; i < 2; i++)
        {
            handlers[i] = new MyEvent();
        }

        WorkerPool<ValueEvent> workerPool =
                new WorkerPool<ValueEvent>(ringBuffer,
                        ringBuffer.newBarrier(),
                        new FatalExceptionHandler(),
                        handlers);

        // 构造反向依赖
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 阶段1：申请节点，并将消息放入节点中
        long next = ringBuffer.next();
        ringBuffer.get(next).setValue(0);

        // 阶段2：提交节点
        ringBuffer.publish(next);

        next = ringBuffer.next();
        ringBuffer.get(next).setValue(1);

        // 阶段2：提交节点
        ringBuffer.publish(next);

        next = ringBuffer.next();
        ringBuffer.get(next).setValue(2);

        // 阶段2：提交节点
        ringBuffer.publish(next);
    }

    class MyEvent implements WorkHandler{

        @Override
        public void onEvent(Object o) throws Exception {
            System.out.println("MyEvent : " + o);
        }
    }

    public static void main(String[] args){
        new DisruptorTest().test();
    }
}

final class ValueEvent
{
    private long value;

    public long getValue()
    {
        return value;
    }

    public void setValue(final long value)
    {
        this.value = value;
    }

    public static final EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>()
    {
        public ValueEvent newInstance()
        {
            return new ValueEvent();
        }
    };
}
