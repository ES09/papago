package com.sp.papago.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.papago.mapper.CodeMapper;
import com.sp.papago.service.CodeService;
import com.sp.papago.vo.CodeVO;

@Service
public class CodeServiceImpl implements CodeService {

	@Resource
	private CodeMapper cm;
	
	@Override
	public List<CodeVO> getCodeList() {
		return cm.getCodeList();
	}

	@Override
	public String getCodeByCodeNum(Integer codeNum) {
		CodeVO cvo = cm.getCodeByCodeNum(codeNum);
		return cvo.getCodeCode(); // 선택 된 언어의 코드를 넘기는 것
	}

}
