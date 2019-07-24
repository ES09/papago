package com.sp.papago.vo;

import lombok.Data;

@Data
public class CodeVO {
	
	// 번역하거나 번역 될 언어 목록
	
	private Integer codeNum;
	private String codeCode; // 언어의 코드
	private String codeLanguage; // 언어이 이름

}
