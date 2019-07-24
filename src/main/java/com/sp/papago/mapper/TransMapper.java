package com.sp.papago.mapper;

import java.util.List;

import com.sp.papago.vo.TransVO;

public interface TransMapper {
	
	public Integer insertTrans(TransVO tvo);
	public TransVO findTrans(TransVO tvo);
	public Integer plusCount(Integer transNum);
	public List<TransVO> getTransList();

}
