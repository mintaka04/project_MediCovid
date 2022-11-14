package kr.co.medicovid.dao.lsh;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.co.medicovid.dto.HospitalCategoryDTO;
import kr.co.medicovid.dto.HospitalInfoDTO;

public interface SearchResultDAO {
	
	// 카테고리에 맞는 병원 리스트만 불러옴
	@Select("SELECT h.hno, h.hname, h.haddress, h.htel, h.htime, h.hx, h.hy FROM hospitalInfo h NATURAL JOIN hospitalCategory c WHERE c.ccategory = #{category} LIMIT #{startNo}, 6")
	public List<HospitalInfoDTO> selectByCategory(int category, int startNo);
	
	// 카테고리, 검색어, 권역구분 필터, 페이지에 맞는 병원 리스트 불러옴
	@Select("<script>"
			+ "SELECT h.hno, h.hname, h.haddress, h.htel, h.htime, h.hx, h.hy FROM hospitalInfo h NATURAL JOIN hospitalCategory c "
			+ "<trim prefix='WHERE' prefixOverrides='AND|OR'> "
			+ "<if test = 'category != 0'> "
			+ "AND c.ccategory = #{category} "
			+ "</if> "			
			+ "<if test = 'keyword != null'> "
			+ "AND (h.haddress like concat('%',#{keyword},'%') or h.hname like concat('%',#{keyword},'%')) "
			+ "</if> "
			+ "<if test='guArr.length &gt; 1'> "
			+ "AND h.hgu IN "
			+ "<foreach item='item' index='index' collection='guArr' open='(' separator=',' close=')'> "
			+ "#{item}</foreach> "
			+ "</if> "
			+ "</trim> "
			+ "GROUP BY hno "
			+ "LIMIT #{startNo}, 6 "
			+ "</script>")
	public List<HospitalInfoDTO> selectByFilter(int category, String keyword, String[] guArr, int startNo);
	
	// 위에서 불러온 카테고리, 검색어, 권역구분 필터, 페이지에 맞는 병원들에 연결된 카테고리 리스트 불러옴
	@Select("<script>"
			+ "SELECT c.hno, c.ccategory FROM hospitalInfo h NATURAL JOIN hospitalCategory c "
			+ "<trim prefix='WHERE' prefixOverrides='AND|OR'> "
			+ "<if test = 'category != 0'> "
			+ "AND c.ccategory = #{category} "
			+ "</if> "			
			+ "<if test = 'keyword != null'> "
			+ "AND (h.haddress like concat('%',#{keyword},'%') or h.hname like concat('%',#{keyword},'%')) "
			+ "</if> "
			+ "<if test='guArr.length &gt; 1'> "
			+ "AND h.hgu IN "
			+ "<foreach item='item1' index='index' collection='guArr' open='(' separator=',' close=')'> "
			+ "#{item1}</foreach> "
			+ "</if> "
			+ "<if test = 'hnoList.size() &gt; 0'> "
			+ "AND hno IN "
			+ "<foreach item='item2' index='index' collection='hnoList' open='(' separator=',' close=')'> "
			+ "#{item2}</foreach>"
			+ "</if>"
			+ "</trim> "
			+ "</script>")
	public List<HospitalCategoryDTO> selectCategoryByFilter(int category, String keyword, String[] guArr, ArrayList<Integer> hnoList);
	
	// 카테고리, 검색어, 권역구분 필터에 맞는 총 병원갯수 구하기 
	@Select("<script>"
			+ "SELECT COUNT(DISTINCT hno) FROM hospitalInfo NATURAL JOIN hospitalCategory "
			+ "<trim prefix='WHERE' prefixOverrides='AND|OR'> "
			+ "<if test = 'category != 0'> "
			+ "AND ccategory = #{category} "
			+ "</if> "			
			+ "<if test = 'keyword != null'> "
			+ "AND (haddress like concat('%',#{keyword},'%') or hname like concat('%',#{keyword},'%')) "
			+ "</if> "
			+ "<if test = 'guArr.length &gt; 1'> "
			+ "AND hgu IN "
			+ "<foreach item='item' index='index' collection='guArr' open='(' separator=',' close=')'> "
			+ "#{item}</foreach> "
			+ "</if> "
			+ "</trim> "
			+ "</script>")
	public int getTotalCount(int category, String keyword, String[] guArr);
}
