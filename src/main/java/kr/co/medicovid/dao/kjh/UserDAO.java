package kr.co.medicovid.dao.kjh;

import org.apache.ibatis.annotations.Delete;

public interface UserDAO {
	
	// 회원 탈퇴
	@Delete("DELETE FROM users WHERE uno = #{uno} ")
	public void delete(int uno);

}
