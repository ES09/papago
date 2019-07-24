package com.sp.papago.mapper;

import java.util.List;

import com.sp.papago.vo.CodeVO;

public interface CodeMapper {

	public List<CodeVO> getCodeList();
	public CodeVO getCodeByCodeNum(Integer codeNum);
}
