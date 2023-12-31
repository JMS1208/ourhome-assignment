## 1. API 명세 
|기능|HTTP 메서드|API Path|Request|Response|
|:------|:---|:-------|:----|----:|
|회원가입|POST|/public/sign-up|[SignUpRequestDto](README.md#signuprequestdto)|[SignUpResponseDto](README.md#signupresponsedto)|
|로그인|POST|/public/sign-in|[SignInRequestDto](README.md#signinrequestdto)|[SignInResponseDto](README.md#signinresponsedto)|
|Body 인증 정보로 인증 후 데이터 조회|POST|/public/orders|[OrderHistoryRequestDto](README.md#orderhistoryrequestdto)|[OrderHistoryResponseDto](README.md#orderhistoryresponsedto-body-방식)|
|Header 토큰으로 인증 후 데이터 조회|GET|/api/orders||[OrderHistoryResponseDto](README.md#orderhistoryresponsedto-header-방식)|


## 2. DTO
### 1) 회원가입 
#### SignUpRequestDto
```
{
    "id": string,
    "pw": string
}
```

##### 예시
```
{
    "id": "ourhomeid",
    "pw": "!ourhomep$"
}
```


#### SignUpResponseDto
```
{
  "result": string,
  "rtnCd": number,
  "rtnMsg": string
}
```
##### 예시
```
{
    "result": null,
    "rtnCd": 200,
    "rtnMsg": "정상 처리 완료"
}
```

### 2) 로그인 
#### SignInRequestDto
```
{
    "id": string,
    "pw": string
}
```

##### 예시
```
{
    "id": "ourhomeid",
    "pw": "!ourhomep$"
}
```


#### SignInResponseDto
```
{
  "result": {
    "value": string,
    "expiredAt": string
  },
  "rtnCd": number,
  "rtnMsg": string
}
```
##### 예시
```
{
    "result": {
        "value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzAzOTQyNDQwLCJleHAiOjE3MDY1MzQ0NDB9.QJ4XhicRr3OmzNN1rC8dugb7tu-4_YCkrS9K8wNJ78g",
        "expiredAt": "2024-01-29T13:20:40.277+00:00"
    },
    "rtnCd": 200,
    "rtnMsg": "정상 처리 완료"
}
```

### 3) Body 인증 정보로 인증 후 데이터 조회
#### OrderHistoryRequestDto
```
{
    "id": string,
    "pw": string
}
```

##### 예시
```
{
    "id": "ourhomeid",
    "pw": "!ourhomep$"
}
```


#### OrderHistoryResponseDto (Body 방식)
```
{
  "result": {
    "value": string,
    "expiredAt": string
  },
  "rtnCd": number,
  "rtnMsg": string
}
```
##### 예시
```
{
    "result": {
        "value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzAzOTQyNDQwLCJleHAiOjE3MDY1MzQ0NDB9.QJ4XhicRr3OmzNN1rC8dugb7tu-4_YCkrS9K8wNJ78g",
        "expiredAt": "2024-01-29T13:20:40.277+00:00"
    },
    "rtnCd": 200,
    "rtnMsg": "정상 처리 완료"
}
```

### 4) Header 토큰으로 인증 후 데이터 조회 

#### OrderHistoryResponseDto (Header 방식)
```
{
  "result": {
    "value": string,
    "expiredAt": string
  },
  "rtnCd": number,
  "rtnMsg": string
}
```
##### 예시
```
{
    "result": {
        "value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzAzOTQyNDQwLCJleHAiOjE3MDY1MzQ0NDB9.QJ4XhicRr3OmzNN1rC8dugb7tu-4_YCkrS9K8wNJ78g",
        "expiredAt": "2024-01-29T13:20:40.277+00:00"
    },
    "rtnCd": 200,
    "rtnMsg": "정상 처리 완료"
}
```
