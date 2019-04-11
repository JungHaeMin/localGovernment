package hyemin.forkakaopay.batch;

import hyemin.forkakaopay.domain.input.Input;
import hyemin.forkakaopay.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class ReadFileBatch {

	/******************************************************************************
	 * 데이터 파일에서 각 레코드를 데이터베이스에 저장
	 ***************************************************************************** */

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public FlatFileItemReader<Input> reader(){

		FlatFileItemReader<Input> reader = new FlatFileItemReader<Input>();
		reader.setResource(new ClassPathResource("inputdata.csv"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<Input>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"id","region", "target", "purpose", "fund", "rate","institute","mgmt","reception"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Input>() {{
				setTargetType(Input.class);
			}});
		}});
		return reader;
	}


	@Bean
	public JdbcBatchItemWriter<Input> writer() {


		JdbcBatchItemWriter<Input> writer = new JdbcBatchItemWriter<Input>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Input>());
		writer.setSql("INSERT INTO input (id, region, target, purpose, fund, rate,institute,mgmt,reception) " +
				"VALUES (:id, :region, :target, :purpose, :fund, :rate,:institute,:mgmt,:reception)");

		writer.setDataSource(dataSource);
		return writer;
	}


	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1())
				.end()
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Input, Input> chunk(null)
				.reader(reader())
				.writer(writer())
				.build();
	}

}
