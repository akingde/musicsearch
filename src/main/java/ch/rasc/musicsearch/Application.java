package ch.rasc.musicsearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

import ch.rasc.edsutil.optimizer.WebResourceProcessor;

@Configuration
@ComponentScan(basePackages = { "ch.ralscha.extdirectspring", "ch.rasc.musicsearch" })
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.active",
				System.getProperty("spring.profiles.active", "development"));
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		characterEncodingFilter.setForceEncoding(false);
		return characterEncodingFilter;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer(
			final Environment environment) {
		return servletContext -> {
			try {
				boolean isDefaultProfileActive = environment.acceptsProfiles("default");
				WebResourceProcessor processor = new WebResourceProcessor(servletContext,
						isDefaultProfileActive);
				processor.process();

				ClassPathResource cpr = new ClassPathResource("index.html");
				String indexHtml = StreamUtils.copyToString(cpr.getInputStream(),
						StandardCharsets.UTF_8);
				indexHtml = indexHtml.replace("application.app_css",
						(String) servletContext.getAttribute("app_css"));
				indexHtml = indexHtml.replace("application.app_js",
						(String) servletContext.getAttribute("app_js"));
				indexHtml = indexHtml.replace("application.getContextPath()",
						servletContext.getContextPath());
				servletContext.setAttribute("index.html", indexHtml);
			}
			catch (IOException e) {
				LoggerFactory.getLogger(Application.class).error("read index.html", e);
			}
		};
	}

}