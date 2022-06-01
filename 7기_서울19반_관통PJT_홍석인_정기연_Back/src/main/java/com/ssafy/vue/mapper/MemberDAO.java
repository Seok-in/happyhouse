package com.ssafy.vue.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ssafy.vue.dto.MemberVO;

@Repository
@Mapper
public interface MemberDAO {
	void memberInsert(MemberVO vo);
	MemberVO login(MemberVO vo);
	String idCheck(String id);
	void memberUpdate(MemberVO vo);
	void memberDelete(String id);
	MemberVO getUser(String id);
	String getPW(MemberVO vo);
} 
