package hyemin.forkakaopay;

import com.google.gson.Gson;
import hyemin.forkakaopay.domain.biz.Biz;
import hyemin.forkakaopay.domain.biz.BizRepository;
import hyemin.forkakaopay.domain.input.Input;
import hyemin.forkakaopay.domain.input.InputRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;

import static hyemin.forkakaopay.dummy.Dummy.*;

/******************************************************************************
 * 데이터 파일에서 각 레코드를 데이터베이스에 저장
 ***************************************************************************** */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private BizRepository BizRepo;

	@Autowired
	private InputRepository inputRepo;


	private Gson gson = new Gson();

	/******************************************************************************
	 * 지원하는 지자체 목록 검색
	 ***************************************************************************** */
	@Test
	public void 지자체정보_전체조회_테스트(){
		List<Input> list = convertData((List<Biz>) BizRepo.findAll());
		for(Input i : list){
			System.out.println(gson.toJson(i));
		}
	}

	/******************************************************************************
	* 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력
	***************************************************************************** */
	@Test
	public void 지자체정보_검색_테스트(){
		Input i = inputRepo.findByRegion("고양시");
		System.out.println(gson.toJson(i));
	}

	/******************************************************************************
	 * 지원하는 지자체 정보 수정
	 ***************************************************************************** */
	@Test
	public void 지자체정보_수정_테스트(){

		Input i = inputRepo.findByRegion("고양시");
		System.out.println(gson.toJson(i));
		Input input =new Input( 0 , "고양시","고양이를 키우는 자", "행복", "300백만원","추천금액 이내","고양시","일산고양지점","고양이 키우는 지점");
		try {
			Biz biz = BizRepo.findByRegion(input.getRegion());
			biz = biz.update(biz,input);
			BizRepo.save(biz);
		}catch (Exception e){
		}
		i = inputRepo.findByRegion("고양시");
		System.out.println(gson.toJson(i));
	}

	/******************************************************************************
	 * 지원한도 컬럼에서 지원금액으로 내림차순 정렬
	 * (지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력
	 * 	o 입력: 출력 개수 K
	 * 	o 출력: K 개의 지자체명 (e.g. { 강릉시, 강원도, 거제시, 경기도, 경상남도 } )
	 ***************************************************************************** */
	@Test
	public void 지자체정보_지원금액_이차보전평균비율_기준정렬_테스트(){

		int count = 7;
		List<Input> list =  convertData((List<Biz>) BizRepo.findAll());
		String[] rList;

		for (Input input : list) {
			input.fundValue = getFundValue(input.getFund());
			input.rateValue = getRateValues(input.getRate())[MID];
		}

		list.sort(Comparator.comparing(Input::getFundValue).reversed()
				.thenComparing(Input::getRateValue));
		if(list.size() <= count ) count = list.size();
		rList = new String[count];

		for( int index=0;index<count;index++) {
			rList[index] = list.get(index).getRegion();
		}
		System.out.println(gson.toJson(rList));
	}

	/******************************************************************************
	 * 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력
	 ***************************************************************************** */
	@Test
	public void 지자체정보_보전비율_최저값_테스트(){
		List<Input> list =  convertData((List<Biz>) BizRepo.findAll());
		for (Input input : list) {
			input.rateValue = getRateValues(input.getRate())[MIN];
		}
		list.sort(Comparator.comparing(Input::getRateValue));
		System.out.println(gson.toJson(list.get(0).getInstitute()));
	}

}
