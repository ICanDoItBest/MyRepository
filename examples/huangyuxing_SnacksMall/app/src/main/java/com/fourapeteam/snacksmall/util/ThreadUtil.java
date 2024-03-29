package com.fourapeteam.snacksmall.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jun on 2016/10/8.
 */
public class ThreadUtil {

    private static ExecutorService executorService;

    public static void execute(Runnable runnable){
        if (executorService==null){
            executorService = Executors.newFixedThreadPool(10);
        }
        executorService.submit(runnable);
    }

}
