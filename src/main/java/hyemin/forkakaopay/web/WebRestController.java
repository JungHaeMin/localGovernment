package hyemin.forkakaopay.web;

import hyemin.forkakaopay.domain.biz.Biz;
import hyemin.forkakaopay.domain.biz.BizRepository;
import hyemin.forkakaopay.domain.input.Input;
import hyemin.forkakaopay.domain.input.InputRepository;
import hyemin.forkakaopay.dto.InputSaveRequestDto;
import hyemin.forkakaopay.dto.LocalSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static hyemin.forkakaopay.dummy.Dummy.*;

@RestController
@AllArgsConstructor
public class WebRestController {

    @Autowired
    private BizRepository BizRepo;

    @Autowired
    private InputRepository InputRepo;

    /******************************************************************************
     * 지원하는 지자체 목록 검색
     ***************************************************************************** */
    @GetMapping("/read")
    public List<Input> readBiz(){
        return convertData((List<Biz>) BizRepo.findAll());
    }


    /******************************************************************************
     * 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력
     ***************************************************************************** */
    @PostMapping("/search")
    public Input searchByRegion(@RequestBody LocalSaveRequestDto dto ) {
        return InputRepo.findByRegion(dto.getRegion());
    }

    /******************************************************************************
     * 지원하는 지자체 정보 수정
     ***************************************************************************** */
    @PostMapping("/update")
    public boolean searchByRegion(@RequestBody InputSaveRequestDto dto ) {
        try {
            Biz biz = BizRepo.findByRegion(dto.getRegion());
            biz = biz.update(biz,new Input(dto));
            BizRepo.save(biz);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /******************************************************************************
     * 지원한도 컬럼에서 지원금액으로 내림차순 정렬
     * (지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력
     * 	o 입력: 출력 개수 K
     * 	o 출력: K 개의 지자체명 (e.g. { 강릉시, 강원도, 거제시, 경기도, 경상남도 } )
     ***************************************************************************** */
    @GetMapping("/search/lowFund/{count}")
    public String[] searchLowFund(@PathVariable("count") int count) {

        List<Input> list =  convertData((List<Biz>) BizRepo.findAll());
        String[] rList;
        int index = 0;

        for (Input input : list) {
            input.fundValue = getFundValue(input.getFund());
            input.rateValue = getRateValues(input.getRate())[MID];
        }

        list.sort(Comparator.comparing(Input::getFundValue).reversed()
                .thenComparing(Input::getRateValue));
        if(list.size() <= count ) count = list.size();
        rList = new String[count];
        for( Input input : list)  rList[index++] = input.getRegion();
        return rList;
    }

    /******************************************************************************
     * 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력
     ***************************************************************************** */
    @GetMapping("/search/LowestRate")
    public String searchLowestRate() {
        List<Input> list =  convertData((List<Biz>) BizRepo.findAll());
        for (Input input : list) {
            input.rateValue = getRateValues(input.getRate())[MIN];
        }
        list.sort(Comparator.comparing(Input::getRateValue));
        return list.get(0).getInstitute();
    }

}
