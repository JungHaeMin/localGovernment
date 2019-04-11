package hyemin.forkakaopay.domain.biz;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface BizRepository extends CrudRepository<Biz, Long>,
        QueryByExampleExecutor<Biz> {

    @Query(nativeQuery = true, value ="select biz.*  " +
            "from biz join local on biz.local_code = code and region = :region")
    Biz findByRegion(@Param("region") String region);

}