package hyemin.forkakaopay.listener;

import hyemin.forkakaopay.domain.biz.Biz;
import hyemin.forkakaopay.domain.biz.BizRepository;
import hyemin.forkakaopay.domain.input.Input;
import hyemin.forkakaopay.domain.input.InputRepository;
import hyemin.forkakaopay.domain.local.Local;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.assertj.core.util.DateUtil.now;

;


@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	@Autowired
	private final InputRepository inputRepo;

	@Autowired
	private final BizRepository bizRepos;

	@Autowired
	public JobCompletionNotificationListener(InputRepository inputRepo, BizRepository bizRepository) {
		this.inputRepo = inputRepo;
		this.bizRepos = bizRepository;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {

			for (Input input : inputRepo.findAll()) {
				bizRepos.save(new Biz(0,input.getTarget(),input.getPurpose(),input.getFund(),input.getRate()
						,input.getInstitute(),input.getMgmt(),input.getReception(), new Local(input.getRegion()) ,now(), now() ));
			}
		}
	}
}