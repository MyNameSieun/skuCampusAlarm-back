# 설정 방법

1. **MySQL 데이터베이스 설정**

   - MySQL에 `campusalarm`이라는 데이터베이스를 생성합니다.
   - 데이터베이스 생성 쿼리:
     ```sql
     CREATE DATABASE campusalarm;
     ```

2. **프로젝트 설정**

   - `skuCampusAlarm/src/main/resources/application.properties` 파일을 열어 MySQL 설정을 입력합니다.
   - 6번 줄에 MySQL 데이터베이스 사용자 아이디를 입력하고, 7번 줄에 비밀번호를 입력합니다.
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/campusalarm
     spring.datasource.username=YOUR_USERNAME
     spring.datasource.password=YOUR_PASSWORD
     ```

# 실행 방법

1. **프로젝트 빌드 및 실행**

   - Maven을 사용하여 프로젝트를 빌드합니다.
     ```bash
     ./mvnw clean package
     ```
   - 빌드가 완료되면, 다음 명령어로 애플리케이션을 실행합니다.
     ```bash
     java -jar target/skuCampusAlarm.jar
     ```

<br>

<div id="top"></div>

# API 명세서

1. [회원 관련 API](#1)
2. [게시글 관련 API](#2)
3. [댓글 관련 API](#3)

## <span id="1">1.회원 관련 API</span>

### 1.1 회원 가입

- Endpoint: /members/new
- Method: POST
- Request Body:

  ```json
  {
    "name": "John Doe",
    "nickname": "johndoe",
    "email": "john.doe@example.com",
    "password1": "password123",
    "password2": "password123"
  }
  ```

<br>

- Responses:

  - 201 Created: 회원 가입 성공

    ```json
    "회원 가입이 완료되었습니다."
    ```

  - 400 Bad Request: 유효성 검사 실패

    ```json
    "이미 등록된 이메일입니다. 다른 이메일을 사용해주세요."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 1.2 로그인

- Endpoint: /login
- Method: POST
- Request Body:

  ```json
  {
    "email": "john.doe@example.com",
    "password1": "password123"
  }
  ```

<br>

- Responses:

  - 200 OK: 로그인 성공

    ```json
    {
      "id": 1,
      "name": "John Doe",
      "nickname": "johndoe",
      "email": "john.doe@example.com"
    }
    ```

  - 401 Unauthorized: 로그인 실패

    ```json
    "이메일 또는 비밀번호가 올바르지 않습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 1.3 로그아웃

- Endpoint: /logout
- Method: GET
- Responses:

  - 200 OK: 로그아웃 성공

    ```json
    "로그아웃이 성공적으로 완료되었습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 1.4 프로필 조회

- Endpoint: /profile
- Method: GET
- Responses:

  - 200 OK: 프로필 정보 및 게시물 조회

    ```json
    {
      "member": {
        "id": 1,
        "name": "John Doe",
        "nickname": "johndoe",
        "email": "john.doe@example.com"
      },
      "posts": [
        {
          "id": 1,
          "title": "Post Title",
          "content": "Post content",
          "createdAt": "2024-08-23T00:00:00"
        }
      ]
    }
    ```

  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    null
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 1.5 프로필 닉네임 변경

- Endpoint: /profile/updateNickname
- Method: POST
- Request Body:

  ```json
  {
    "newNickname": "newnickname"
  }
  ```

- Responses:
  - 200 OK: 닉네임 변경 성공
    ```json
    "닉네임이 성공적으로 변경되었습니다."
    ```
  - 400 Bad Request: 유효성 검사 실패
    ```json
    "새 닉네임을 입력해주세요."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 1.6 타 사용자 프로필 조회

- Endpoint: /members/{memberId}/profile
- Method: GET
- Path Variables:
  - memberId: 조회할 회원의 ID
- Responses:

  - 200 OK: 회원 프로필 및 게시물 조회

    ```json
    {
      "member": {
        "id": 1,
        "name": "John Doe",
        "nickname": "johndoe",
        "email": "john.doe@example.com"
      },
      "posts": [
        {
          "id": 1,
          "title": "Post Title",
          "content": "Post content",
          "createdAt": "2024-08-23T00:00:00"
        }
      ]
    }
    ```

  - 404 Not Found: 회원 정보 없음
    ```json
    null
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br><br>

## <span id="2">2. 게시글 관련 API</span>

### 2.1 게시글 작성

- Endpoint: /posts/new
- Method: POST
- Request Body:

  ```json
  {
    "title": "Post Title",
    "content": "Post content"
  }
  ```

- Responses:

  - 201 Created: 게시글 작성 성공

    ```json
    "게시글이 성공적으로 작성되었습니다."
    ```

  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    "로그인이 필요합니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 2.2 게시글 목록 조회

- Endpoint: /posts
- Method: GET
- Responses:

  - 200 OK: 게시글 목록 조회

  ```json
  [
    {
      "id": 1,
      "title": "Post Title",
      "content": "Post content",
      "createdAt": "2024-08-23T00:00:00"
    }
  ]
  ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 2.3 게시글 조회

- Endpoint: /posts/{id}
- Method: GET
- Path Variables:
  - id: 조회할 게시글의 ID
- Responses:

  - 200 OK: 게시글 조회

    ```json
    {
    "id": 1,
    "title": "Post Title",
    "content": "Post content",
    "createdAt": "2024-08-23T00:00:00",
    "comments": [ ... ]
    }
    ```

  - 404 Not Found: 게시글 없음

    ```json
    null
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 2.4 게시글 삭제

- Endpoint: /posts/{id}
- Method: DELETE
- Path Variables:
  - id: 삭제할 게시글의 ID
- Responses:

  - 200 OK: 게시글 삭제 성공
    ```json
    "게시글이 성공적으로 삭제되었습니다."
    ```
  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    "로그인이 필요합니다."
    ```

  - 403 Forbidden: 게시글 작성자가 아님

  ```json
  "게시글 작성자만 삭제할 수 있습니다."
  ```

  - 404 Not Found: 게시글 없음

    ```json
    "해당 ID의 게시물을 찾을 수 없습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 2.5 게시글 수정

- Endpoint: /posts/{id}
- Method: PUT
- Request Parameters:
  - title: 게시글 제목 (선택)
  - content: 게시글 내용 (선택)
  - images: 첨부 이미지 (선택)
- Path Variables:
  - id: 수정할 게시글의 ID
- Responses:

  - 200 OK: 게시글 수정 성공

    ```json
    "게시글이 성공적으로 수정되었습니다."
    ```

  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    "로그인이 필요합니다."
    ```

  - 403 Forbidden: 게시글 작성자가 아님

    ```json
    "게시글 작성자만 수정할 수 있습니다."
    ```

  - 404 Not Found: 게시글 없음

    ```json
    "해당 ID의 게시물을 찾을 수 없습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br><br>

## <span id="3">3. 댓글 관련 API</span>

### 3.1 댓글 작성

- Endpoint: /posts/{postId}/comment
- Method: POST
- Request Body:

  ```json
  {
    "content": "This is a comment.",
    "parentId": null
  }
  ```

- Path Variables:
  - postId: 게시글 ID
- Responses:

  - 201 Created: 댓글 작성 성공

    ```json
    "댓글이 성공적으로 작성되었습니다."
    ```

  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    "로그인이 필요합니다."
    ```

  - 400 Bad Request: 유효성 검사 실패

    ```json
    "댓글 내용을 입력해주세요."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 3.2 댓글 수정

- Endpoint: /posts/{postId}/comment/{commentId}
- Method: PUT
- Request Body:

  ```json
  {
    "content": "Updated comment content."
  }
  ```

- Path Variables:
  - postId: 게시글 ID
  - commentId: 수정할 댓글의 ID
- Responses:

  - 200 OK: 댓글 수정 성공

    ```json
    "댓글이 성공적으로 수정되었습니다."
    ```

  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    "로그인이 필요합니다."
    ```

  - 403 Forbidden: 댓글 작성자가 아님

    ```json
    "댓글 작성자만 수정할 수 있습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 3.3 댓글 삭제

- Endpoint: /posts/{postId}/comment/{commentId}
- Method: DELETE
- Path Variables:
  - postId: 게시글 ID
  - commentId: 삭제할 댓글의 ID
- Responses:

  - 200 OK: 댓글 삭제 성공

    ```json
    "댓글이 성공적으로 삭제되었습니다."
    ```

  - 401 Unauthorized: 로그인되지 않은 사용자

    ```json
    "로그인이 필요합니다."
    ```

  - 403 Forbidden: 댓글 작성자가 아님

    ```json
    "댓글 작성자만 삭제할 수 있습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 3.4 댓글 조회

- Endpoint: /posts/{postId}/comments
- Method: GET
- Path Variables:
  - postId: 댓글을 조회할 게시글의 ID
- Responses:

  - 200 OK: 댓글 목록 조회

    ```json
    [
      {
        "id": 1,
        "content": "This is a comment.",
        "createdAt": "2024-08-23T00:00:00",
        "author": {
          "id": 1,
          "nickname": "johndoe"
        }
      }
    ]
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 3.5 댓글에 답글 작성

- Endpoint: /reply/{parentId}
- Method: POST
- Request Body:

  ```json
  {
    "content": "This is a reply."
  }
  ```

- Path Variables:
  - parentId: 답글을 달 댓글의 ID
- Responses:
  - 200 OK: 답글 작성 성공
    ```json
    "답글이 성공적으로 추가되었습니다."
    ```
  - 401 Unauthorized: 로그인되지 않은 사용자
    ```json
    "로그인이 필요합니다."
    ```
  - 403 Forbidden: 삭제된 댓글에 답글 작성 시도
    ```json
    "삭제된 댓글에는 답글을 작성할 수 없습니다."
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>

### 3.6 댓글의 답글 조회

- Endpoint: /comments/{parentId}/replies
- Method: GET
- Path Variables:
  - parentId: 답글을 조회할 댓글의 ID
- Responses:

  - 200 OK: 답글 목록 조회

    ```json
    [
      {
        "id": 2,
        "content": "This is a reply.",
        "createdAt": "2024-08-23T00:00:00",
        "author": {
          "id": 1,
          "nickname": "johndoe"
        }
      }
    ]
    ```

<br>

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br>
