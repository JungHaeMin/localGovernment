package hyemin.forkakaopay.domain.local;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hyemin.forkakaopay.domain.biz.Biz;
import lombok.Builder;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Local {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true)
    public int code;

    @Column(length = 50)
    public  String region;

    @Transient
    @JsonBackReference
    public Biz getBiz() {
        return biz;
    }

    public void setBiz(Biz biz) {
        this.biz = biz;
    }

    @Transient
    @JsonBackReference
    @OneToOne(mappedBy = "local")
    private Biz biz;

    @Builder
    public Local( String region){
        this.region = region;
    }

    public Local(){}

}
