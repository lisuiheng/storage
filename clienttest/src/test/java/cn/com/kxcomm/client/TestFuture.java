package cn.com.kxcomm.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;



public class TestFuture{
    private final static Logger log = LoggerFactory.getLogger(TestFuture.class);

    public static void main(String[] args){

        final FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {

            public String call() {

                try{

                    Thread.sleep(10000);

                }catch(Exception e){

                    e.printStackTrace();

                }

                return "[future task finished]";

            }

        });

        Thread waiter = new Thread(new Runnable(){

            public void run(){

                log.info("Waiter started its work");

                try{

                    Thread.sleep(5000);

                }catch(Exception e){

                    e.printStackTrace();

                }

                log.info("Waiter finished work and start waiting future task to return");



                String ret = null;

                for(int i = 0; i < 10; i++){

                    try{

                        ret = ft.get(1000, TimeUnit.MILLISECONDS);

                        break;

                    }

                    catch(TimeoutException e){

                        //e.printStackTrace();

                        log.info("Waiter waited for "+(i+1)+" second(s), but not returned yet. ret=["+ret+"].");

                    }

                    catch(Exception e){

                        e.printStackTrace();

                    }

                }

                log.info("Future task returned with:"+ret);

            }

        });

        waiter.start();

        ft.run();



    }

}