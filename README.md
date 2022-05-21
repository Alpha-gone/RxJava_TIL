# Rxjava

교재: RxJava 프로그래밍 -유동환, 박정준-

## 1. 마블다이어그램
![마블다이어그램 이미지](https://reactivex.io/assets/operators/legend.png)  
<br>
*Rx 프로그래밍을 이해하기 위해 필요한 도구로 데이터의 흐름과 처리를 한눈에 이해할 수 있게 표현 하였다.* <br>

### 마블다이어그램의 요소
* 상단 실선 : Observable의 시간 표시줄로, 왼쪽에서 오른쪽으로 시간이 흐름  
* 실선의 도형 : Observable에서 발행하는 데이터, 데이터가 발행될 떄 onNext 알림이 발생  
* 파이프(|) : Observable에서 데이터 발행을 완료하였다는 의미로 이후에는 데이터 발행이 불가능 하다, 완료시 onComplete 알림이 발생
* 아래로 내려오는 점선 화살표 : 상단 화살표는 함수의 입력, 하단 화살표는 함수의 출력을 의미한다.  
* 가운데 박스 : 함수를 의미한다. filp 함수는 입력된 데이터를 뒤집어서 출력함
* 하단 실선 : 함수의 결과가 출력된 시간 표시줄
* X : 함수 처리 중 발생한 에러를 의미, 에러 발생시 onError 알람이 발생

<br><br>

## 2. Observable
*Observable이란? 관찰자(Observer)가 관찰하는 대상, 현재는 관찰되지 않았지만 이론을 통해서 앞으로 관찰할 가능성*

### Observable의 세가지 알림
* onNext : Observable이 데이터의 발행을 알림
* onComplete : 모든 데이터의 발행을 완료했음을 알림
* onError : Observable에서 어떤 이유로 에러가 발생했음을 알림, onError 이벤트가 발생하면 onNext, onComplete 이벤트가 발생하지 않음, Observable의 실행을 종료함


### Single 클래스
    1개의 데이터만 발행하도록 한정하며 데이터가 하나가 발행과 동시에 종료(onSuccess) 됨

### Maybe 클래스
    Single과 비슷하게 0 혹은 1개의 데이터를 발행하고 종료함 Single 클래스에 onComplete 이벤트가 추가된 형태

### Hot Observable과 Cold Observable
* Cold Observable : Observable을 선언하고 팩토리 함수를 호출해도 옵서버가 subscribe() 함수를 호출하여 구독하지 않으면 데이터를 발행하지 않음 <br>
                    구독자가 구독하면 준비된 데이터를 처음부터 발행한다.
* Hot Observable : 구독자가 존재 여부와 관계없이 데이터를 발행하는 Observable <br>
                    구독한 시점부터 Observable에서 발행한 값을 받는다.

### Subject 클래스
    차가운 Observable을 뜨거운 Observable로 바꿔주는 클래스로 Observable의 속성과 구독자 속성이 모두 있다.

#### AsyncSubject
    Observable에서 발행한 마지막 데이터를 얻어올 수 있는 클래스

#### BehaviorSubject
    구독자가 구독을 하면 가장 최근 값 혹은 기본 값을 넘겨주는 클래스

#### PublishSubject
    구독자가 subscribe() 함수를 호출하면 값을 발행하는 가장 평범한 클래스

#### ReplaySubject 클래스
    구독자가 새로 생기면 항상 데이터의 처음부터 끝까지 발행하는 것을 보장해주는 클래스
    모든 데이터 내용을 저장해두는 과정 중 메모리 누수가 발생할 가능성을 염두해야함

#### ConnectableObservable 클래스 
    connect()함수를 호출한 시점부터 subscribe() 함수를 호출한 구독자에게 데이터를 발행하는 클래스

<br><br>

## 3. 연산자

### 함수형 인터페이스
|인터페이스 이름|포함 메서드|설명|
|:--:|:--:|:--:|
Action| | 인자가 없는 람다 표현식을 넣어야 할때 사용
BooleanSupplier|boolean getAsBoolean()|인자 없이 Boolean값을 반환
Predicate\<T>|boolean test(T t)|t값을 받아서 참이나 거짓을 반환
BiPredicate\<T1, T2>|boolean test(T1 t1, T2 t2)| t1, t2값을 받아서 참이나 거짓을 반환
Consumer\<T>|void accept(T t)|t값을 받아서 처리, 반환값은 없음
Function\<T, R>|R apply(T t)|t값을 받아서 결과를 R타입으로 반환
BiFunction\<T, U, R>| R apply(T t, U u)| t와 u값을 받아서 결과를 R 타입으로 반환

---

### 생성 연산자
    just(), create(), fromXXX(), interval(), range(), timer(), intervalRange(), defer(), repeat()

### 변환 연산자
    map(), flatMap(), concatMap(), switchMap(), groupBy(), scan(), buffer(), window()

### 필터 연산자
    filter(), take(), skip(), distinct()

### 결합 연산자
    zip(), conbineLatest(), merge(), concat()

### 조건 연산자
    amb(), takeUntil(), skipUntil(), all()

### 에러 처리 연산자
    onErrorReturn(), onErrorResumeNext(), retry(), retryUntil()

### 기타 연산자
    subscribe(), subscribeOn(), observeOn(), reduce(), count(), delay(), timeInterval()

<br><br>

## 4-1. 스케줄러의 종류
* 스케줄러는 RxJava코드를 어느 스레드에서 실행할지 지정할 수 있다.
* subscribe() 함수와 observeOn() 함수를 모두 지정하면 Observable에서 데이터 흐름이 발생하는 스레드와 처리된 결과를 구독자에게 발행하는 스레드를 분리할 수 있다.
* subscribeOn() 함수만 호출하면 Observable의 모든 흐름이 동일한 스레드에서 실행된다.
* 스케줄러를 별도로 지정하지 않으면 현재(main) 스레드에서 동작을 실행한다.

### 뉴 스레드 스케줄러 | newThread()
    뉴 스레드 스케줄러는 요청을 받을 때마다 새로운 스레드를 생성

### 계산 스케줄러 | computation()
    CPU에 대응하는 계산용 스케줄러, 내부적으로 스레드 풀을 생성하며 스레드 개수는 기본적으로 프로세서 개수와 동일

### IO 스케줄러 | io()
    네트워크상의 요청을 처리하거나 각종 입·출력 작업을 실행하기 위한 스케줄러, 필요할 때마다 스레드를 계속 생성

### 트램펄린 스케줄러 | trampoline()
    현재 스레드에 무한한 크기의 대기 행렬을 생성하는 스케줄러

### 싱글 스레드 스케줄러 | single()
    RaJava 내부에서 단일 스레드를 별도로 생성하여 구독 작업을 처리, 여러 번 구독 요청이 와도 공통으로 사용

<br>

## 4-2. subscribeOn()과 observeOn()

<p align="center"><img src="https://reactivex.io/documentation/operators/images/schedulers.png" width = "500"></p>

### subscribeOn() 함수
    Observable에서 구독자가 subscribe() 함수를 호출했을 떄 데이터 흐름을 발행하는 스레드를 지정, 처음 지정한 스레드를 고정시킴

### observeOn() 함수
    처리된 결과를 구독자에게 전달하는 스레드를 지정, 계속 호출하여 다른 스레드로 변경 가능

<br><br>

## 5. 디버깅
*함수형 프로그래밍은 함수의 부수효과를 없도록 해야하는 것이 원칙이나, doOnXXX()계열 함수는 부수효과를 일으켜서 코드에 문제가 있는지 확인 할 수 있게 해줌*
### doOnXXX() 함수
* doOnNext() : onNext 이벤트 발생시 실행
* doOnError() : onError 이벤트 발생시 실행
* doOnComplete() : onComplete 이벤트 발생시 실행
* doOnEach() : onNext, onError, onComplete 이벤트를 한 번에 처리 할 수 있음

### doOnSubscribe(), doOnDispose(), 기타 함수
* doOnSubscribe() : Observable을 구독했을 때 작업을 수행, 인자로 Disposable 객체가 제공됨
* doOnDispose() : Observable의 구독을 해제했을때 호출, Action 객체가 인자이며 스레드 다수에서 Observable을 참조할 수 있기 때문에 '스레드 안전'하게 동작해야 됨
* doOnLifeCycle() : doOnSubscribe()와 doOnDispose() 함수를 한꺼번에 호출하는 함수
* doOnTerminate() : onComplete()나 onError 이벤트 발생 직전에 호출
* doFinally() : onError, onComplete, onDispose 이벤트 발생시에 호출

<br><br>

## 6. 예외 처리
*RxJava는 try-catch문으로 예외처리가 불가능함 따라서 에러도 어떠한 데이터로 보는것이 적절함*

### 함수를 활용하는 예외 처리의 장점
1. 예외 발생이 예상되는 부분을 선언적으로 처리 할 수 있음
2. Observable을 생성하는 측과 구독하는 측이 서로다를 수 있음, Observable을 생성하는 측에서 발생할 수 있는 예외 처리를 미리 해두면 구독자는 선언된 예외 상황을 보고 처리할 수 있음

### onErrorReturn(), onErrorReturnItem() 함수
    에러가 발생했을 때 원하는 데이터로 대체하는 함수, 에러가 발생하는 경우 인자로 넘겼던 기본값을 대신 발행하고 onComplete 이벤트가 발생함 <br>(onError 이벤트는 발생하지 않음)
* onErrorReturn() : Throwable 객체를 인자로 전달, 예외의 종류를 확인 할 수 있음
* onErrorReturnItem() : 에러 발생시 대체할 기본값만 인자로 받음, 코드가 간결한 대신 예외의 종류를 확인 할 수 없음

### onErrorResumeNext() 함수
    에러 발생시 원하는 Observable로 대체함, 에러 발생시 추가 작업을 해야 할 때 유용

### retry() 함수
    onError 이벤트 발생 시 subscribe()를 다시 함, 재시도 횟수와 조건을 지정할 수 있음

### retryUntil() 함수
    재시도를 중단할 조건이 발생할 때까지 계속 재시도

### retryWhen() 함수
    재시도 조건을 동적으로 설정해야 하는 복잡한 로직을 구현 할 때 활용하는 함수

<br><br>

## 7.흐름제어
*Observable이 데이터를 발행하는 속도와 옵서버가 데이터를 받아서 처리하는 속도의 차이가 발생할 때 사용하는 함수*

### sample() 함수
    특정한 시간 동안 가장 최근에 발행된 데이터만 걸러주는 함수, 해당 시간동안 많은 데이터가 들어와도 해당 구간의 마지막 데이터만 발행하고 나머지는 무시함

### buffer() 함수
    일정 시간 동안 데이터를 모아두었다가 한꺼번에 발행하는 함수, 넘치는 데이터 흐름을 제어할 필요가 있을 때 활용

### throttleFirst(), throttleLast() 함수
* throttleFirst() : 주어진 조건에서 가장 먼저 입력된 값을 발행
* throttleLast() : 주어진 조건에서 가장 마지막에 입력된 값을 발행

### window() 함수
    인자로 지정한 수 만큼의 데이터가 모이면 새로운 Observable로 만들어 줌

### debounce() 함수
    지정한 시간 간격 안에 이벤트가 발생하지 않으면 마지막 입력된 값을 발행함, UI 기반의 프로그래밍에서 유용하게 활용

<br><br>

## 8. Flowable 클래스
*배압 이슈를 위해 별도로 분리한 클래스로 활용은 Observable과 동일함, toObservable()과 toFlowable() 함수를 사용하여 상호 변환이 가능함*

### onBackpressureBuffer()
    배압 이슈 발생시 별도의 버퍼에 저장함, Flowable 클래스는 기본적으로 128개의 버퍼가 있음

#### BackpressureOverflowStrategy
* ERROR : MissingBackpressureException 예외를 던지고 데이터 흐름을 중단함
* DROP_LATEST : 버퍼에 쌓여 있는 최근 값을 제거
* DROP_OLDEST : 버퍼에 쌓여 있는 가장 오래된 값을 제거함

### onBackpressureDrop()
    배압 이슈 발생시 해당 데이터를 무시함

### onBackpressureLatest()
    처리할 수 없어서 쌓이는 데이터를 무시하고 최신 데이터만 유지함