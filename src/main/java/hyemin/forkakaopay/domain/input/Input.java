package hyemin.forkakaopay.domain.input;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hyemin.forkakaopay.dto.InputSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicUpdate
@SelectBeforeUpdate
public class Input {

    @Transient
    @JsonBackReference
    public int getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(length = 50, nullable = false)
    public String region;
    @Column(length = 200)
    public String target;
    @Column(length = 50)
    public String purpose;
    @Column(length = 50)
    public String fund;
    @Column(length = 20)
    public String rate;
    @Column(length = 50)
    public String institute;
    @Column(length = 50)
    public String mgmt;
    @Column(length = 100)
    public String reception;

    @Builder
    public Input(int id,String region, String target, String purpose, String fund, String rate, String institute, String mgmt, String reception) {
        this.id = id;
        this.region = region;
        this.target = target;
        this.purpose = purpose;
        this.fund = fund;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }
    public Input() {
    }

    public Input(InputSaveRequestDto dto) {
        this.region = dto.getRegion();
        this.target =  dto.getTarget();
        this.purpose = dto.getPurpose();
        this.fund = dto.getFund();
        this.rate =  dto.getRate();
        this.institute =  dto.getInstitute();
        this.mgmt = dto.getMgmt();
        this.reception =  dto.getReception();
    }


    @Transient
    @JsonBackReference
    public long fundValue;

    @Transient
    @JsonBackReference
    public Double rateValue;

    @Transient
    public long getFundValue() {
        return fundValue;
    }
    @Transient
    public Double getRateValue() {
        return rateValue;
    }


    public String getRegion() {
        return region;
    }

    public String getTarget() {
        return target;
    }

    public String getPurpose() {
        return purpose;
    }


    public String getFund() {
        return fund;
    }


    public String getRate() {
        return rate;
    }

    public String getInstitute() {
        return institute;
    }


    public String getMgmt() {
        return mgmt;
    }

    public String getReception() {
        return reception;
    }


}
