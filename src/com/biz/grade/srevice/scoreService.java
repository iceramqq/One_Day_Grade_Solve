package com.biz.grade.srevice;

import com.biz.grade.domain.ScoreVO;

public interface scoreService {
	public void loardScore();
	public boolean inputScore();
	
	public void saveScoreVO(ScoreVO scoreVO);
	
	public void saveScore();
	public void scoreList();
	public void calcSum();
	public void calcAvg();
	
}
