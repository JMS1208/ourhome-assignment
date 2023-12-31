## 1. API 명세 
|기능|HTTP 메서드|API Path|Request|Response|
|:------|:---|:-------|:----|----:|
|회원가입|POST|/public/sign-up|[SignUpRequestDto](README.md#signuprequestdto)|String|
|로그인|POST|/public/sign-in|[SignInRequestDto](README.md#signinrequestdto)|JwtToken|
|Body 인증 정보로 인증 후 데이터 조회|POST|/public/orders|SearchRequestDto|OrderHistoryDto|
|Header 토큰으로 인증 후 데이터 조회|GET|/api/orders||OrderHistoryDto|


## 2. DTO
### 1) 회원가입 
#### SignUpRequestDto
```
{
    "id": string;
    "pw": string;
}
```

##### 예시
```
{
    "id": "ourhomeid",
    "pw": "!ourhomep$"
}
```


#### String
```
{
  "result": string;
  "rtnCd": number;
  "rtnMsg": string;
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
