# Spring Board
> Spring Boot 3 기반으로 하는 게시판 프로젝트입니다.

![홈 화면](https://github.com/ohoon/spring-board-v2/assets/46547443/bc668bcd-5268-439a-b3c8-87a4bbb9c2eb)

## 목차
- [들어가며](#들어가며)
  - [프로젝트 소개](#1-프로젝트-소개)    
  - [프로젝트 기능](#2-프로젝트-기능)    
  - [사용 기술](#3-사용-기술)   
     - [백엔드](#3-1-백엔드)
     - [프론트엔드](#3-2-프론트엔드)
  - [실행 화면](#4-실행-화면)   

- [구조 및 설계](#구조-및-설계)
  - [패키지 구조](#1-패키지-구조)
  - [DB 설계](#2-db-설계)

- [마치며](#마치며)

## 들어가며
### 1. 프로젝트 소개

기본적인 CRUD 기능과 페이징, 검색, 사용자 인증, 인가, 소셜 로그인 등을 구현해보면서
Spring Security, Spring Data Jpa, Querydsl, OAuth 2.0 등의 기술들을 학습하고 사용하는 경험을 쌓기 위해 시작한 프로젝트입니다.

### 2. 프로젝트 기능

- **회원 -** 회원가입, 로그인 (Spring Security), 소셜 로그인 (OAuth 2.0), 회원 정보 수정/탈퇴
- **게시글 -** CRUD 기능, 조회수, 추천, 페이징 및 검색
- **댓글 -** CRD 기능, 대댓글, 페이징
- **기타 -** 사용자 입력값 검증

### 3. 사용 기술

#### 3-1 백엔드

##### 주요 프레임워크 / 라이브러리
- Java 17
- SpringBoot 3.1.2
- Spring Security
- Spring Data Jpa
- Querydsl 5.0.0
- OAuth 2.0

##### Build Tool
- Gradle

##### DataBase
- H2 Database 2.1.214

#### 3-2 프론트엔드
- JavaScript
- Thymeleaf 3.1
- Bootstrap 5.3.1
- summernote 0.8.18


### 4. 실행 화면

  <details>
    <summary>회원</summary>   
     
  **1. 회원가입 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/a64f37f9-e50c-4403-b639-de3446dbd376)   
  회원가입 시 사용자 입력값을 검증하고 아이디가 중복되지 않은지 확인한다. <br/>
  회원가입 완료 시 회원 정보를 저장하고 로그인 화면으로 이동한다.   
     
  **2. 로그인 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/d5db1981-f4de-4f85-85e1-b6f963350e49)   
  일치하는 아이디, 비밀번호가 아닐 시 로그인 실패 메시지가 나온다. <br/>
  OAuth 2.0 기반의 구글과 네이버 소셜 로그인을 지원한다. <br/>
  로그인에 성공하면 전체 게시글 목록 화면으로 이동한다.   
     
  **3. 회원정보 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/1d44aa16-b857-414e-ac21-7ef65ae7284e)   
  아이디, 닉네임, 이메일, 등록일, 최종 변경일을 보여주며 비밀번호 변경이나 회원정보 수정/탈퇴가 가능하다. <br/>
  소셜 로그인의 경우에는 비밀번호 변경과 회원 탈퇴 버튼이 보이지 않고 닉네임, 이메일만 변경할 수 있다.
           
  </details>
  
  <br/>   
  
  <details>
    <summary>게시글</summary>   
       
    
  **1. 전체 게시글 목록 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/ff2a6861-b541-4ad7-8da4-55edd5bb32f3)   
  전체 게시글 목록을 페이징 처리하여 한 페이지당 일정 개수의 게시글만 조회할 수 있다.   
     
  **2. 게시글 작성/수정 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/3680be7b-2f47-4969-9f43-d517c446f35d)   
  로그인 한 사용자만 새로운 글을 작성할 수 있고, 작성이 끝나면 전체 게시글 목록 화면으로 이동한다.   <br/> 
  수정 화면은 작성자 본인이 아니면 Forbidden 에러와 함께 홈 화면으로 돌아간다.
     
  **3. 게시글 보기 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/f1e97008-5639-4a53-93d2-df014da1f496)   
  사용자가 보기 화면에 들어올 때마다 조회수가 카운팅된다. <br/>
  한 사용자 ID당 각각의 게시글에 1회씩 추천 가능하다. <br/>
  작성자 본인이 아니면 게시글 수정/삭제 버튼이 보이지 않는다. <br/>
  게시글 삭제는 실제로 데이터를 지우는 것이 아닌 목록에서 보이지 않도록 하는 방식을 사용한다.
  
  **4. 게시글 검색 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/e33e56c7-50b0-48f7-9754-b256f1ee7f49)   
  검색 조건에는 제목/내용, 제목, 내용, 작성자, 댓글이 있으며, 키워드와 조합되어 조건에 맞는 게시글만 보여준다. <br/>
  검색된 결과도 페이징 처리되어 보여진다. <br/>
  상단 네비게이션에서 통합 검색을 지원한다.
     
  </details>

  <br/>   
  
  <details>
    <summary>댓글</summary>   
       
  **1. 댓글 화면**   
  ![image](https://github.com/ohoon/spring-board-v2/assets/46547443/dc146438-2cd8-42b5-90a0-4a7f1fd962fd)   
  로그인 한 사용자만 댓글을 달 수 있으며, 댓글 내용을 클릭하면 대댓글을 작성하는 입력칸이 나온다. <br/>
  댓글 삭제는 게시글과 동일하게 댓글 목록에서만 보이지 않는다. <br/>
  대댓글이 있는 부모 댓글을 삭제한 경우에는 (작성자가 삭제한 댓글입니다.) 라고 표시된다.
           
  </details>
  <br/>   
 
   
## 구조 및 설계   
   
### 1. 패키지 구조
   
![package](https://github.com/ohoon/spring-board-v2/assets/46547443/2d73c742-27e6-4a26-8daf-ade6f4b8d9d5)

     
 ### 2. DB 설계
 
![erd](https://github.com/ohoon/spring-board-v2/assets/46547443/d6282bc5-137e-4069-9206-b9d7c72c9a89)   
   
<br/>

## 마치며   

<details>
  <summary>보완 사항</summary>
     
- 게시글 보기 화면 밑에도 전체 게시글 목록 넣기
- 말머리 기능 (공지글) 추가하기
- 추천 n개 이상의 게시글 또는 추천 기준 게시글 정렬하기
- 어드민 페이지 추가하기
- Docker로 가상화하기
- CI/CD 구축하기
- aws 서버로 호스팅하기
  
</details>   

<br/>

<details>
  <summary>막혔지만 해결한 경험</summary>
  
- 작성자 컬럼을 따로 STRING으로 둘지, 매번 member와 join해서 가져올지 고민 <br/>
  -> 작성자를 따로 저장해서 글 목록을 불러오는 것과 같은 상황에서 join 과정을 생략할 수 있기 때문에 컬럼을 따로 추가하기로 결정함
  
- dto와 entity 변환은 서비스, 컨트롤러 어느 레이어에서 해야할까 고민 <br/>
  -> 컨트롤러에 entity가 넘어가면 프레젠테이션 계층에 중요한 정보인 entity가 노출되어서 보안 상 위험 <br/>
  -> 그러나 서비스에서 dto로 변환해서 넘겨주면 웹용 컨트롤러, api 컨트롤러에게 각각 명세에 맞는 dto를 만들어서 줘야함 (중복 코드 발생) <br/>
  -> 웹 컨트롤러만 있는 프로젝트라서 서비스 레이어에서 dto로 변환하기로 결정함
  
- 소셜 로그인때는 비밀번호가 필요없는데 비밀번호 컬럼을 어떻게 처리해야할까 고민 <br/>
  -> 비밀번호 인증용 테이블과 소셜로그인용 테이블을 따로 만들어놓고 덜 민감한 정보가 있는 member 테이블과 join해서 인증하도록 설계함

- 조회수 증가 로직에서 동시성 이슈가 발생할 가능성이 보임 <br/>
  -> 1씩 증가하는 로직에는 낙관적 락을 걸어서 동시성 문제가 발생하지 않도록 했음 <br/>
  -> 컬럼을 통째로 덮어씌우는 로직은 동시성 이슈가 발생하지 않는다고 보고 락을 걸지않고 그대로 구현

- 소셜로그인 provider마다 api 명세가 달라서 스프링에서 기본으로 지원하는 oauth2 userinfo 서비스에서 정보를 읽어오지 못하고 에러를 뿜음 <br/>
  -> defaultOAuth2UserService를 참고해서 회원 속성을 가져오는 부분만 provider에 맞게 가져오도록 구현해서 사용함
  
</details>  

