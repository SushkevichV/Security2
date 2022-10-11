package by.sva.security2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* Для страниц, которые никак не обрабатываются сервером, а просто возвращают страницу, 
 * маппинг можно настроить в конфигурации. Страница login обрабатывается 
 * Spring Security контроллером по умолчанию, поэтому для неё отдельный контроллер не требуется.
 * 
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/news").setViewName("news");
	}

}
