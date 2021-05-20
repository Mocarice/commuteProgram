package com.hwan.commuteProgram.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface MemberDAO {
	public boolean loginCheck(MemberVO vo);
	
	public MemberVO viewMember(MemberVO vo);
	
	public void logout(HttpSession session);
	
	public void addMember(MemberVO vo);
	
	public List<MemberVO> totalMember();
	
	public MemberVO oneMember(MemberVO vo);
	
}