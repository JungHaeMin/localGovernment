package hyemin.forkakaopay.dummy;

import hyemin.forkakaopay.domain.biz.Biz;
import hyemin.forkakaopay.domain.input.Input;
import org.apache.log4j.helpers.LogLog;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Dummy {
    final static public int MIN = 0;
    final static public int MAX = 1;
    final static public int MID = 2;

    final static public int MILLION = 1000000;
    final static public int THOUSAND = 1000;
    final static public int HUNDRED = 100;
    final static public int TEN = 10;


    static public List<Input> convertData(List<Biz> list){
        List<Input> rList = new ArrayList<>();
        for(Biz biz : list){
            rList.add(new Input(0,biz.getLocal().region,biz.getTarget(),biz.getPurpose(),biz.getFund(),biz.getRate(),biz.getInstitute(),biz.getMgmt(),biz.getReception()));
        }
        return rList;
    }



    static public Double[] getRateValues(String strRate){
        Double[] rateValues = new Double[]{0.0,0.0,0.0};
        int valueCnt=0;

        StringTokenizer str = new StringTokenizer(strRate, "%~ ", true);
        try{
            while(str.hasMoreTokens()) {
                String token = str.nextToken();
                if( token.contains("%") || token.contains(" ") || token.contains("~")) {
                    continue;
                }
                rateValues[valueCnt++] = Double.valueOf(token);
            }
        }catch (Exception e){
            rateValues[MIN] =10000000.0;
            rateValues[MAX] =10000000.0;
            rateValues[MID] =10000000.0;
            return  rateValues;
        }
        if(valueCnt == 1){
            rateValues[ valueCnt++] = rateValues[MIN];
        }
        rateValues[valueCnt] =  (rateValues[MIN]+rateValues[MAX])/valueCnt;
        return rateValues;
    }


    static public long getFundValue(String strFund){
        long fundValue = 1;
        String token="";
        int num;
        StringTokenizer str = new StringTokenizer(strFund, "억만천백원 ", true);
        try {
            while (str.hasMoreTokens()) {
                String bToken = token;
                token = str.nextToken();
                if ("억".equals(token)) {
                    fundValue *= HUNDRED * MILLION;
                } else if ("만".equals(token)) {
                    fundValue *= TEN *THOUSAND;
                }else if ("천".equals(token) && !bToken.equals("추")) {
                    fundValue *= THOUSAND;
                }else if ("백".equals(token)) {
                    fundValue *= HUNDRED;
                }else {
                    try {
                        num = Integer.parseInt(token);
                        fundValue *= num;
                    } catch (NumberFormatException nfe) {
                    }
                }
            }
        }catch (Exception e){
            return 0;
        }
        return fundValue;
    }



}
