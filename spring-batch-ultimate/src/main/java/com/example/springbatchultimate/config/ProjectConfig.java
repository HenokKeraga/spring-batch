package com.example.springbatchultimate.config;

import com.example.springbatchultimate.model.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ProjectConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    public ProjectConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;

    }

    @Bean
    public Job job1() {
        return this.jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(taskletStep1())
                .next(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory
                .get("step1")
                .<Customer, Customer>chunk(10)
                .reader(flatFileItemReader(null))
                .writer(customerItemWriter())
                .build();

    }

    @Bean
    public TaskletStep taskletStep1() {
        return this.stepBuilderFactory
                .get("taskletStep1")
                .tasklet((contribution, context) -> {
                    System.out.println("running");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> flatFileItemReader(@Value("#{jobParameters['inputFlatFile']}") Resource resource) {

        return new FlatFileItemReaderBuilder<Customer>()
                .saveState(false)
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"id", "firstName", "lastName", "birthdate"})
                .fieldSetMapper(fieldSet -> Customer
                        .builder()
                        .id(fieldSet.readLong("id"))
                        .firstName(fieldSet.readString("firstName"))
                        .lastName(fieldSet.readString("lastName"))
                        .birthdate(fieldSet.readDate("birthdate", "yyyy-MM-dd HH:mm:ss"))
                        .build()
                ).build();

    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return item -> System.out.println(item);
    }
}
