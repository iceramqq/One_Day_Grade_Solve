package com.biz.grade.config;

public class Lines {
	public static String dline = "";
	public static String sline = "";
	
	static {
		for (int i = 0; i < 100; i++) {
			dline+="=";
			sline+="-";
		}
	}
}
