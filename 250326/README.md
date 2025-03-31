# 250326 수

### 완료한 일
- 연희 팀원과 api 명세 작성 완료
- config server를 최종적으로 배포했다. jks파일도 문제가 없다.


### 완료하지 못한 일
- gitlab master 브랜치에 merge request 템플릿을 넣으려고 했는데 잘 안 넣어진다.. 브랜치를 따로 파서 템플릿을 넣은 다음에 master브랜치에 merge request를 하고 다른 팀원이 approve까지 해줬는데도 master브랜치에 merge request가 안 된다. master에만 merge request를 막아놓은 것인지 확인이 필요하다.
<br><br><br>
---

### 알게된 것
- postman에도 api 명세를 작성할 수 있는 documentation 기능이 있었다.
- 백엔드에서 DTO를 List<> 타입으로 만들면 JSON에서 [] 로 변환되고, DTO를 Map<key,value> 타입으로 만들면 "key": value 형태로 변환된다.




### Spring boot에서 data type에 따른 json 형식의 차이

- 보내줘야 할 응답

```jsx
{
    "monthTotal": 23333221,
    "year": 2025,
    "month": 3,
    "daily": {
        "1": {
            "income": 0,
            "expense": 0
        },
        "31": {
            "income": 12341234,
            "expense": 234000
        }
    },
    "transactions": {
        "1": [
            {
                "_id": "67e241927be4585f3d4c7c47",
                "year": 2025,
                "month": 3,
                "day": 25,
                "isCombined": true,
                "title": "아이스아메리카노",
                "cost": -3000,
                "category": "cafe",
                "memo": "hello",
                "vendor": "바나프레소",
                "time": "2025-03-25T14:45:00Z",
                "paymentMethod": "네이버페이",
                "group": [
                    {
                        "_id": "67e241927be4585f3d4c7c47",
                        "year": 2025,
                        "month": 3,
                        "day": 24,
                        "isCombined": false,
                        "title": "정*희",
                        "cost": 3000,
                        "category": "transfer",
                        "bank": "카카오뱅크",
                        "time": "2025-03-25T14:45:00Z",
                        "paymentMethod": "카카오뱅크",
                        "group": []
                    },
                    {
                        "_id": "67e241927be4585f3d4c7c47",
                        "year": 2025,
                        "month": 3,
                        "day": 24,
                        "isCombined": false,
                        "title": "아이스아메리카노",
                        "cost": -6000,
                        "category": "cafe",
                        "vendor": "바나프레소",
                        "time": "2025-03-25T14:45:00Z",
                        "paymentMethod": "네이버페이",
                        "group": []
                    }
                ]
            },
            {
                "_id": "67e241927be4585f3d4c7c47",
                "year": 2025,
                "month": 3,
                "day": 25,
                "isCombined": false,
                "title": "아이스아메리카노",
                "cost": -3000,
                "category": "cafe",
                "memo": "hello",
                "vendor": "바나프레소",
                "time": "2025-03-25T14:45:00Z",
                "paymentMethod": "네이버페이",
                "group": []
            }
        ],
        "31": []
   
```

- List<> 로 쓰면 json에 배열로 넘어감
    - `private List<DayDto> day;`
    
    ```jsx
    public class DayDto {
        int day;
        int expense;
        int income;
    }
    ```
    
- Map<Integer, DayDto>로 쓰면 json에 “1” : { } 로 넘어감
    - `private Map<Integer, DayDto>`
    
    ```jsx
    public class DayDto {
        int expense;
        int income;
    }
    ```
