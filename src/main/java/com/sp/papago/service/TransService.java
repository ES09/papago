package com.sp.papago.service;

import java.util.List;

import com.sp.papago.vo.TransVO;

public interface TransService {
	
	public Object doTrans(TransVO tvo);
	public Object findTrans(TransVO tvo);
	public List<TransVO> getTransList();

}
