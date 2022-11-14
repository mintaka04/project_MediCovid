package kr.co.medicovid.dao.hjj;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.medicovid.dto.UsersDTO;

public interface UserInfoDAO {
	
    //이메일검사
	@Select("SELECT uid FROM users where uid=#{uid}")
	String select(String uid);  //dto로 받을지String = 쿼리문 리턴값인지를 내가 정하면된다 
	

	//일반로그인
	@Select("SELECT * FROM users where uid=#{uid} and upw=#{upw}")
	UsersDTO selectTwo(@Param("uid") String uid , @Param("upw")String upw);
	

	//회원가입
	@Insert("INSERT INTO users values(#{uno},#{uid},#{upw},#{uname},#{utel},'일반',0,'ROLE_USER')")
	public void insert(UsersDTO dto);
	
	@Select("SELECT uid from users where utel=#{utel}")
	String getFindId(int utel);
	
	// 시큐리티 로그인
	@Select("SELECT * FROM users where uid=#{uid}")
	UsersDTO selectOne(@Param("uid") String uid);
   //유저로그인
   @Select("SELECT * from users where uid=#{uid} and uloginType=#{uloginType}")
   UsersDTO getSnsOne(@Param("uid")String uid,@Param("uloginType")String uloginType);
	

	//sns 회원 가입
   @Insert ("INSERT INTO users(uno,uid,upw,uname,utel,uloginType,penalty,role) "
		   +"values(#{uno},#{uid},#{upw},#{uname},#{utel},#{uloginType},0,'ROLE_USER')")
   int insertSNS(UsersDTO dto);
   
   //비밀번호찾기
   @Select("select * from users where uid=#{uid}")
   UsersDTO  findUsersPwd(@Param("uid")String uid);
   
   //일반 회원 패스워드 계정정보 확인후 패스워드 변경
   @Update("UPDATE users set upw = #{upw} WHERE uno=#{uno}")
   int updatePw(@Param("upw")String upw,@Param("uno")int uno); //여기서 upw 임시패스워드
	
}
