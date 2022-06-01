package com.ssafy.vue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.MemberVO;
import com.ssafy.vue.mapper.MemberDAO;

@Service
@ComponentScan(basePackages = {"com.ssafy.vue.dao"})
public class MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	public void memberInsert(MemberVO vo) {
		memberDAO.memberInsert(vo);		
	}

	public MemberVO login(MemberVO vo) {
		return memberDAO.login(vo);		
	}

	public String idcheck(String id) {
		return memberDAO.idCheck(id);
		
	}

	public void memberUpdate(MemberVO vo) {
		memberDAO.memberUpdate(vo);
		
	}

	public void memberDelete(String id) {
		// TODO Auto-generated method stub
		memberDAO.memberDelete(id);
	}

	public MemberVO getUser(String id) {
		// TODO Auto-generated method stub
		return memberDAO.getUser(id);
	}

	public String getPW(MemberVO vo) {
		return memberDAO.getPW(vo);
	}

}
