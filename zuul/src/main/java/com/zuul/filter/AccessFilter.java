package com.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zuul
 * @description: token验证器
 * @author: Ailuoli
 * @create: 2019-07-23 10:22
 **/

@Slf4j
@Component
public class AccessFilter extends ZuulFilter {

    /*
    过滤器类型，他决定过滤器在请求的哪个生命周期中执行，这里定义为pre，代表会在请求被路由之前执行

    pre:可以在请求被路由之前调用
    ServletDetectionFilter: 它的执行顺序为-3，是最新被执行的过滤器，主要用来检测DispatcherServlet处理运行。
    它的返回结果会以bool类型保存并返回给isDispatcherServletRequest参数中，这样可以使用RequestUtils.isDispatcherServletRequest()
    和RequestUtils.isZuulServletRequest()方法来判断请求处理的源头，以实现后续不同的处理机制，除了/zuul/*路径访问的请求会绕过DispatcherServlet被
    ZuulServlet处理，主要用来应对处理大文件上传的情况。另外，对于ZuulServlet的访问路径/zuul/*,我们可以通过zuul.servletPath参数来进行修改


    routing: 在路由请求时被调用

    post: 在routing和error过滤器之后被调用


    error: 处理请求时发生错误时被调用

     */
    @Override
    public String filterType() {
        return "pre";
    }

    /*
    过滤器的只能怪顺序，当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
    数值越小优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /*
    判断该过滤器是否需要被执行。实际应用中可以使用该函数来指定过滤器的有效范围
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    /*
    过滤器的具体逻辑。这里我们通过            requestContext.setSendZuulResponse(false);   令zuul过滤该请求，不对其进行路由，然后通过
                                          requestContext.setResponseStatusCode(401);  设置了返回的错误码
                                          requestContext.setResponseBody(String.valueOf(map));  对返回内容进行了设置
     */
    @Override
    public Object run() throws ZuulException {

        //获取request对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");

        log.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());

        Object accessToken = request.getParameter("accessToken");

        if(accessToken == null){

            Map<String,Object> map = new HashMap<>();
            map.put("status",401);
            map.put("msg","登录失效");
            map.put("body",null);

            log.warn("access token is empty !");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody(String.valueOf(map));
            return null;
        }
        log.info("access token ok");
        return null;
    }
}

