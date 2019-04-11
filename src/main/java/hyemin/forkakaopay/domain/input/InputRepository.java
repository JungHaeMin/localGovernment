package hyemin.forkakaopay.domain.input;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface InputRepository extends CrudRepository<Input, Long>,
        QueryByExampleExecutor<Input> {

    @Query(nativeQuery = true, value ="select biz.id, region, biz.target, biz.purpose, biz.fund, biz.rate, biz.institute, biz.mgmt, biz.reception  " +
            "from biz join local on biz.local_code = code and region = :region")
    Input findByRegion(@Param("region") String region);

}