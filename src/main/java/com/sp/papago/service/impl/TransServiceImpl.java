package com.sp.papago.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.papago.mapper.TransMapper;
import com.sp.papago.service.CodeService;
import com.sp.papago.service.TransService;
import com.sp.papago.vo.TransVO;

@Service
public class TransServiceImpl implements TransService{

	@Resource
	private TransMapper tm;
	@Resource
	private CodeService cs;
	
	@Override
	public Object doTrans(TransVO tvo) {
		String clientId = "  ";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "  ";//애플리케이션 클라이언트 시크릿값";
		String source = cs.getCodeByCodeNum(tvo.getTransSource()).trim();
		String target = cs.getCodeByCodeNum(tvo.getTransTarget()).trim();
		
		 try {
	            String text = URLEncoder.encode(tvo.getTransText(), "UTF-8");
	            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("POST");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            // post request
	            String postParams = "source=" + source + "&target=" + target + "&text=" + text;
	            System.out.println(postParams);
	            con.setDoOutput(true);
	            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	            wr.writeBytes(postParams);
	            wr.flush();
	            wr.close();
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            // 결과 메세지 자르기
	            ObjectMapper om = new ObjectMapper();
	            Map<String,Object> rMap = om.readValue(response.toString(), Map.class); // 맵 형태로 결과를 담기
	            String transResult = ((Map)((Map)rMap.get("message")).get("result")).get("translatedText").toString(); // 결과 자르기
	            if(rMap.get("errorCode")!=null) { // 에러나면 결과에 에러 담기
	            	tvo.setTransResult("에러");
	            	tvo.setTransError((String)rMap.get("errorCode"));
	            	return tm.insertTrans(tvo);
	            } else { // 번역 성공하면 저장하기
	            	tvo.setTransResult(transResult);
	            	tm.insertTrans(tvo);
	            	return transResult;
	            }
	            
	        } catch (Exception e) {
	            System.out.println(e);
	        }
			
			return null;
		}

	@Override
	public Object findTrans(TransVO tvo) { // 했었던 번역이라면 데이터베이스에서 찾아서 출력
		TransVO result = tm.findTrans(tvo);
		if(result!=null) {
			tm.plusCount(result.getTransNum());
			return result.getTransResult();
		}
		return null;
	}

	@Override
	public List<TransVO> getTransList() {
		return tm.getTransList();
	}

}
