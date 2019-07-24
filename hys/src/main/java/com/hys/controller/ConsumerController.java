package com.hys.controller;

import com.hys.entity.pojo.Person;
import com.hys.entity.pojo.User;
import com.hys.service.HelloService;
import com.hys.service.PersonObservableCommand;
import com.hys.service.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: hys
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-05 17:02
 **/
@SuppressWarnings("all")
@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/ribbon-consumer")
    public String helloConsumer() {

        return helloService.helloService(8L);

    }


    @RequestMapping("/hystrix_command/synchronize")
    public List<Person> Comm() throws ExecutionException, InterruptedException {


        String result = helloService.helloService(8L);
        System.out.println(result);


        List<Long> longs = new ArrayList<>();
        Long[] arrlong = {10L,11L,12L};
        longs.addAll(Arrays.asList(arrlong));
        List<Person> personList = helloService.findAll(longs);
        System.out.println(personList);

        /*
        继承HystrixCommand的 queue() 和  execute();
         */

        //同步执行
        UserCommand userCommand = new UserCommand(restTemplate, 1L);
        String string = userCommand.execute();
        System.out.println(string);
//
//
//        //异步执行
//        UserCommand userCommand1 = new UserCommand(restTemplate, 2L);
//        Future<String> future = userCommand1.queue();
//        System.out.println(future.get());
//
//
//
        /******************************************************************************************/

        /*
        继承HystrixCommand 的observe()  和  toObservable()
         */

        //observe() 返回Hot Observable, 该命令会在observe()调用的时候立即执行，当Observable每次被订阅的时候会重放它的行为。

//        UserCommand userCommand2 = new UserCommand(restTemplate,3L);
//        Observable<String> ohValue = userCommand2.observe();
//        List<String> list = new ArrayList<>();
//        //订阅  ，这里是发布者被订阅者订阅
//        ohValue.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("聚合完了所有的请求");
//                System.out.println(list);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(String s) {
//                list.add(s);
//            }
//        });
//        System.out.println(list.size() + list.toString());

        //注意一次只执行一次execute();
        //toObservable() 执行后，命令不会被立即执行，只有当所有订阅者都订阅后它才会执行。
//        UserCommand userCommand3 = new UserCommand(restTemplate,4L);
//        Observable<String> ocValue = userCommand3.toObservable();
//        ocValue.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("完成了所有请求");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println(s);
//            }
//        });



        long [] ids = {5L,6L,7L};
        PersonObservableCommand personObservableCommand = new PersonObservableCommand(restTemplate,ids);
//        Observable<String> coldObservable = personObservableCommand.toObservable();
//        coldObservable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("完成所有PersonObservableCommand");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println(s);
//            }
//        });


        Observable<String> hotObservable = personObservableCommand.observe();
        hotObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("完成了所有的请求！");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });


        Observable<String> obs = helloService.getPersonObs();


        return personList;
    }

}

