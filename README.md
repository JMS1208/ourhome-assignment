## 1. API 명세 
|기능|HTTP 메서드|API Path|Request|Response|
|:------|:---|:-------|:----|----:|
|회원가입|POST|/public/sign-up|[SignUpRequestDto](README.md#signuprequestdto)|[SignUpResponseDto](README.md#signupresponsedto)|
|로그인|POST|/public/sign-in|[SignInRequestDto](README.md#signinrequestdto)|[SignInResponseDto](README.md#signinresponsedto)|
|액세스 토큰 갱신|POST|/public/token|[RefreshTokenRequestDto](README.md#refreshtokenrequestdto)|[RefreshTokenResponseDto](README.md#refreshtokenresponsedto)|
|Body 인증 정보로 인증 후 데이터 조회|POST|/public/orders?page=0&size=10&direction=DESC|[OrderHistoryRequestDto](README.md#orderhistoryrequestdto)|[OrderHistoryResponseDto](README.md#orderhistoryresponsedto-body-방식)|
|Header 토큰으로 인증 후 데이터 조회|GET|/api/orders?page=0&size=10&direction=DESC||[OrderHistoryResponseDto](README.md#orderhistoryresponsedto-header-방식)|


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

### 3) 액세스 토큰 갱신 
#### RefreshTokenRequestDto
```
{
    "accessToken": string,
    "refreshToken": string
}
```

##### 예시
```
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzA0MDM0NTI4LCJleHAiOjE3MDQ2MzkzMjh9.cpX3xNcm7HEF9o730sJIcMRznnEgImibylQBI2K2vlU",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzA0MDM0NTI4LCJleHAiOjE3MDY2MjY1Mjh9.d9xY10t5hZ4FGRhFSaZUHDTWKyGKeMBk-kSJLZk9jGM"
}
```


#### RefreshTokenResponseDto
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
        "value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzA0MDM0NTc3LCJleHAiOjE3MDQ2MzkzNzd9.vwsZ5XwlEDbWYhrtJt04bZbw7oJq8gwA1ZS-P0ll0Lo",
        "expiredAt": "2024-01-07T14:56:17.280+00:00"
    },
    "rtnCd": 200,
    "rtnMsg": "정상 처리 완료"
}
```

### 4) Body 인증 정보로 인증 후 데이터 조회
#### 쿼리 파라미터
|Name|Description|Default|Type|Required|Option|
|:------|:---|:---|:-------|:----|---:|
|page|오프셋|0|number|false||
|size|크기|10|number|false||
|direction|정렬|DESC|string|false|[ASC/DESC]|

#### OrderHistoryRequestDto
```
{
    "body": {
        "id": string,
        "pw": string
    },
    "token": string
}
```

##### 예시
```
{
    "body": {
        "id": "ourhomeid",
        "pw": "!ourhomepw$"
    },
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdXJob21laWQiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzAzODc1MzUzLCJleHAiOjE3MDY0NjczNTN9.Zujkz7F_LJ-Fsh_JNRPYW7sSTl-pG07M6w_yGSL2l0w"
}
```


#### OrderHistoryResponseDto (Body 방식)
```
{
  "result": {
    "orders": [
      {
        "id": number,
        "orderedAt": string,
        "orderDetails": [
          {
            "id": number,
            "product": {
              "id": number,
              "name": string,
              "price": number
            },
            "quantity": number,
            "price": number
          }
        ]
      }
    ]
  },
  "rtnCd": number,
  "rtnMsg": string
}
```
##### 예시
```
{
    "result": {
        "orders": [
            {
                "id": 1,
                "orderedAt": "2023-01-01T10:00:00",
                "orderDetails": [
                    {
                        "id": 1,
                        "product": {
                            "id": 1,
                            "name": "빵",
                            "price": 1000
                        },
                        "quantity": 2,
                        "price": 2000
                    },
                    {
                        "id": 2,
                        "product": {
                            "id": 2,
                            "name": "떡볶이",
                            "price": 3000
                        },
                        "quantity": 1,
                        "price": 3000
                    }
                ]
            },
            {
                "id": 2,
                "orderedAt": "2023-01-02T10:00:00",
                "orderDetails": [
                    {
                        "id": 3,
                        "product": {
                            "id": 3,
                            "name": "갈비",
                            "price": 10000
                        },
                        "quantity": 1,
                        "price": 10000
                    },
                    {
                        "id": 4,
                        "product": {
                            "id": 4,
                            "name": "김밥",
                            "price": 2000
                        },
                        "quantity": 2,
                        "price": 4000
                    }
                ]
            }
        ]
    },
    "rtnCd": 200,
    "rtnMsg": "select success"
}
```

### 5) Header 토큰으로 인증 후 데이터 조회 

#### OrderHistoryResponseDto (Header 방식)
```
{
  "result": {
    "orders": [
      {
        "id": number,
        "orderedAt": string,
        "orderDetails": [
          {
            "id": number,
            "product": {
              "id": number,
              "name": string,
              "price": number
            },
            "quantity": number,
            "price": number
          }
        ]
      }
    ]
  },
  "rtnCd": number,
  "rtnMsg": string
}
```
##### 예시
```
{
    "result": {
        "orders": [
            {
                "id": 1,
                "orderedAt": "2023-01-01T10:00:00",
                "orderDetails": [
                    {
                        "id": 1,
                        "product": {
                            "id": 1,
                            "name": "빵",
                            "price": 1000
                        },
                        "quantity": 2,
                        "price": 2000
                    },
                    {
                        "id": 2,
                        "product": {
                            "id": 2,
                            "name": "떡볶이",
                            "price": 3000
                        },
                        "quantity": 1,
                        "price": 3000
                    }
                ]
            },
            {
                "id": 2,
                "orderedAt": "2023-01-02T10:00:00",
                "orderDetails": [
                    {
                        "id": 3,
                        "product": {
                            "id": 3,
                            "name": "갈비",
                            "price": 10000
                        },
                        "quantity": 1,
                        "price": 10000
                    },
                    {
                        "id": 4,
                        "product": {
                            "id": 4,
                            "name": "김밥",
                            "price": 2000
                        },
                        "quantity": 2,
                        "price": 4000
                    }
                ]
            }
        ]
    },
    "rtnCd": 200,
    "rtnMsg": "select success"
}
```
