package com.groovy;

import com.groovy.config.FilterConfiguration;
import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableConfigurationProperties({FilterConfiguration.class})
@SpringBootApplication
public class GroovyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroovyApplication.class, args);
    }



    @Bean
    public FilterLoader filterLoader(FilterConfiguration filterConfiguration){

        FilterLoader filterLoader = FilterLoader.getInstance();

        filterLoader.setCompiler(new GroovyCompiler());

       // String scriptRoot = System.getProperty("zuul.filter.root","groovy/filters");

        try {

            FilterFileManager.setFilenameFilter(new GroovyFileFilter());

            System.out.println(System.getProperty("user.dir"));
            FilterFileManager.init(
                    filterConfiguration.getInterval(),
                    "C:\\Users\\wsail\\IdeaProject\\spring-cloud\\groovy\\src\\main\\java\\com\\groovy\\filters\\pre"
            );

        }catch (Exception e){

            throw new RuntimeException(e);
        }


        return filterLoader;

    }




}
