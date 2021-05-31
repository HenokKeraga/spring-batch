package com.example.springbatchultimate.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public ProjectConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job job1(){
        return this.jobBuilderFactory.get("job1")
                .start(taskletStep1())
                .build();
    }

    @Bean
    public TaskletStep taskletStep1(){
        return this.stepBuilderFactory
                .get("taskletStep1")
                .tasklet((contribution,context)->{
                    System.out.println("running");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
