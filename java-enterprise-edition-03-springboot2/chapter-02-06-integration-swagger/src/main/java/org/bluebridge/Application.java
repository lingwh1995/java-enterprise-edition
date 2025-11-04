package org.bluebridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 原生swagger访问url:
 *      http://localhost:8080/swagger-ui.html
 * bootstramp swagger访问url:
 *      http://localhost:8080/doc.html
 * 与SpringSecurity集成:
 *      @Override
 *      public void configure(WebSecurity web) throws Exception {
 *      web.ignoring()
 *      .antMatchers("/swagger-ui.html")
 *      .antMatchers("/v2/**")
 *      .antMatchers("/swagger-resources/**");
 *      }
 * @author ronin
 * @version V1.0
 * @since 2019/12/9 11:00
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
