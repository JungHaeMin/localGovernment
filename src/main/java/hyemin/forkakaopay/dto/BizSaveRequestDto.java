package hyemin.forkakaopay.dto;

import hyemin.forkakaopay.domain.biz.Biz;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 27.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class BizSaveRequestDto {

    private String code;
    private String target;
    private String  purpose;
    private String  fund;
    private String  rate;
    private String institute;
    private String mgmt;
    private String reception;

    @Builder
    public BizSaveRequestDto(String code, String target, String purpose, String fund, String rate, String institute, String mgmt, String reception) {
        this.code = code;
        this.target = target;
        this.purpose = purpose;
        this.fund = fund;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }

    public Biz toEntity(){
        return Biz.builder()
                .target(target)
                .purpose(purpose)
                .fund(fund)
                .rate(rate)
                .institute(institute)
                .mgmt(mgmt)
                .reception(reception)
                .build();
    }
}
