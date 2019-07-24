package com.hys.service;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.HystrixRequestCache;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.Arrays;

/**
 * @program: hys
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-10 18:36
 **/

public class PersonObservableCommand extends HystrixObservableCommand<String> {


    private RestTemplate restTemplate;

    private long [] ids;


    public PersonObservableCommand(RestTemplate restTemplate, long[] ids) {
        super(HystrixObservableCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("CmmandGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
        );
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected Observable<String> construct() {

        System.out.println(Thread.currentThread().getName() + "is running.....");
            /*
            observable 有三个关键的事件方法，分别为onNext,onCompleted,onError
             */
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {

                        for(long id : ids) {
                            String str = restTemplate.getForObject("http://first-service-provider/first/person?personId={id}", String.class,id);
                            subscriber.onNext(str);
                        }

                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io());
    }


    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()){
                        String str = "error";
                        subscriber.onNext(str);
                        subscriber.onCompleted();
                    }

                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }



}
