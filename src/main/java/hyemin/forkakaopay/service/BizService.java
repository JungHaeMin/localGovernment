package hyemin.forkakaopay.service;

import hyemin.forkakaopay.domain.biz.BizRepository;
import hyemin.forkakaopay.dto.BizSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class BizService {
    private BizRepository bizRepository;

    @Transactional
    public int save(BizSaveRequestDto dto){
        return bizRepository.save(dto.toEntity()).getId();
    }

}
