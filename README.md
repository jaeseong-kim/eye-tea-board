## Eye-Tea-Board
<div align="center"><img src="./img/eyeteaboard-logo.jpg" width="300" height="300"></div>

## Web Page
홈페이지 : [EyeTeaBoard](ec2-13-209-106-121.ap-northeast-2.compute.amazonaws.com)  
노션 : [EyeTeaBoard](https://www.notion.so/EyeTeaBoard-7f8a0e6801734f5eaaf2cec07882cb4b) 
개발기간 : 2023.04.14 ~ 2023.05.16

### 프로젝트 소개
차를 마시며 IT를 주제로 여러가지 이야기를 나누는 CRUD 게시판입니다.

## Stacks
### 환경
<img src="https://img.shields.io/badge/Github-181717?style=flat&logo=Github&logoColor=white">

### 개발
<img src="https://img.shields.io/badge/Java-lightgrey?style=flat"/> <img src="https://img.shields.io/badge/Spring Boot-grenn?style=flat&logo=Spring Boot&logoColor=6DB33F"/> <img src="https://img.shields.io/badge/MariaDB-003545?style=flat&logo=MariaDB&logoColor=white"/>

### CI / CD
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=flat&logo=GitHUb Actions&logoColor=white"> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=Amazon S3&logoColor=white"> <img src="https://img.shields.io/badge/Amazon CodeDeploy-368CCB?style=flat"> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat&logo=Amazon EC2&logoColor=white">

### DB
<img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat&logo=Amazon RDS&logoColor=white"/>

### 서버
<img src="https://img.shields.io/badge/Nginx-009639?style=flat&logo=NGINX&logoColor=white">

## DB ERD
<img src="./img/eyeteaboard-erd.png" width="750" height="550">

## CI/CD Flow
<img src="./img/cd_cd_workflow.png" width="750" height="550">

## 주요 기능
- ### 방문자
  - 게시글 읽기
  - 댓글 읽기

- ### 유저
|게시글 쓰기|게시글 수정|
|:---:|:---:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/4a3d7148-05a8-4915-8fb4-a1c3b7d7a933" width="100%" height="100%"/>|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/93de227a-ebb3-4769-a0c9-577cd3d324ab" width="100%" height="100%"/>|

|게시글 좋아요|게시글 삭제|
|:---:|:---:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/d3e35175-7420-4082-ae96-e65a282d99f3" width="100%" height="100%"/>|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/0136cb63-c862-48f9-8f62-021e6dd3c699" width="100%" height="100%"/>|

|댓글 쓰기|댓글 삭제|
|:---:|:---:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/bb854f73-4d7e-4401-8653-bfed04b18471" width="100%" height="100%"/>|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/4974bb52-aa51-4bea-84a0-1af02fc9f324" width="100%" height="100%"/>|

|                                                                  댓글 좋아요                                                                  |
|:----------------------------------------------------------------------------------------------------------------------------------------:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/d0b51859-b64e-4f17-8a82-f87f72bdc5a9" width="100%" height="100%"/>|
  
|                                                    개인 정보 수정                                                     |                                                     내가 쓴 게시글                                                    |
|:---------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/3f760d2f-6b63-4444-8d29-96fe3cedccfc" width="100%" height="100%"/> | <img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/6c48b32c-f05b-41ee-ac04-eee4667b4e8d" width="100%" height="100%"/> |

|                                                        내가 쓴 댓글                                                        |
|:---------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/2e7f75d2-f2e7-431c-899b-6fa221c41c97" width="100%" height="100%"/> |

- ### 관리자
|회원정보 열람&사용자 정지|게시글 삭제|
|:---:|:---:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/eb276be8-79f2-425d-b72b-f85bae219818" width="100%" height="100%"/>|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/1ffce7d3-7b6a-42dd-9e81-e45c0c56fae7" width="100%" height="100%"/>|<img src="" widht="100%" height="100%"/>|

|댓글 삭제|
|:---:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/574b4803-6c98-444f-b797-2d27b95555a3" widht="100%" height="100%"/>|

- ### 공통
|카테고리별로 보기|좋아요순으로 정렬|
|:---:|:---:|
|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/2b44e0dd-e5c3-458e-872e-9cbfb893b12d" width="100%" height="100%"/>|<img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/a6049332-29bd-4663-8475-771d8e106c78" width="100%" height="100%"/>|

|                                                                   페이지 기능                                                                   |
|:------------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/jaeseong-kim/eye-tea-board/assets/61345774/5f3a0068-aa73-451a-96ee-ccd8c815cf8c" width="100%" height="100%"/> |

## 유지/보수
  - 게시글 수정 보안 이슈 해결
  - Pagination 기능 추가(게시글 리스트, 관리자 페이지)
  - 마이페이지 기능 추가(내 게시글, 내 댓글, 개인정보 수정)
  - 컨트롤러단 유효성 검사