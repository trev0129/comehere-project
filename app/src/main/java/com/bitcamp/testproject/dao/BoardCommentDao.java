package com.bitcamp.testproject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bitcamp.testproject.vo.Comment;

@Mapper
public interface BoardCommentDao {

  //  List<Map<String, Object>> findAll(int boardNo);

  int insertComment(Comment comment);

  List<Comment> findByBoardNo(int boardNo);

  Comment findCommentByNo(int commentNo);

  int deleteComment(int boardNo);

  int updateComment(Comment comment);

}