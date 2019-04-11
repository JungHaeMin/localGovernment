package hyemin.forkakaopay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocalSaveRequestDto {

    private String region;

    @Builder
    public LocalSaveRequestDto( String region) {
        this.region = region;
    }

}
