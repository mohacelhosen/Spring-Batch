package com.store.batch.batch;

import com.store.batch.user.model.User;
import com.store.batch.user.repository.UserRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class BatchConfiguration {
    @Autowired
    private UserRepository userRepository;

    // Step 1: Create an ItemReader
    //FlatFileItemReader reads the CSV file line by line, where each line typically represents a record/document
    @Bean
    public FlatFileItemReader<User> itemReader() {
        FlatFileItemReader<User> userFlatFileItemReader = new FlatFileItemReader<>();

        // Set the resource location (CSV file path)
        userFlatFileItemReader.setResource(new FileSystemResource("src/main/resources/res/users.csv"));

        // Set a name for the reader
        userFlatFileItemReader.setName("csv-reader");

        // Skip the header line in the CSV file
        userFlatFileItemReader.setLinesToSkip(1);


        // ignore any extra fields
        // suppose each has 7 fields but there is some record which have more than 7
        // more than 7 which extra fields will be ignore if setStrict(false) , if setStrict(true) then it will create exception
        userFlatFileItemReader.setStrict(false);

        // Set the LineMapper for mapping lines to objects
        //The LineMapper is responsible for mapping a line (record) from the input file to a domain object, in this case, a User object.
        userFlatFileItemReader.setLineMapper(lineMapper());

        return userFlatFileItemReader;
    }

    // Step 2: Define the LineMapper for mapping lines to objects
    private LineMapper<User> lineMapper() {
        DefaultLineMapper<User> userDefaultLineMapper = new DefaultLineMapper<>();

        // Configure the DelimitedLineTokenizer for comma-separated values
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(",");
        lineTokenizer.setNames("id", "name", "age", "gender", "email", "dob", "country"); //specify the names of the fields in the CSV file corresponding to the properties in the User class.
        lineTokenizer.setStrict(false);// null values for the fields that are missing in the CSV file or if a line has more fields than  it will simply ignore the extra fields.

        // Configure the BeanWrapperFieldSetMapper for mapping fields to the User class
        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        // Set the configured LineTokenizer and FieldSetMapper to the DefaultLineMapper
        userDefaultLineMapper.setFieldSetMapper(fieldSetMapper);
        userDefaultLineMapper.setLineTokenizer(lineTokenizer);

        return userDefaultLineMapper;
    }

    @Bean
    public  UserProcessor userProcessor(){
        return new UserProcessor();
    }

    @Bean
    public RepositoryItemWriter<User> itemWriter(){
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }
}
