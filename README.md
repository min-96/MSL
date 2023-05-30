# 마쉴랭

![image](https://github.com/min-96/MSL/assets/67457956/b017b501-0ac3-460d-8835-93f8f45667f2)


## 📜프로젝트 소개

- 술에 진심인 사람들을 위한 칵테일 레시피 공유 커뮤니티
- 술, 안주, 사는 얘기를 자유롭게 할 수 있는 공간

## 🔗링크
- [마쉴랭 웹사이트](https://msl-frontend.vercel.app/) (현재는 AWS 비용 문제로 인해 닫아두었습니다)
- [API 명세서](https://api.mashillaeng.site/swagger-ui/index.html#/)


## 😀팀 소개

|         역할 |        이름 |
| --- | --- |
| 프론트엔드 | 정채운 |
| 백엔드 | 김민영 |
| 백엔드 | 이종민 |

## 📚 백엔드 협업 방식
- 정해진 기간동안 둘 다 같은 기능을 만들고, 서로의 코드를 비교하며 더 나은 방식에 대해 토론했습니다.
- 더 낫다고 결론지은 한쪽의 코드를 머지하거나, 아예 새로운 코드를 함께 작성했습니다.

### ⚒기능

- 인증 / 인가
    - 회원가입
    - JWT를 이용한 로그인
    - 일반 유저 / 관리자 권한
- 유저
    - 유저 정보 변경
    - 메일인증을 통한 비밀번호 변경
- 게시판 CRUD
    - 게시글 등록, 수정, 삭제
    - 카테고리별, 유저별, 태그별 게시글 조회
    - 좋아요를 50개 받은 게시물은 인기 게시물로 조회
- 좋아요
    - 게시글 좋아요 입력, 취소
    - 댓글 좋아요 입력, 취소
- 신고
    - 신고를 50개 이상 받은 게시물은 관리자 페이지에서 조회
    - 관리자 권한으로 신고된 게시물 삭제 가능
    - 신고 취소
- 댓글
    - 게시글에 댓글 입력, 수정, 삭제
    - 댓글에 대댓글 입력, 수정 ,삭제
- 유저 팔로우
    - 마음에 드는 유저 팔로우, 언팔로우
    - 팔로우 한 유저들의 게시글 목록만 조회 가능
- 해시태그
    - 게시글 등록 시 해시태그 여러개 입력 가능
    - 해당 해시태그가 달린 게시물만 따로 검색 가능
- 1:1 채팅
    - WebSocket을 이용한 실시간 채팅
    - 채팅 메세지 읽음 / 안읽음 상태 표시
    - 안읽은 채팅이 존재할 때 알림 표시

## **💻**기술 스택

### 백엔드

- Java : 17
- Spring Boot : 2.7.7
- Spring Data JPA
- QueryDsl
- Build : Gradle
- Test : Postman
- DB : MySQL

### 인프라

- Github Actions
- Docker
- AWS EC2
- AWS CodeDeploy
- AWS S3
- AWS ELB
- AWS Route 53
- AWS RDS

## **🛠 Architecture**
![Blank diagram](https://user-images.githubusercontent.com/102534186/229378676-fb655439-34f8-4b8e-8c6d-b7619fb3d934.png)

## 🎯ERD
![image](https://user-images.githubusercontent.com/102534186/229378435-9e838c80-3eb3-4149-b84f-ef276b1db8be.png)


<details>
    
<h2><summary> 📃페이지 </summary><h2>
    
![Pasted Graphic](https://user-images.githubusercontent.com/67457956/229806689-6c796222-d963-4f34-8380-b5ba8ef64d3b.png)

![Pasted Graphic 1](https://user-images.githubusercontent.com/67457956/229808232-ab716863-a3de-4d40-81de-1f722e5a14f8.png)

![Pasted Graphic 2](https://user-images.githubusercontent.com/67457956/229808716-4babb640-f7f1-4a4c-9e6a-078c10f37bbb.png)

![image](https://user-images.githubusercontent.com/67457956/229809273-a8e145fc-de74-4791-aa4e-23ff73b6afae.png)

![Pasted Graphic 4](https://user-images.githubusercontent.com/67457956/229809397-0625eced-6b56-4534-83e1-251eec625c0f.png)

![Pasted Graphic 5](https://user-images.githubusercontent.com/67457956/229809569-b80b274f-74e2-4d6b-80ad-96c410f5c5f5.png)\

![Pasted Graphic 6](https://user-images.githubusercontent.com/67457956/229809835-2eb355d9-1612-4e37-8b88-f8afe32cc79f.png)

![image](https://user-images.githubusercontent.com/67457956/229810850-89e0b58c-953f-41ad-9c02-842a488efb34.png)

![image](https://user-images.githubusercontent.com/67457956/229811170-498bb32a-2847-44df-8ee8-a23f19732bf3.png)

</details>
