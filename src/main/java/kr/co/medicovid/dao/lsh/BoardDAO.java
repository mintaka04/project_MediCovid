package kr.co.medicovid.dao.lsh;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.medicovid.dto.BoardDTO;

public interface BoardDAO {
	
	// 공지 뒤에서 10개 가져오기
	@Select("SELECT bno, title, content, writer, regdate, uno FROM board ORDER BY bno DESC LIMIT #{startNo}, 10")
	public List<BoardDTO> selectAll(int startNo);
	
	// 공지 1개 가져오기
	@Select("SELECT bno, title, content, writer, regdate, uno FROM board WHERE bno = #{bno}")
	public BoardDTO selectOne(int bno);
	
	// 총 공지 갯수 가져오기
	@Select("SELECT COUNT(*) FROM board")
	public int getTotalCount();
	
	// 공지사항 추가
	@Insert("INSERT INTO board VALUES(0, #{title}, #{content}, 'admin', now(), 1)")
	public void insertOne(String title, String content);
	
	// 공지사항 수정
	@Update("UPDATE board SET title = #{title}, content = #{content} WHERE bno = #{bno}")
	public void updateOne(String title, String content, int bno);

	// 공지사항 삭제
	@Delete("DELETE FROM board WHERE bno = #{bno}")
	public void deleteOne(int bno);

}
