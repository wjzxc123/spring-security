package com.lut.springsecurity.config;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 13:33
 */
@AllArgsConstructor
@Getter
public enum VoteTypeEnum {
	/**/
	AFFIRMATIVE(1,"有一个通过则通过"),CONSENSUS(2,"至少有一般通过"),UNANIMOUS(3,"全部通过");


	int code;

	String describe;

	public static VoteTypeEnum getVoteTypeByCode(int code){
		VoteTypeEnum[] enums = VoteTypeEnum.values();
		for (VoteTypeEnum anEnum : enums) {
			if (code == anEnum.getCode()){
				return anEnum;
			}
		}
		return AFFIRMATIVE;
	}
}
