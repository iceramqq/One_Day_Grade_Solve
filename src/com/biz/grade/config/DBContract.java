package com.biz.grade.config;

public class DBContract {
	
	public static class STUDENT {
		public static final int ST_NUM = 0;
		public static final int ST_NAME = 1;
		public static final int ST_DEPT = 2;
		public static final int ST_GRADE = 3;
		public static final int ST_TEL = 4;
	}
	
	public static class SCORE {
		public static final int Sc_NUM = 0;
		public static final int Sc_KOR = 1;
		public static final int Sc_ENG = 2;
		public static final int Sc_MATH = 3;
		public static final int Sc_MUSIC = 4;
		public static final int Sc_SUM = 5;
		public static final int Sc_AVG = 6;
	}
	
	public static class MENU {
		public static final int 학생정보등록 = 1;
		public static final int 학생정보출력 = 2;
		public static final int 성적등록  = 3;
		public static final int 성적일람표  = 4;
	}

}
