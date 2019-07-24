package com.hys.service;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * @program: hys
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-10 16:08
 **/
public class UserCommand extends HystrixCommand<String> {

    /*
    HystrixCommand 属性
    execution配置控制得是HystrixCommand.run()的执行

       -execution.isolation.strategy:该属性用来设置HystrixCommand.run()执行的隔离策略

            THREAD：通过线程池的隔离策略。它在独立的线程上执行，并且它的并发限制受线程池中线程数量的限制

            SEMAPHORE: 通过信号量隔离的策略。它在调用线程上执行，并且它的并发限制收信号量计数的限制
       -execution.isolation.thread.timeoutInMillisecond:该属性用来配置HystrixCommand执行的超时时间，单位为毫秒。超时后进行服务降级
       -execution.timeout.enable:该属性用来配置HystrixCommand.run()的执行是否启用超时时间，默认为true
       -execution.isolation.thread.interruptOnTimeout:该属性用来配置当HystrixCommand.run()执行超时的时候是否将它中断
       -execution.isolation.thread.interruptOnCancel:该属性用来配置当HystrixCommand.run()执行被取消的时候是否要将它中断
     */





    private static final HystrixCommandKey GETTER_KEY=HystrixCommandKey.Factory.asKey("CommandKey");

    private RestTemplate restTemplate;

    private long personId;

    public UserCommand( RestTemplate restTemplate, long id) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey"))
                .andCommandKey(GETTER_KEY)

                //       execution.isolation.strategy:该属性用来设置HystrixCommand.run()执行的隔离策略
                //
                //          THREAD：通过线程池的隔离策略。它在独立的线程上执行，并且它的并发限制受线程池中线程数量的限制
                //
                //          SEMAPHORE: 通过信号量隔离的策略。它在调用线程上执行，并且它的并发限制收信号量计数的限制
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE))

                // -execution.isolation.thread.timeoutInMillisecond:该属性用来配置HystrixCommand执行的超时时间，单位为毫秒。超时后进行服务降级
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000))

                // -execution.timeout.enable:该属性用来配置HystrixCommand.run()的执行是否启用超时时间，默认为true
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(true))

                // -execution.isolation.thread.interruptOnTimeout:该属性用来配置当HystrixCommand.run()执行超时的时候是否将它中断
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadInterruptOnTimeout(false))

                // -execution.isolation.thread.interruptOnCancel:该属性用来配置当HystrixCommand.run()执行被取消的时候是否要将它中断
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadInterruptOnFutureCancel(false))


        );
        this.restTemplate = restTemplate;
        this.personId = id;
    }



    @Override
    protected String run() throws Exception {

        String str = restTemplate.getForObject("http://FIRST-SERVICE-PROVIDER/first/person?personId={personId}", String.class, personId);

        flushCache(personId);

        return str;
    }



    /**
     * 实现该方法可以让请求命令具备缓存的功能。当不同的外部请求处理逻辑调用了同一个依赖服务时，Hystrix会根据getCacheKey方法返回的值来区分
     * 是否重复的请求，如果他们的cacheKey相同，那么该依赖服务只会在第一个请求到达时被真实的调用一次，另一个请求则是直接从请求缓存中返回结果，
     * 所以通过开启缓存可以让我们实现的Hystrix命令具备以下几项好处
     * 1.减少重复请求，降低依赖服务的并发度
     * 2.在同一用户请求的上下文中，相同依赖服务的返回数据始终保持一致。
     * 3.请求缓存在run()和construct()执行之前生效，所以可以有效的减少不必要的线程开销。
     *
     * 有三个注解用来支持缓存的
     * 1.@CacheResult : 该注解用来标记请求命令返回的结果应该被缓存，他必须与@HystrixCommand注解结合使用
     *
     * 2.@CacheRemove : 该注解用来让请求命令的缓存失效，失效的缓存根据定义的Key决定
     *
     *
     * 3.@CacheKey ： 该注解用来在请求命令的参数上标记，使其作为缓存的Key值如果没有标注则会使用所有参数
     *
     */
    @Override
    protected String getCacheKey() {
        System.out.println("使用了Hystrix缓存--key值为:"+personId);
        return String.valueOf(personId);
    }



    private static void flushCache(long id){
        //刷新缓存，根据id进行清理
        System.out.println("刷新缓存");
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));

    }




    @Override
    protected String getFallback() {
        Throwable executionException = getExecutionException();
        System.out.println(executionException.getMessage());
        executionException.printStackTrace();
        return "fail";
    }


}

