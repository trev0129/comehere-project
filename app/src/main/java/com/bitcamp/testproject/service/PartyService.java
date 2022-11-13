package com.bitcamp.testproject.service;

import java.util.List;
import javax.servlet.http.Part;
import com.bitcamp.testproject.vo.Criteria;
import com.bitcamp.testproject.vo.Party;
import com.bitcamp.testproject.vo.PartyMember;

// 비즈니스 로직을 수행하는 객체의 사용규칙(호출규칙)
//
public interface PartyService {

  void add(Party party, PartyMember partyMember, Part file) throws Exception;

  void attend(Party party, PartyMember partyMember) throws Exception;

  boolean update(Party party, Part file) throws Exception;

  Party get(int no) throws Exception;

  boolean delete(int no) throws Exception;

  List<Party> list(Criteria cri) throws Exception;

  int listCount() throws Exception;

  int listCount2(
      String gu, 
      String sports, 
      String partyDate, 
      String partyTime,
      String searchText
      ) throws Exception;

  List<Party> list2(
      String gu, 
      String sports, 
      String partyDate, 
      String partyTime,
      String searchText,
      String listStar,
      String listCreate,
      String listPartyDate,
      Criteria cri) throws Exception;

  int checkOwner(int partyNo) throws Exception;

}








