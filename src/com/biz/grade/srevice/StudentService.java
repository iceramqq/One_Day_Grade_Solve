package com.biz.grade.srevice;

import java.util.List;

import com.biz.grade.domain.StudentVO;

public interface StudentService {
	public void lordStudent();
	public boolean inputStudent();
	public void savestudent();
	public void studentList();
	
	public List<StudentVO> getStudentList();
	
	public StudentVO getStudent(String st_num);
}
