package hyemin.forkakaopay.domain.local;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface LocalRepository extends CrudRepository<Local, Long>,
        QueryByExampleExecutor<Local> {
}