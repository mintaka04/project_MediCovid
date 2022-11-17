# project_MediCovid
![medi_main](https://user-images.githubusercontent.com/103241214/202333281-d7ed8ce3-17cd-431a-9102-62d5b3e31b51.png)
코로나 시대에 관련 병원들에 대한 서비스를 하나의 웹사이트에서 접근할 수 있었으면 좋을 것 같아 제작한 사이트입니다.
  
+ 일반 사용자(환자) : 관련 서비스를 제공하는 병원들에 대한 정보를 얻거나 진료를 예약할 수 있습니다.
+ 병원 : 시간대별 받을 수 있는 환자들의 수를 조정할 수 있으며, 환자들의 예약을 관리, 과거 예약내역에 대한 접근이 가능합니다.
+ 사이트 관리자 : 예약을 하고 병원에 나타나지 않은 일반 사용자에 대한 페널티를 부과하고, 채팅을 통해 FAQ에 답변할 수 있습니다.
  
+ 프로젝트 기간 : 07/12 ~ 08/02
+ 프로젝트 참가 인원 : 5명
  
  
  
## 주요 기능
+ 로그인, 회원가입 (네이버, 카카오)
+ 가까운 위치의 병원 확인, 구별 코로나 감염자 수 확인
+ 병원 검색 / 필터링 / 주변 약국 확인 / 병원 예약
+ 병원 후기 별점
+ 병원 측과 사이트 관리자 측에서도 예약에 대한 정보와 페널티에 대해 다룰 수 있음
  
  
  
## Stacks
Spring Framework, JSP, JavaScript, MySQL, AWS
  
  
  
## 본인이 구현한 기능
  
### 병원 상세 페이지
![medi_hop_detail](https://user-images.githubusercontent.com/103241214/202335085-db8f40af-86c9-4aa0-b30d-932517727d99.png)
+ 병원 상세 페이지에서는 지도 api에 표시되는 위치와 평점, 병원의 상세 정보를 볼 수 있습니다.
+ 평점의 경우, 하루에 한 번 평균을 내어 반영됩니다.
+ 서울시 공공데이터에서 가져온 해당 병원의 반경 200m 이내에 존재하는 약국 리스트를 확인할 수 있으며, 클릭시 지도에 체크되는 위치를 볼 수 있습니다.
![medi_make_reserve](https://user-images.githubusercontent.com/103241214/202336089-3c20a001-c2ff-4987-b5ef-a0f9357e8b42.png)
+ 사이트 회원의 경우 예약하기 기능을 통해 원하는 날짜와 시간에 병원을 예약할 수 있습니다.

### 병원 관리자 메인 페이지
병원측 아이디로 로그인 했을 때 접근 가능한 페이지입니다.
![medi_adminhop_main](https://user-images.githubusercontent.com/103241214/202344207-6f920ed5-63ce-4f4b-b57e-18e9fddf0d13.png)
+ 일일 예약 관리를 통해 현재 날짜 기준 +14일(주말 제외)에 해당하는 예약 내역을 확인할 수 있습니다.
(이전날, 다음날 버튼을 이용)
+ 내방확인/내방취소 버튼을 통해 환자의 내방여부를 체크할 수 있습니다.
(내방확인이 되지 않은 환자는 과거 예약 내역에서 확인 불가능)
+ 과거 예약 내역을 통해 7일 전, 30일 전, 90일 전의 내역을 확인할 수 있습니다. 

### 병원 예약 가능 인원 관리 페이지
병원 관리자 메인 페이지에서 하단의 예약 인원 설정 버튼 클릭시 이동 가능합니다.
![medi_adminhop_revmem](https://user-images.githubusercontent.com/103241214/202343874-43675e4d-d4eb-43be-a311-37bceeded4f2.png)
+ 현재 날짜 기준 +14일에 해당하는 일자별, 그리고 병원에서 설정한 운영 시간 내의 시간별 예약된 인원수와 예약 가능 인원수를 확인할 수 있습니다.
![medi_adminhop_revmem_change](https://user-images.githubusercontent.com/103241214/202344584-35cba7f7-e6a0-48a2-a94b-11f577f8002c.png)
+ 각 시간 클릭시 해당 시간의 예약 가능 인원을 변경할 수 있습니다.
+ 이 때 이미 예약된 인원수 이상만 가능합니다.
+ 예약 가능한 최대 인원수는 병원 정보 수정 페이지에서 수정 가능합니다.

#### 병원 정보 수정 페이지
병원 관리자 메인 페이지에서 하단의 병원 정보 수정 버튼 클릭시 이동 가능합니다.
![medi_adminhop_hopinfo](https://user-images.githubusercontent.com/103241214/202345207-0f36021d-655b-402e-985d-e298d6e4c42a.png)
+ 병원에 대한 정보를 수정할 수 있습니다.
