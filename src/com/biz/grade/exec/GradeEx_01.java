package com.biz.grade.exec;

import java.util.Scanner;

import com.biz.grade.config.DBContract;
import com.biz.grade.config.Lines;
import com.biz.grade.srevice.StudentService;
import com.biz.grade.srevice.StudentServiceImplV1;
import com.biz.grade.srevice.scoreService;
import com.biz.grade.srevice.scoreServiceImplV1;

public class GradeEx_01 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StudentService stService = new StudentServiceImplV1();
		stService.lordStudent();
		
		scoreService scService = new scoreServiceImplV1();
		scService.loardScore();
		
		while (true) {
			System.out.println(Lines.dline);
			System.out.println("빛고을 학생 관리 시스템 V1");
			System.out.println(Lines.dline);
			System.out.println("1.학생 정보 등록");
			System.out.println("2.학생 정보 출력");
			System.out.println("3.성적등록");
			System.out.println("4.성적일람표 출력");
			System.out.println(Lines.sline);
			System.out.println("QUIT.업무종료");
			System.out.println(Lines.sline);
			System.out.print("업무선택>>");
			String strManu = scan.nextLine();
			if(strManu.equals("QUIT")) {
				break;
			}
			int intManu = 0;
			try {
				intManu = Integer.valueOf(strManu);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("메뉴는 숫자로만 선택할수 있음!!");
				continue;
			}
			if(intManu == DBContract.MENU.학생정보등록) {
				while(true) {
					if(!stService.inputStudent()) {
						break;
					}	
				}
			} else if (intManu == DBContract.MENU.학생정보출력) {
				stService.studentList();
			} else if (intManu == DBContract.MENU.성적등록) {
				
				// 1. inputScore() 호출하여 코드를 수행하고
				// 2. inputScore()가 true를 return하면 계속 반복하고
				// 3. inputSocre()가 false를 return하면 반복을 중단
				while(scService.inputScore());
				scService.calcSum();
				scService.calcAvg();
			} else if (intManu == DBContract.MENU.성적일람표) {
				scService.calcSum();
				scService.calcAvg();
				scService.scoreList();
			}
		}
		System.out.println("업무종료!!!");
		System.out.println("야 퇴근이다!!!");
	}
}
