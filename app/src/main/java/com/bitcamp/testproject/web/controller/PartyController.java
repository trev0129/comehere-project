package com.bitcamp.testproject.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bitcamp.testproject.service.PartyCommentService;
import com.bitcamp.testproject.service.PartyService;
import com.bitcamp.testproject.service.RegionService;
import com.bitcamp.testproject.service.ReviewService;
import com.bitcamp.testproject.service.SportsService;
import com.bitcamp.testproject.vo.Criteria;
import com.bitcamp.testproject.vo.Member;
import com.bitcamp.testproject.vo.PageMaker;
import com.bitcamp.testproject.vo.Party;
import com.bitcamp.testproject.vo.PartyMember;

@Controller
@RequestMapping("/party/")
public class PartyController {

  // 파티 지울때 멤버도 지워야?

  // 파티 파일, 댓글, 후기파일, 후기, 명단 

  ServletContext sc;
  PartyService partyService;
  RegionService regionService;
  SportsService sportsService;
  PartyCommentService partyCommentService;
  ReviewService reviewService;

  public PartyController(
      PartyService partyService, 
      RegionService regionService, 
      SportsService sportsService, 
      PartyCommentService partyCommentService,
      ReviewService reviewService,
      ServletContext sc) {
    System.out.println("PartyController() 호출됨!");
    this.partyService = partyService;
    this.regionService = regionService;
    this.sportsService = sportsService;
    this.partyCommentService = partyCommentService;
    this.reviewService = reviewService;
    this.sc = sc;
  }

  // InternalResourceViewResolver 사용 전:
  //
  //  @GetMapping("form")
  //  public String form() throws Exception {
  //    return "board/form";
  //  }

  // InternalResourceViewResolver 사용 후:
  @GetMapping("form")
  public void form(Model model) throws Exception {
    model.addAttribute("regions", regionService.list());
    model.addAttribute("sports", sportsService.list());

  }

  @GetMapping("attendForm") 
  public Map attendForm(int no) throws Exception {
    Party party = partyService.get(no);
    if (party == null) {
      throw new Exception("해당 번호의 모임이 없습니다!");
    }
    Map map = new HashMap();
    map.put("party", party);
    return map;
  }

  @PostMapping("add") 
  public String add(
      Party party,
      PartyMember partyMember,
      Part file,
      HttpSession session) throws Exception {
    party.setUser((Member) session.getAttribute("loginMember"));
    party.setThumbnail(saveAttachedFile(file));
    partyService.add(party, partyMember, file);
    return "redirect:list";
  }

  @PostMapping("attendAdd")
  public String attendAdd(
      Party party,
      PartyMember partyMember,
      HttpSession session) throws Exception {
    party.setUser((Member) session.getAttribute("loginMember"));
    partyService.attend(party, partyMember);
    return "redirect:list";
  }

  private String saveAttachedFile(Part file) throws IOException, ServletException {
    //    List<AttachedFile> attachedFiles = new ArrayList<>();
    String dirPath = sc.getRealPath("/party/files");

    // 첨부파일이 있다면 실행
    if (file.getSize() != 0) {
      String filename = UUID.randomUUID().toString();
      file.write(dirPath + "/" + filename);
      return filename;
    }
    return null;
  }


  @GetMapping("list")
  public void list(Criteria cri, Model model) throws Exception {
    System.out.println(cri);
    PageMaker pageMaker = new PageMaker();
    pageMaker.setCri(cri);
    pageMaker.setDisplayPageNum(2);
    pageMaker.setTotalCount(partyService.listCount());
    System.out.println(pageMaker.getDisplayPageNum());

    model.addAttribute("partys", partyService.list(cri));
    model.addAttribute("regions", regionService.list());
    model.addAttribute("sports", sportsService.list());
    model.addAttribute("pageMaker", pageMaker);
  }


  @GetMapping("list-ajax")
  public void listparam(
      String gu, 
      String sports, 
      String partyDate, 
      String partyTime, 
      String searchText,
      String listStar,
      String listCreate,
      String listPartyDate,
      Criteria cri, 
      Model model) throws Exception {
    System.out.println(cri);
    System.out.println("1단계");
    PageMaker pageMaker = new PageMaker();
    System.out.println("2단계");
    pageMaker.setCri(cri);
    System.out.println("3단계");
    pageMaker.setDisplayPageNum(2);
    System.out.println("4단계");
    pageMaker.setTotalCount(partyService.listCount2(gu, sports, partyDate, partyTime, searchText));
    System.out.println("5단계");
    System.out.println(cri.getPagesStart());
    System.out.println(cri.getPerPageNum());

    model.addAttribute(
        "partys",
        partyService.list2(gu, sports, partyDate, partyTime, searchText, listStar, listCreate, listPartyDate, cri));
    System.out.println("6단계");
    model.addAttribute("pageMaker", pageMaker);
    System.out.println("7단계");
    System.out.println(pageMaker);
    System.out.printf("%s, %s, %s, %s, %s, %s, %s, %s\n", gu, sports, partyDate, partyTime, searchText, listStar, listCreate, listPartyDate);
  }

  @GetMapping("list-ajax-page")
  public void listparam2(
      String gu, 
      String sports, 
      String partyDate, 
      String partyTime, 
      String searchText,
      String listStar,
      String listCreate,
      String listPartyDate,
      Criteria cri, 
      Model model) throws Exception {
    System.out.println(cri);
    System.out.println("1단계");
    PageMaker pageMaker = new PageMaker();
    System.out.println("2단계");
    pageMaker.setCri(cri);
    System.out.println("3단계");
    pageMaker.setDisplayPageNum(2);
    System.out.println("4단계");
    pageMaker.setTotalCount(partyService.listCount2(gu, sports, partyDate, partyTime, searchText));
    System.out.println("5단계");
    System.out.println(cri.getPagesStart());
    System.out.println(cri.getPerPageNum());

    model.addAttribute(
        "partys",
        partyService.list2(gu, sports, partyDate, partyTime, searchText, listStar, listCreate, listPartyDate, cri));
    System.out.println("6단계");
    model.addAttribute("pageMaker", pageMaker);
    System.out.println("7단계");
    System.out.println(pageMaker);
    System.out.printf("%s, %s, %s, %s, %s, %s, %s, %s\n", gu, sports, partyDate, partyTime, searchText, listStar, listCreate, listPartyDate);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @GetMapping("detail")
  public Map detail(int no, Model model, Criteria cri) throws Exception {

    // 페이징하기 위한 연산 
    PageMaker pageMaker = new PageMaker();
    cri.setPerPageNum(15);
    pageMaker.setCri(cri);
    pageMaker.setTotalCount(partyCommentService.countCommentListTotal(no));


    Party party = partyService.get(no);
    System.out.println(party);
    int sportNo = party.getSports().getNo();
    int userNo = party.getUser().getNo();

    if (party == null) {
      throw new Exception("해당 번호의 모임이 없습니다!");
    }
    Map map = new HashMap();
    map.put("party", party);
    model.addAttribute("reviews", reviewService.list(userNo, sportNo));
    model.addAttribute("pageMaker", pageMaker);
    return map;
  }

  @GetMapping("updateForm")
  public Model updateForm(int no, Model model) throws Exception {
    Party party = partyService.get(no);
    if (party == null) {
      throw new Exception("해당 번호의 모임이 없습니다!");
    }

    model.addAttribute("regions", regionService.list());
    model.addAttribute("sports", sportsService.list());

    return model.addAttribute("party", party);
  }

  @PostMapping("update")
  public String update(
      Party party,
      Part file,
      HttpSession session) 
          throws Exception {
    party.setThumbnail(saveAttachedFile(file));
    checkOwner(party.getNo(), session);
    if (!partyService.update(party, file)) {
      throw new Exception("모임을 변경할 수 없습니다!");
    }
    return "redirect:list";
  }

  private void checkOwner(int partyNo, HttpSession session) throws Exception {
    Member loginMember = (Member) session.getAttribute("loginMember");
    if (partyService.checkOwner(partyNo) != loginMember.getNo()) {
      throw new Exception("모임 주최자가 아닙니다.");
    }
  }

  @GetMapping("delete")
  public String delete(
      int no, 
      HttpSession session) 
          throws Exception {
    checkOwner(no, session);

    if (!partyService.delete(no)) {
      throw new Exception("모임을 삭제할 수 없습니다.");
    }
    return "redirect:list";
  }

}






