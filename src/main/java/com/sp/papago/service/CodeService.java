package com.sp.papago.service;

import java.util.List;

import com.sp.papago.vo.CodeVO;

public interface CodeService {

	public List<CodeVO> getCodeList();
	public String getCodeByCodeNum(Integer codeNum);
	
}
