package com.biz.grade.srevice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.config.DBContract;
import com.biz.grade.config.Lines;
import com.biz.grade.domain.StudentVO;

public class StudentServiceImplV1 implements StudentService {
	
	private List<StudentVO> studentList;
	private Scanner scan;
	private String fileName;
	
	public StudentServiceImplV1() {
		// TODO Auto-generated constructor stub
		studentList = new ArrayList<StudentVO>();
		scan = new Scanner(System.in);
		fileName = "src/com/biz/grade/exec/data/student.txt";
		
	}
	
	@Override
	public List<StudentVO> getStudentList() {
		return studentList;
	}
	
	public StudentVO getStudent(String st_num) {
		StudentVO studentVO = null;
		
		//
		
		for (StudentVO stVO : studentList) {
			//3.매개변수로 받은 가 학생
			if(stVO.getNum().equals(st_num)) {
				studentVO = stVO;
				break;
			}
			//6.만약 에서 해당학번을 못찾으면
			//반복문은 끝까지 진행항 것이다.
		}
		
		//
		//
		//8. for 반복문이 끝까지 진행된 상태라면 
		//
		return studentVO;
	}
	
	@Override
	public void lordStudent() {
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
				String[] students = reader.split(":");
				StudentVO studentVO = new StudentVO();
				studentVO.setNum(students[DBContract.STUDENT.ST_NUM]);
				studentVO.setName(students[DBContract.STUDENT.ST_NAME]);
				studentVO.setDept(students[DBContract.STUDENT.ST_DEPT]);
				studentVO.setGrade(Integer.valueOf(students[DBContract.STUDENT.ST_GRADE]));
				studentVO.setTel(students[DBContract.STUDENT.ST_TEL]);
				studentList.add(studentVO);
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("학생정보 파일 읽기 오류!!!");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("학생정보 파일 읽기 오류!!!");
		} 
		
	}

	@Override
	public boolean inputStudent() {
		StudentVO studentVO = new StudentVO();
		
		System.out.print("학번(END:종료)>>");
		// 변수명 명명규칙
		// 두단어 이상 사용할대 단어 첫글자 대문자
		// 두단어 이상 사용할때 단어 사이 _
		String st_num = scan.nextLine();
		if(st_num.equals("END")) {
			return false;
		}
		int intNum = 0;
		try {
			intNum = Integer.valueOf(st_num);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("학번은 1~999999까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}
		
		st_num = String.format("%05d",intNum);
		for(StudentVO sVO : studentList) {
			if(sVO.getNum().equals(st_num)) {
				System.out.println(st_num + " 학생정보가 이미 등록되어 있습니다");
				return true;
			}
		}
		
		st_num = String.format("%05d", intNum);
		studentVO.setNum(st_num);
		
		System.out.print("이름(END:종료)>>");
		String st_name = scan.nextLine();
		if(st_name.equals("END")) {
			return true;
		}
		studentVO.setName(st_name);
		
		System.out.print("학과(END:종료)>>");
		String st_dept = scan.nextLine();
		if(st_dept.equals("END")) {
			return true;
		}
		studentVO.setDept(st_dept);
		
		System.out.print("학년(END:종료)>>");
		// 변수명 명명규칙
		// 두단어 이상 사용할대 단어 첫글자 대문자
		// 두단어 이상 사용할때 단어 사이 _
		String st_grade = scan.nextLine();
		if(st_num.equals("END")) {
			return false;
		}
		int intGrade = 0;
		try {
			intGrade = Integer.valueOf(st_grade); 
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("숫자만 가능");
		}
		if(intGrade<0 || intGrade>4) {
			// TODO: handle exception
			System.out.println("학년은 1~4까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}
		studentVO.setGrade(intGrade);
		
		System.out.print("전화번호 : 010-111-1111 형식으로 입력(END:종료)>>");
		String st_tel = scan.nextLine();
		if(st_tel.equals("END")) {
			return true;
		}
		studentVO.setTel(st_tel);
		studentList.add(studentVO);
		this.savestudent();
		return true;
		
		
		
	}


	@Override
	public void savestudent() {
		// TODO Auto-generated method stub
		FileWriter fileWriter = null;
		PrintWriter pWriter = null;
		
		try {
			fileWriter = new FileWriter(this.fileName);
			pWriter = new PrintWriter(fileWriter);
			
			for (StudentVO studentVO : studentList) {
				pWriter.printf("%s:",studentVO.getNum());
				pWriter.printf("%s:",studentVO.getName());
				pWriter.printf("%s:",studentVO.getDept());
				pWriter.printf("%d:",studentVO.getGrade());
				pWriter.printf("%s:\n",studentVO.getTel());
			}
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void studentList() {
		System.out.println(Lines.dline);
		System.out.println("학생 명부 리스트");
		System.out.println(Lines.dline);
		System.out.println("학번\t|이름\t|학과\t|학년\t|전화번호\t|");
		System.out.println(Lines.sline);
		for (StudentVO studentVO : studentList) {
			System.out.printf("%s\t|",studentVO.getNum());
			System.out.printf("%s\t|",studentVO.getName());
			System.out.printf("%s\t|",studentVO.getDept());
			System.out.printf("%d\t|",studentVO.getGrade());
			System.out.printf("%s\t|\n",studentVO.getTel());
		}
	}

}
