package com.sp.papago.vo;

import lombok.Data;

@Data
public class TransVO {
	
	private Integer transNum;
	private String transText; // 번역할 내용
	private String transResult; // 번역된 내용
	private Integer transCount; // 번역 횟수
	private Integer transSource; // 번역할 언어의 코드
	private Integer transTarget; // 번역될 언어의 코드
	private String transError; // 에러코드

}
