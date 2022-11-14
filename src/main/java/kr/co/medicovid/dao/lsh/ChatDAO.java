package kr.co.medicovid.dao.lsh;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.medicovid.dto.ChatDTO;

public interface ChatDAO {

	// 채팅방 목록 가져오기
	@Select("SELECT sender, content, count(CASE WHEN status = 1 THEN 1 END) AS status FROM chat WHERE chatDate = curdate() AND sender not in('admin') GROUP BY sender")
	public List<ChatDTO> selectChatRoomList();
	
	// 채팅방에 해당하는 채팅만 가져오기
	@Select("SELECT sender, receiver, content, chatTime FROM chat WHERE sender = #{userId} OR receiver = #{userId}")
	public List<ChatDTO> selectChatBySender(String userId);
	
	// 새채팅 인서트
	@Insert("INSERT INTO chat VALUES(0, #{sender}, #{receiver}, #{content}, curdate(), #{chatTime}, 1)")
	public void insertChat(String sender, String receiver, String content, String chatTime);
	
	@Update("UPDATE chat set status = 0 WHERE sender = #{userId} OR receiver = #{userId}")
	public void updateStatusToRead(String userId);

}
