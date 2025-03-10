## 1. **결합도 (Coupling)**

결합도는 모듈 간의 의존성을 나타내며, 낮을수록(Loose Coupling) 유지보수성과 확장성이 좋다. 결합도가 높으면 모듈 간의 변경이 서로에게 큰 영향을 준다.

### **결합도의 종류 (낮은 결합도 → 높은 결합도 순)**

1. **자료 결합(Data Coupling)**
   - 모듈 간에 필요한 데이터만 전달하는 방식. 가장 낮은 결합도로 바람직함.
   - **예시**:
     ```java
     void calculate(int a, int b) { ... }  // 필요한 데이터만 전달
     ```

2. **스탬프 결합(Stamp Coupling)**
   - 구조체나 객체 전체를 전달하지만, 실제로 필요한 데이터는 일부만 사용됨.
   - **예시**:
     ```java
     class Person {
         String name;
         int age;
     }
     void printPersonInfo(Person p) {
         System.out.println(p.name);
     } // age는 사용 안 함
     ```

3. **제어 결합(Control Coupling)**
   - 모듈이 특정 기능을 수행하도록 제어 신호(Flag, Boolean 값)를 전달하는 방식.
   - **예시**:
     ```java
     void processData(boolean isLoggingEnabled) {
         if (isLoggingEnabled) {
             System.out.println("Logging enabled");
         }
     }
     ```

4. **외부 결합(External Coupling)**
   - 외부 환경(파일, DB, 외부 라이브러리)에 의존하는 경우.
   - **예시**:
     ```java
     void readConfig() {
         File file = new File("config.txt");
     }
     ```

5. **공통 결합(Common Coupling)**
   - 여러 모듈이 전역 변수(Global Variable)를 공유하여 데이터를 사용.
   - **예시**:
     ```java
     class Shared {
         static int globalValue;
     }
     void updateValue() {
         Shared.globalValue = 10;
     }
     ```

6. **내용 결합(Content Coupling)** (가장 나쁜 결합도)
   - 한 모듈이 다른 모듈 내부 구현을 직접 참조하거나 수정.
   - **예시**:
     ```java
     class A {
         int data = 10;
     }
     class B {
         void modifyData(A obj) {
             obj.data = 20; // 직접 접근하여 변경
         }
     }
     ```

---

## 2. **응집도 (Cohesion)**

응집도는 모듈 내부 요소 간의 연관성을 나타내며, 높을수록 유지보수성이 좋다. 높은 응집도가 바람직함.

### **응집도의 종류 (낮은 응집도 → 높은 응집도 순)**

1. **우연적 응집(Coincidental Cohesion)**
   - 관련 없는 기능들이 한 모듈에 모여 있음.
   - **예시**:
     ```java
     class Utils {
         void printMessage() { System.out.println("Hello"); }
         void calculateSum() { int sum = 5 + 10; }
         void getUserInput() { Scanner sc = new Scanner(System.in); }
     }
     ```

2. **논리적 응집(Logical Cohesion)**
   - 비슷한 유형의 작업을 하나의 모듈에서 처리하지만, 서로 직접적인 관련은 없음.
   - **예시**:
     ```java
     void processData(int type) {
         if (type == 1) {
             readFile();
         } else if (type == 2) {
             readDatabase();
         }
     }
     ```

3. **시간적 응집(Temporal Cohesion)**
   - 특정 시점에 실행되는 기능들을 한 모듈에 모아둠.
   - **예시**:
     ```java
     void initialize() {
         loadConfig();
         connectToDB();
         startLogger();
     }
     ```

4. **절차적 응집(Procedural Cohesion)**
   - 특정 절차(순서)를 따르는 기능들이 모여 있지만, 하나의 기능을 중심으로 하지는 않음.
   - **예시**:
     ```java
     void processRequest() {
         validateUser();
         logRequest();
         sendResponse();
     }
     ```

5. **통신적 응집(Communicational Cohesion)**
   - 같은 데이터를 사용하거나 같은 입출력 작업을 수행하는 기능들을 묶음.
   - **예시**:
     ```java
     void processStudentData(Student s) {
         calculateGrade(s);
         printReport(s);
     }
     ```

6. **순차적 응집(Sequential Cohesion)**
   - 한 기능의 출력이 다음 기능의 입력으로 사용됨.
   - **예시**:
     ```java
     int processData(int data) {
         int processed = cleanData(data);
         return analyzeData(processed);
     }
     ```

7. **기능적 응집(Functional Cohesion)** (가장 바람직함)
   - 모듈이 하나의 명확한 기능을 수행하도록 구성됨.
   - **예시**:
     ```java
     int calculateSum(int a, int b) {
         return a + b;
     }
     ```

---

## **정리**

- **결합도는 낮을수록 좋고(Loose Coupling), 응집도는 높을수록 좋다(High Cohesion).**
- **좋은 설계 원칙:**
  - **낮은 결합도:** 데이터 결합(Data Coupling)
  - **높은 응집도:** 기능적 응집(Functional Cohesion)

