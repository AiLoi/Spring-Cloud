package com.hys.service;

import com.hys.entity.pojo.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


/**
 * @program: hys
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-05 16:53
 **/
@SuppressWarnings("all")
@Service
public class HelloService {


    @Autowired
    private RestTemplate restTemplate;




    /*
    1. HystrixCommand:用于在依赖的服务返回单个操作结果的时候

    execute():同步执行，从依赖的服务返回一个单一的结果对象，或是在发生错误的时候抛出异常。

    queue(): 异步执行，直接返回一个Future对象，其中包含了服务执行结束时要返回的单一结果对象。

    2. HystrixObservableCommand:用于在依赖的服务返回多个操作结果时

    observe():返回Observable对象，他代表了操作的多个结果他是一个HotObservable.

    toObservable():同样会返回Observable对象，也代表了操作的多个结果，但它返回的是一个Cold Observable;

    命令模式，将来自客户端的请求封装成一个对象，从而让你可以使用不同的请求对客户端进行参数化.





     */

    /**
     * fallback处理
     * 当命令自行失败的时候，Hystrix会进入fallback尝试回退处理，我们通常也称该操作为“服务降级”。
     * 而能引起服务降级处理的情况有以下几种：
     * 1.当命令处于“熔断/短路”状态，断路器是打开的时候。
     * 2.当前命令的线程池，请求队列或者信号量被占满的时候
     * 3.HystrixObservableCommand.construct()或HystrixCommand.run()抛出异常的时候。
     * <p>
     * <p>
     * HystrixCommand 和HystrixObservableCommand中实现降级逻辑时还略有不同
     * <p>
     * 1.当使用HystrixCommand的时候，通过实现HystrixCommand.getFallback()来实现服务降级逻辑
     * <p>
     * 2.当使用HystrixObservableCommand的时候，通过HystrixObservableCommand.resumeWithFallback()实现服务降级逻辑，
     * 该方法会返回一个Observable对象来发射一个或多个降级结果
     * <p>
     * Hystrix 会根据不同的执行方法做出不同的处理。
     * <p>
     * 1.execute() ：抛出异常。
     * 2.queue():正常返回Future对象，但是当调用get()来获取结果的时候会抛出异常。
     * 3.observe():正常返回Observable对象，当订阅它的时候，将立即通过调用订阅者的onError方法来通知中止请求。
     * 4.toObservable():正常返回Observable对象，当订阅它的时候，将通过调用订阅者的onError方法来通知中止请求。
     */


    //当产生该异常时不会走该方法  ，会直接抛出异常

    //缓存结果
    @CacheResult(cacheKeyMethod = "getPersonId")
    @HystrixCommand(fallbackMethod = "helloFallback", ignoreExceptions = {HystrixBadRequestException.class}, commandKey = "helloService", groupKey = "PersonGroup", threadPoolKey = "PersonThread")
    public String helloService(/*@CacheKey("personId")*/ long personId) {

        return restTemplate.getForObject("http://FIRST-SERVICE-PROVIDER/first/person?personId={personId}", String.class, personId);
    }


    @HystrixCollapser(batchMethod = "findAll",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
                    //该参数用来设置批处理过程中每个命令延迟的时间，单位为毫秒
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "200"),
                    //设置一次合并请求的最大数量
                    @HystrixProperty(name = "maxRequestsInBatch", value = ""+Integer.MAX_VALUE),
                    //该参数用来设置批处理过程中是否开启请求缓存
                    @HystrixProperty(name = "requestCache.enabled", value = "false")
            })

    public Future<Person> find(Long id) {

        return restTemplate.getForObject("http://FIRST-SERVICE-PROVIDER/first/person?personId={1}", Future.class, id);
    }


    //   @CacheResult
    @HystrixCommand(fallbackMethod = "getFallback", commandProperties = {

            //       execution.isolation.strategy:该属性用来设置HystrixCommand.run()执行的隔离策略
            //
            //          THREAD：通过线程池的隔离策略。它在独立的线程上执行，并且它的并发限制受线程池中线程数量的限制
            //
            //          SEMAPHORE: 通过信号量隔离的策略。它在调用线程上执行，并且它的并发限制收信号量计数的限制

            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),

            // -execution.isolation.thread.timeoutInMillisecond:该属性用来配置HystrixCommand执行的超时时间，单位为毫秒。超时后进行服务降级

            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    },

            threadPoolProperties = {
                    //该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
                    @HystrixProperty(name = "coreSize", value = "20"),
                    //该参数用来设置线程池的最大队列大小。当设置为-1的时候，线程池将使用SynchronousQueue实现的队列，否则将使用LinkedBlockingQueue实现队列
                    @HystrixProperty(name = "maxQueueSize", value = "-1"),
                    //该参数用来为队列设置拒绝阈值。通过该参数，即使队列没有达到最大值也能拒绝请求。该参数主要是对LinkedBlockingQueue队列的补充，因为LinkedBlockingQueue队列不饿能动态修改它的对象大小。默认为5
                    //注意LinkedBlockingQueue属性为-1时无法生效
                    @HystrixProperty(name = "queueSizeRejectionThreshold",value = "10"),
                    //该参数用来设置滚动时间窗的长度，单位为毫秒
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "10000"),
                    //该参数用来设置滚动时间窗被划分成“桶”的数量
                    //注意metrics.rollingStats.timeInMilliseconds必须能被metrics.rollingStats.numBuckets整除，否则抛出异常
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10")

            }
    )
    public List<Person> findAll(List<Long> ids) {

        System.out.println(ids);
        ParameterizedTypeReference<List<Person>> res = new ParameterizedTypeReference<List<Person>>() {
        };

        HttpEntity entity = new HttpEntity(ids, null);
        ResponseEntity<List<Person>> responseEntity = restTemplate.exchange("http://FIRST-SERVICE-PROVIDER/first/all_person", HttpMethod.POST, entity, res);

        return responseEntity.getBody();
    }


    /**
     * 清除缓存
     *
     * @param id
     */
    @CacheRemove(commandKey = "getPersonId")
    @HystrixCommand
    public void update(long id) {
        //这里应该是一个更新命令 ，
        restTemplate.getForObject("http://FIRST-SERVICE-PROVIDER/first/person?personId={personId}", String.class, id);
    }


    /**
     * 异步
     *
     * @return
     */
    public Future<String> getPerson() {

        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://first-service-provider/person?personId=1", String.class);
            }
        };
    }


    public String getPersonId(long personId) {
        System.out.println("缓存的key:" + personId);
        return String.valueOf(personId);
    }

    //表示使用observer()执行
    //LAZY表示使用toObservable()执行
    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    public Observable<String> getPersonObs() {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        String s = restTemplate.getForObject("http://first-service-provider/person?personId=1", String.class);
                        subscriber.onNext(s);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }


    public List<Person> getFallback(List<Long> ids) {

        Person person = new Person();
        person.setNote("error");
        person.setId(ids.get(0));
        person.setName("error");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        System.out.println(person);
        return personList;
    }

    public String helloFallback(long id) {

        return "error：" + id;
    }

}

