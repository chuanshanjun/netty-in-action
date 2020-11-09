package nia.chapter7;

import java.util.Collections;
import java.util.List;

/**
 * @author:ChuanShanJun
 * @date:2020/11/8
 * @description:
 */
public class EventLoopExamplesYoung {
    public static void executeTaskInEventLoop() {
        boolean terminated = true;

        while (!terminated) {
            List<Runnable> readyEvents = blockUntilEventsReady();
            for (Runnable ev : readyEvents) {
                ev.run();
            }
        }
    }

    private static final List<Runnable> blockUntilEventsReady() {
        return Collections.<Runnable>singletonList(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
