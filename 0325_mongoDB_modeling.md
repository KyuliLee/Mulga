## MongoDB 모델링 고려 사항

1. **데이터 액세스 패턴**  
   - 읽기 및 쓰기 성능을 고려하여 데이터를 모델링해야 함.
   - 자주 함께 조회되는 데이터는 **Embedding**하여 성능을 최적화.
   - 독립적으로 갱신되는 데이터는 **Referencing**하여 중복을 줄임.

2. **데이터 일관성**  
   - 중복된 데이터를 허용할 것인지, 참조를 사용할 것인지 결정해야 함.
   - 중복된 데이터를 허용하면 성능은 좋아지지만, 일관성을 유지하기 어려울 수 있음.

3. **문서 크기 제한 (16MB)**  
   - 하나의 문서 크기가 16MB를 초과하면 **Embedding**보다는 **Referencing**을 사용해야 함.

4. **데이터 정규화 vs 비정규화**  
   - 정규화(Referencing)는 데이터 중복을 줄이고, 갱신이 쉬움.
   - 비정규화(Embedding)는 성능 향상을 위해 하나의 문서에 필요한 데이터를 포함.

5. **쿼리 성능과 인덱싱**  
   - 조회 시 어떤 필드에 인덱스를 걸어야 하는지 고려해야 함.
   - `lookup` 같은 조인이 필요한 경우 성능 영향을 고려해야 함.

---

## Embedding vs Referencing 비교

| 방법 | 장점 | 단점 |
|------|------|------|
| **Embedding (내장)** | - 빠른 읽기 성능 (한 번의 조회로 데이터 가져옴) <br> - 조인이 필요 없음 <br> - 데이터가 한 문서에 포함되어 일관성이 높음 | - 데이터 중복 발생 가능 <br> - 문서 크기(16MB) 제한 존재 <br> - 일부 데이터만 업데이트가 어려울 수 있음 |
| **Referencing (참조)** | - 데이터 중복 최소화 <br> - 독립적인 업데이트 가능 <br> - 문서 크기 문제 없음 | - 추가 조회가 필요 (`$lookup` 사용) <br> - 성능 저하 가능 <br> - 네트워크 요청이 증가할 수 있음 |

---

## Data Modeling 결과
### `user` collection
```json
{
  "_id": "asdfasdasdfasdfsdf",
  "budget": 10000,
  "name": "물가",
  "email": "mulga@ssafy.com",
  "isWithdrawn": false,
  "receivesNotification": true
}
```

### `transaction` collection
```json
{
  "_id": {
    "$oid": "67e241927be4585f3d4c7c47"
  },
  "user_id": {
    "&oid": "asdfasdasdfasdfsdf"
  },
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
      "year": 2025,
      "month": 3,
      "day": 24,
      "isCombined": false,
      "title": "정*희",
      "cost": 3000,
      "category": "transfer",
      "bank": "카카오뱅크",
      "time": "2025-03-25T14:45:00Z",
      "paymentMethod": "카카오뱅크"
    },
    {
      "year": 2025,
      "month": 3,
      "day": 24,
      "isCombined": false,
      "title": "아이스아메리카노",
      "cost": -6000,
      "category": "cafe",
      "vendor": "바나프레소",
      "time": "2025-03-25T14:45:00Z",
      "paymentMethod": "네이버페이"
    }
  ]
}
```

### `analysis` collection
```json
{
  "_id": "asdfasdf_2025_3",
  "year": 2025,
  "month": 3,
  "month_total": 1000200,
  "category": {
    "education": 0,
    "etc": 0,
    "food": 0,
    "health": 0,
    "home": 0,
    "leisure": 0,
    "liquor": 0,
    "living": 0,
    "pet": 0,
    "shopping": 0,
    "subscription": 0,
    "traffic": 0,
    "transfer": 0,
    "travel": 0,
    "beauty": 123,
    "cafe": 1232342
  },
  "paymentMethod": {
    "네이버페이": 1231232,
    "신한": 123412
  },
  "daily": {
    "1": {
      "income": 0,
      "expense": 0
    },
    "2": {
      "income": 0,
      "expense": 0
    },
    "3": {
      "income": 0,
      "expense": 0
    },
    "4": {
      "income": 0,
      "expense": 0
    },
    "5": {
      "income": 0,
      "expense": 0
    },
    "6": {
      "income": 0,
      "expense": 0
    },
    "7": {
      "income": 0,
      "expense": 0
    },
    "8": {
      "income": 0,
      "expense": 0
    },
    "9": {
      "income": 0,
      "expense": 0
    },
    "10": {
      "income": 0,
      "expense": 0
    },
    "11": {
      "income": 0,
      "expense": 0
    },
    "12": {
      "income": 0,
      "expense": 0
    },
    "13": {
      "income": 0,
      "expense": 0
    },
    "14": {
      "income": 0,
      "expense": 0
    },
    "15": {
      "income": 0,
      "expense": 0
    },
    "16": {
      "income": 0,
      "expense": 0
    },
    "17": {
      "income": 0,
      "expense": 0
    },
    "18": {
      "income": 0,
      "expense": 0
    },
    "19": {
      "income": 0,
      "expense": 0
    },
    "20": {
      "income": 0,
      "expense": 0
    },
    "21": {
      "income": 0,
      "expense": 0
    },
    "22": {
      "income": 0,
      "expense": 0
    },
    "23": {
      "income": 0,
      "expense": 0
    },
    "24": {
      "income": 0,
      "expense": 0
    },
    "25": {
      "income": 0,
      "expense": 0
    },
    "26": {
      "income": 0,
      "expense": 0
    },
    "27": {
      "income": 0,
      "expense": 0
    },
    "28": {
      "income": 0,
      "expense": 0
    },
    "29": {
      "income": 0,
      "expense": 0
    },
    "30": {
      "income": 0,
      "expense": 0
    },
    "31": {
      "income": 0,
      "expense": 0
    }
  }
}
```
