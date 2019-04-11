package hyemin.forkakaopay.domain.biz;

import hyemin.forkakaopay.domain.input.Input;
import hyemin.forkakaopay.domain.local.Local;
import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

import static org.assertj.core.util.DateUtil.now;

@Entity
@DynamicUpdate
@SelectBeforeUpdate
@Table(name = "biz")
public class Biz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Local local;

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

    @Column
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    @Column
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Builder
    public Biz(int id, String target, String purpose, String fund, String rate, String institute, String mgmt, String reception, Local local,  Date createdDate, Date modifiedDate ) {
        this.id = id;
        this.target = target;
        this.purpose = purpose;
        this.fund = fund;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
        this.local = local;
        this.local.setBiz(this);
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
    public Biz(){}

    public Biz update(Biz biz, Input input){
        biz.setTarget(input.getTarget());
        biz.setPurpose(input.getPurpose());
        biz.setFund(input.getFund());
        biz.setRate(input.getRate());
        biz.setInstitute(input.getInstitute());
        biz.setMgmt(input.getMgmt());
        biz.setReception(input.getReception());
        biz.setModifiedDate( now() );
        return biz;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", target='" + target + '\'' +
                        ", purpose='" + purpose + '\'' +
                        ", fund='" + fund + '\'' +
                        ", rate='" + rate + '\'' +
                        ", institute='" + institute + '\'' +
                        ", mgmt='" + mgmt + '\'' +
                        ", reception='" + reception + '\''
                ;
    }
    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMgmt() {
        return mgmt;
    }

    public void setMgmt(String mgmt) {
        this.mgmt = mgmt;
    }

    public String getReception() {
        return reception;
    }

    public void setReception(String reception) {
        this.reception = reception;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
