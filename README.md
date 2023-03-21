# 블로그 검색 기능 개발 

구현 사항 
---
#### 생성 API 
- 블로그 검색 API 
    - 키워드에 따른 검색 
- 검색 랭킹 API 
    - 만이 검색된 키워드 랭킹 조회
---  
#### 프로젝트 환경 
- java 11
- spring boot 2.7.9
- grale 
- h2 
---  
#### 프로젝트 구조 
- service : 실제 서비스 로직들 처리 
  - common : config, exception, response등 공통 처리 
  - controller : api 진입 controller 
  - usecae : 실제로 business logic이 처리되는 부분 
- store : 외부 통신, DB등의 데이터를 가져와야하는 처리
  - dataprovider : 데이터 처리 interface
  - mode : db 데이터 처리 
  - webapi : 외부 통신
---  
#### 프로젝트 실행 
- java -jar . blog-0.0.1-SNAPSHOT.jar
  - 경로는 다운받은 jar 파일 위치에 맞춰 명령어 실행 , 직접 빌드시 ./build/libs/ 경로)
---  
#### 명세서 작성 
- Spring Rest Docs 활용 
- jar 파일 실행 이후 localhost:8080/docs/index.html 접속 
---  
#### 동시성 제어 
- Pessimistic Lock 비관적 잠금 방법 사용 
- keyword 조회시 해당 @Lock을 통해서 해당 row lock 
---
#### 트레픽 많은 상황 대비 
- 로컬 캐시(caffeine) 적용 
---
#### 카카오 검색 API 문제 발생시 
- 네이버 검색을 통한 결과 임시 제공 
---
#### **외부 라이브러리** 
- modelmapper : 간편한 객체 변환 
- spring-restdocs-mockmvc : api 명세서 작성 
- caffeine cache : 로컬 캐시 적용 