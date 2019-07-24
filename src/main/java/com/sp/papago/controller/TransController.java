package com.sp.papago.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.papago.service.CodeService;
import com.sp.papago.service.TransService;
import com.sp.papago.vo.TransVO;

@Controller
public class TransController {
	
	@Resource
	private TransService ts;
	
	@Resource
	private CodeService cs;
	
	@GetMapping("/trans")
	public String goTransInputCode(Model m) {
		m.addAttribute("codes", cs.getCodeList());
		return "trans";		
	}
	
	@PostMapping(value="/trans", produces="application/text;charset=UTF-8")
	public @ResponseBody Object translation(TransVO tvo) {
		System.out.println(tvo.getTransText());
		Object getDBResult = ts.findTrans(tvo); // 번역 내용을 DB에서 찾기
		if(getDBResult!=null) { // DB에 있는 내용이면 DB의 내용을 보내주기
			return getDBResult; 
		}
		return ts.doTrans(tvo); // DB에 없다면 번역한 다음 DB에 담는 서비스 호출
	}
	
	@GetMapping("/rank")
	public @ResponseBody List<TransVO> getTransList(){
		return ts.getTransList();
	}

}
