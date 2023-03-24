package com.triakobah.TaskParser;

import com.triakobah.TaskParser.core.config.JobCachingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
@EnableConfigurationProperties(JobCachingConfig.class)
public class TaskParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskParserApplication.class, args);
	}

}
