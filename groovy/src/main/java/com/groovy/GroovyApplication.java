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
        try {

            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(
                    filterConfiguration.getInterval(),
                    filterConfiguration.getRoot()+"/pre",
                    filterConfiguration.getRoot()+"/post"
            );

        }catch (Exception e){

            throw new RuntimeException(e);
        }


        return filterLoader;

    }

}
