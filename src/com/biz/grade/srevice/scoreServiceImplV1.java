package com.biz.grade.srevice;

import java.awt.BufferCapabilities.FlipContents;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.config.DBContract;
import com.biz.grade.config.Lines;
import com.biz.grade.domain.ScoreVO;
import com.biz.grade.domain.StudentVO;

public class scoreServiceImplV1 implements scoreService{
	
	private List<StudentVO> studentList;
	private List<ScoreVO> scoreList;
	private Scanner scan;
	private String fileName;
	
	String[] strSubjects;
	Integer[] intScores;
	private int[] totalSum;
	private int[] totalAvg;
	
	StudentService stService;
	
	public scoreServiceImplV1() {
		// TODO Auto-generated constructor stub
		scoreList = new ArrayList<ScoreVO>();
		scan = new Scanner(System.in);
		fileName = "src/com/biz/grade/exec/data/score.txt";
		
		strSubjects = new String[] {"국어","영어","수학","음악"};
		intScores = new Integer[strSubjects.length];
		
		totalSum = new int[strSubjects.length];
		totalAvg  = new int[strSubjects.length];
		
		stService = new StudentServiceImplV1();
		stService.lordStudent();
		studentList = stService.getStudentList();
	}
	
	@Override
	public void loardScore() {
		// TODO Auto-generated method stub
		FileReader fileReader = null;
		BufferedReader buffer = null;
		
		try {
			fileReader = new FileReader(this.fileName);
			buffer = new BufferedReader(fileReader);
			String reader = "";
			while(true) {
				reader = buffer.readLine();
				if(reader == null) {
					break;
				}
				String[] scores = reader.split(":");
				ScoreVO scoreVO = new ScoreVO();
				scoreVO.setNum(scores[DBContract.SCORE.Sc_NUM]);
				scoreVO.setKor(Integer.valueOf(scores[DBContract.SCORE.Sc_KOR]));
				scoreVO.setEng(Integer.valueOf(scores[DBContract.SCORE.Sc_ENG]));
				scoreVO.setMath(Integer.valueOf(scores[DBContract.SCORE.Sc_MATH]));
				scoreVO.setMusic(Integer.valueOf(scores[DBContract.SCORE.Sc_MUSIC]));
				
				scoreList.add(scoreVO);
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("학생정보 파일 읽기 오류!!!");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("학생정보 파일 읽기 오류!!!");
		} 
	}
	
	//
	//
	
	// 로 전달받은 값을 검사하는 코드
	//
	// 숫자로 바꿀수없는 문자열, 점수범위를 벗어나는 값이면 null을 return
	// 정상적인 문자열을 정수로 바꾸어 return
	private Integer scoreCheck(String sc_score) {
		
		if(sc_score.equals("END")) {
			return -1;
		}
		
		/*
		 * int intscore = null : 오류가 발생하는 코드
		 * 		형 변수는 null 값으로 clear, 초기화를 할수 없다.
		 * Integer intscore = null : 정상적인 코드
		 * 		형 변수는 null 값으로 clear, 초기화를 할수 잇다.
		 */
		Integer intScore = null;
		try {
			intScore = Integer.valueOf(sc_score); 
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("점수는 숫자만 가능");
			System.out.println("다시 입력해 주세요");
			return null;
		}
		if (intScore < 0 || intScore > 100) {
			System.out.println("점수는 0~100까지만 가능");
			System.out.println("다시 입력해 주세요");
			return null;
		}
		
		return intScore;
	}
	
	@Override
	public boolean inputScore() {
		ScoreVO scoreVO = new ScoreVO();
		
		System.out.print("학번(END:종료)>>");
		String st_num = scan.nextLine();
		if(st_num.equals("END")) {
			return false;
		}
		int intNum = 0;
		try {
			intNum = Integer.valueOf(st_num);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("숫자만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}
		if (intNum < 0 || intNum > 100) {
			System.out.println("학번은 1~999999까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}
		//
		st_num = String.format("%05d", intNum);
		
		for (ScoreVO scVO : scoreList) {
			if(scVO.getNum().equals(st_num)) {
				System.out.println(st_num + "학생정보가 이미 등록되어 있습니다");
				return true;
			}
		}
		StudentVO retVO = stService.getStudent(st_num);
		if(retVO == null) {
			System.out.println(st_num+"학생정보가 학적부에 없음");
			System.out.println("성적을 입력할수 없음");
			return true;
		}
		
		scoreVO.setNum(st_num);
		
		for (int i = 0; i < strSubjects.length; i++) {
			
			System.out.printf("%s 점수(END:종료)",strSubjects[i]);
			String sc_score = scan.nextLine();
			// -1, null, 숫자 값이 담겨
			Integer intScore = this.scoreCheck(sc_score);
			if(intScore == null) {
				
				i--;
				continue;
			} else if(intScore < 0) {
				return false;
			}
			// 모든 것이 정상이면 점수배열에 
			intScores[i]= intScore;
		}
		scoreVO.setKor(intScores[0]);
		scoreVO.setEng(intScores[1]);
		scoreVO.setMath(intScores[2]);
		scoreVO.setMusic(intScores[3]);
		
		scoreList.add(scoreVO);
		this.saveScoreVO(scoreVO); // 1명의 데이터를 추가 저장하기
		return true;
	}

	@Override
	public void saveScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scoreList() {
		// TODO Auto-generated method stub
		Arrays.fill(totalSum, 0);
		Arrays.fill(totalAvg, 0);
		
		
		System.out.println(Lines.dline);
		System.out.println("학생 성적 리스트");
		System.out.println(Lines.dline);
		System.out.println("학번\t|이름\t|국어\t|영어\t|수학\t|음악\t|총점\t|평균\t|");
		System.out.println(Lines.sline);
		for (ScoreVO scoreVO : scoreList) {
			System.out.printf("%s\t|",scoreVO.getNum());
			
			StudentVO retVO = stService.getStudent(scoreVO.getNum());
			String st_name = "[없음]";
			if(retVO != null) {
				st_name = retVO.getName();
			}
			System.out.printf("%s\t|",st_name);
			System.out.printf("%d\t|",scoreVO.getKor());
			System.out.printf("%d\t|",scoreVO.getEng());
			System.out.printf("%d\t|",scoreVO.getMath());
			System.out.printf("%d\t|",scoreVO.getMusic());
			System.out.printf("%d\t|",scoreVO.getSum());
			System.out.printf("%5.2f\t|\n",scoreVO.getAvg());
			
			totalSum[0] += scoreVO.getKor();
			totalSum[1] += scoreVO.getEng();
			totalSum[2] += scoreVO.getMath();
			totalSum[3] += scoreVO.getMusic();
		}
		System.out.println(Lines.sline);
		
		System.out.print("과목총점:\t|");
		int sumAndSum = 0;
		for(int sum : totalSum) {
			System.out.printf("%s\t|",sum);
			sumAndSum += sum;
		}
		System.out.printf("%s\t|\n",sumAndSum);

		System.out.print("과목평균:\t|");
		float avgAndAvg = 0f;
		for(int sum : totalSum) {
			float avg = (float)sum / scoreList.size();
			System.out.printf("%5.2f\t|",avg);
			avgAndAvg += avg;
		}
		System.out.printf("\t|%5.2f\t|\n",avgAndAvg / totalSum.length);
		System.out.println(Lines.dline);
	}

	@Override
	public void saveScoreVO(ScoreVO scoreVO) {
		// TODO Auto-generated method stub
		FileWriter fileWriter = null;
		PrintWriter pWriter = null;
		
		try {
			fileWriter = new FileWriter(this.fileName, true);
			
			pWriter = new PrintWriter(fileWriter);
			pWriter.printf("%s:",scoreVO.getNum());
			pWriter.printf("%d:",scoreVO.getKor());
			pWriter.printf("%d:",scoreVO.getEng());
			pWriter.printf("%d:",scoreVO.getMath());
			pWriter.printf("%d\n",scoreVO.getMusic());
			pWriter.flush();
			pWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void calcSum() {
		// TODO Auto-generated method stub
		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getKor()
					+ scoreVO.getEng()
					+ scoreVO.getMath()
					+ scoreVO.getMusic();
			scoreVO.setSum(sum);
		}
	}

	@Override
	public void calcAvg() {
		// TODO Auto-generated method stub
		for (ScoreVO scoreVO : scoreList) {
			float avg = (float) scoreVO.getSum() / 4;
			scoreVO.setAvg(avg);
		}
	}

}
