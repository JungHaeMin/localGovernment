package hyemin.forkakaopay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class InputSaveRequestDto {

    private String region;
    private String target;
    private String  purpose;
    private String  fund;
    private String  rate;
    private String institute;
    private String mgmt;
    private String reception;

    @Builder
    public InputSaveRequestDto(String region, String target, String purpose, String fund, String rate, String institute, String mgmt, String reception) {
        this.region = region;
        this.target = target;
        this.purpose = purpose;
        this.fund = fund;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }
}
