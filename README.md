# 🛒 LOTTE ON 쇼핑몰 개발 프로젝트

<br>
  
## 📌 프로젝트 주제  

롯데e-커머스 LOTTE ON 쇼핑몰 개발  
(Front Office, Back Office, API 서버)  


<br>

  
## 📋 프로젝트 개요  

### 📎 배경  

본 프로젝트는 쇼핑몰의 기본 기능 개발을 목표로 하며,  
실제 업무와 유사한 환경에서 설계 및 구현을 함을 목표로 한다. 

추가로 **SNS 기능**과 **전시(보여지는 화면) 관리 기능**을 추가하여  
사용자와 판매자 사이의 소통의 장과 홍보효과까지 누리게 하며,  
전시관리를 통하여서는 개발자 및 이용자들에게 편의성을 부여한다. 


### 🎯 목표  

1. 업무와 유사한 환경인 MSA 환경을 기반으로 설계 및 구현함을 목표로 한다.
2. Token을 활용하여 회원을 구분하고, 이를 통해 구분된 사용자, 관리자는 부여된 권한에 따른 화면 및 기능을 볼 수 있어야 한다.
3. 전시 모듈화를 통하여 상품 전시와 편성에 소요되는 직접적인 개발, 운영 시간을 효율화 시킨다.


### 👥 사용 대상  

- 일반 사용자  
- 판매자  
- 관리자  


<br>

  
## ✅ 필수 구현 기능  

### 🔧 Back Office (BO)  

- **사용자**  
  - 회원가입, 로그인  
  - 아이디/비밀번호 찾기 및 재설정  

- **판매자**  
  - 상품 관리 (등록, 수정)  
  - 배송 현황 관리  
  - 리뷰 관리  
  - QnA 관리 (조회, 답변)  
  - 환불 내역 관리 (취소/환불 상세)  


<br>

  
### 💻 Front Office (FO)  

- **사용자**  
  - 메인 페이지  
  - 회원가입, 로그인/로그아웃  
  - 아이디/비밀번호 찾기 및 재설정  
  - 상품 리스트 및 상세보기  
  - 장바구니, 찜 목록  
  - 주문하기 및 주문 상세보기  
  - 마이페이지  
    - 회원정보 수정  
    - 내 글 관리 (작성 가능한 리뷰, 리뷰 목록, 문의 목록)  
    - 주문 관리 (주문 내역, 취소 내역)  
  - 리뷰 작성 및 문의 작성  

- **판매자**  
  - 대시보드 및 판매 지표  
  - 상품 등록/수정/리스트  
  - 리뷰 관리  
  - QnA 관리  


<br>

  
### 🔗 API 서버  

- FO/BO 간 통신을 위한 API 서버 구현  


<br>

  
## ➕ 추가 구현 기능  

### 🔧 Back Office (BO)  

- **SNS 기능**  
  - SNS 업로드  
  - 구매 상품 태그  
  - SNS 리스트  
  - SNS 공유하기  

- **전시 기능**  
  - 상세보기 구좌 관리  
  - 배너 관리  
  - 뱃지 관리  
  - 전시 관리  


<br>

  
### 💻 Front Office (FO)  

- **SNS**  
  - SNS 업로드 및 리스트 조회  
  - SNS 상세보기  
    - 공유하기  
    - 태그 상품 이동  
    - 댓글 기능  

- **전시**  
  - 구좌 등록 및 목록 조회  
  - 모듈 등록 및 목록 조회  
  - 배너 생성  
  - 뱃지 생성  
  - 전시 모듈 추가, 순서 변경, 설정  


<br>

  
## 🧾 산출물 (Outputs)  

- ER Diagram  
- S/W Stack  
- 사용 라이브러리 정보  
- 사용자 별 플로우차트  
- 화면 별 스토리보드  
- API 정의서  
- 인프라 구성도  
- 아키텍처 설계 문서  
- 테스트 시나리오 및 결과서  


<br>

  
## 🛠️ 사용 기술 스택  

| 영역 | 기술 |
|------|------|
| **API 서버** | SpringBoot (Java 기반) |
| **인프라** | AWS EC2, AWS S3, Docker |
| **데이터베이스** | MySQL |
| **개발 환경** | SPA, MSA 구조 |
| **배포 환경** | Jenkins, GitLab |
| **기타 기능** | Sleuth, Zipkin (WAS 간 Trace), Circuit Breaker |


<br>

  
## 👨‍👩‍👧‍👦 팀원 소개  

<table style="width: 500px;">
  <thead>
    <tr>
      <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;역할&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
      <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이름&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;팀장&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;정진우&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
      <td rowspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;팀원&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;김준서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;곽혜수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;변준영&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
  </tbody>
</table>
