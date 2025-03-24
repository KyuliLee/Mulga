# MongoDB와 락 종류

## 1. MongoDB란?

MongoDB는 NoSQL 데이터베이스 관리 시스템(DBMS) 중 하나로, JSON과 유사한 BSON(Binary JSON) 형식을 사용하여 데이터를 저장합니다. 관계형 데이터베이스(RDBMS)와 달리 스키마 없이 동적으로 데이터를 저장할 수 있으며, 확장성이 뛰어나 빅데이터 및 분산 환경에서 많이 사용됩니다.

### 1.1 MongoDB의 특징
- **문서 지향(Document-Oriented)**: 데이터가 JSON 형식의 문서(Document)로 저장됨
- **스키마리스(Schema-less)**: 고정된 스키마 없이 유연한 데이터 모델링 가능
- **수평적 확장(Sharding 지원)**: 데이터 분산 저장을 통한 확장성 확보
- **고성능**: 인덱싱 및 메모리 캐싱을 활용한 빠른 데이터 검색
- **트랜잭션 지원**: ACID 트랜잭션을 지원하여 데이터 일관성 보장

## 2. MongoDB의 락(Lock) 종류

MongoDB는 데이터 일관성을 유지하기 위해 여러 종류의 락(Lock) 메커니즘을 제공합니다. MongoDB는 기본적으로 락을 최소화하여 동시성을 높이도록 설계되어 있지만, 특정 작업에서는 락을 사용하여 데이터 무결성을 유지해야 합니다.

### 2.1 글로벌 락 (Global Lock)
- MongoDB 인스턴스 전체에 적용되는 락
- 특정 작업(예: 데이터베이스 덤프)이 수행될 때 전체 데이터베이스에 대한 접근을 제한
- 동시성을 저하시킬 수 있어 자주 사용되지는 않음

### 2.2 데이터베이스 락 (Database Lock)
- 특정 데이터베이스 수준에서 적용되는 락
- 같은 데이터베이스 내의 컬렉션끼리는 동시 작업 가능하지만, 해당 데이터베이스의 락이 걸리면 다른 작업은 대기해야 함

### 2.3 컬렉션 락 (Collection Lock)
- 특정 컬렉션에 대한 락으로, 해당 컬렉션에 대한 동시 쓰기 작업을 방지
- 컬렉션 내의 다른 문서에 대한 읽기/쓰기 작업은 가능

### 2.4 문서 락 (Document Lock)
- 특정 문서(Document) 수준에서 적용되는 락
- MongoDB의 동시성 제어를 위해 가장 많이 사용되는 락
- 특정 문서에 대한 수정 작업이 진행될 때 해당 문서만 락이 걸리므로, 성능 저하를 최소화함

### 2.5 레플리카 셋(Replica Set)과 락
- **프라이머리 노드(Primary Node)**: 문서 수준 락을 사용하여 동시성을 최대화
- **세컨더리 노드(Secondary Node)**: 읽기 작업 시 락을 최소화하여 성능 향상

### 2.6 트랜잭션과 락
- MongoDB 4.0 이상에서는 다중 문서 트랜잭션을 지원하며, 트랜잭션 중에는 문서 단위 락을 사용하여 데이터의 일관성을 보장
- 트랜잭션이 완료되기 전까지 변경사항이 적용되지 않으며, 롤백 기능도 제공됨

## 3. 다중 문서 트랜잭션 (Multi-Document Transactions)

MongoDB는 기본적으로 단일 문서의 원자성을 보장하지만, 4.0 버전부터는 다중 문서 트랜잭션(Multi-Document Transactions)을 지원하여 관계형 데이터베이스처럼 ACID(원자성, 일관성, 격리성, 지속성)를 충족하는 복합 연산을 수행할 수 있습니다.

### 3.1 다중 문서 트랜잭션의 특징
- **다수의 문서를 하나의 트랜잭션으로 묶어 처리 가능**
- **ACID 속성 보장**: 트랜잭션이 완료되기 전까지 변경사항이 반영되지 않으며, 실패 시 롤백 가능
- **레플리카 셋 및 샤딩 환경에서도 지원**
- **세션(Session) 기반 실행**: `startTransaction()`을 사용하여 트랜잭션을 시작하고, `commitTransaction()` 또는 `abortTransaction()`을 통해 완료 또는 취소

### 3.2 다중 문서 트랜잭션 예제
```javascript
const session = db.getMongo().startSession();
session.startTransaction();

try {
    const ordersCollection = session.getDatabase("shop").orders;
    const inventoryCollection = session.getDatabase("shop").inventory;

    ordersCollection.insertOne({ orderId: 1, product: "Laptop", quantity: 1 }, { session });
    inventoryCollection.updateOne({ product: "Laptop" }, { $inc: { stock: -1 } }, { session });

    session.commitTransaction();
    print("트랜잭션 성공");
} catch (error) {
    session.abortTransaction();
    print("트랜잭션 실패: ", error);
} finally {
    session.endSession();
}
```

## 4. 결론
MongoDB는 높은 동시성을 유지하면서도 데이터 무결성을 보장하기 위해 다양한 수준의 락을 제공합니다. 글로벌 락은 성능 저하의 원인이 될 수 있어 최소한으로 사용되며, 일반적으로 문서 락과 컬렉션 락이 활용됩니다. 또한, 최신 MongoDB에서는 트랜잭션을 통해 다중 문서 작업 시에도 일관성을 유지할 수 있도록 개선되었습니다.
