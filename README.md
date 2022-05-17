# Rxjava
================

교재: RxJava 프로그래밍 -유동환, 박정준-


## 1. 마블다이어그램
![마블다이어그램 사진](https://reactivex.io/assets/operators/legend.png)  
<br>
Rx 프로그래밍을 이해하기 위해 필요한 도구로 데이터의 흐름과 처리를 한눈에 이해할 수 있게 표현 하였다.  
* 상단 실선 : Observable의 시간 표시줄로, 왼쪽에서 오른쪽으로 시간이 흐름  
* 실선의 도형 : Observable에서 발행하는 데이터, 데이터가 발행될 떄 onNext 알림이 발생  
* 파이프(|) : Observable에서 데이터 발행을 완료하였다는 의미로 이후에는 데이터 발행이 불가능 하다, 완료시 onComplete 알림이 발생
* 아래로 내려오는 점선 화살표 : 상단 화살표는 함수의 입력, 하단 화살표는 함수의 출력을 의미한다.  
* 가운데 박스 : 함수를 의미한다. filp 함수는 입력된 데이터를 뒤집어서 출력함
* 하단 실선 : 함수의 결과가 출력된 시간 표시줄
* X : 함수 처리 중 발생한 에러를 의미, 에러 발생시 onError 알람이 발생

## 2. Observable
> Observable이란? 관찰자(Observer)가 관찰하는 대상, 현재는 관찰되지 않았지만 이론을 통해서 앞으로 관찰할 가능성

### Observable의 세가지 알림
* onNext : Observable이 데이터의 발행을 알림
* onComplete : 모든 데이터의 발행을 완료했음을 알림
* onError : Observable에서 어떤 이유로 에러가 발생했음을 알림, onError 이벤트가 발생하면 onNext, onComplete 이벤트가 발생하지 않음, Observable의 실행을 종료함


### Single 클래스
> 1개의 데이터만 발행하도록 한정하며 데이터가 하나가 발행과 동시에 종료(onSuccess) 됨

### Maybe 클래스
> Single과 비슷하게 0 혹은 1개의 데이터를 발행하고 종료함 Single 클래스에 onComplete 이벤트가 추가된 형태

### Hot Observable과 Cold Observable
* Cold Observable : Observable을 선언하고 팩토리 함수를 호출해도 옵서버가 subscribe() 함수를 호출하여 구독하지 않으면 데이터를 발행하지 않음 <br>
                    구독자가 구독하면 준비된 데이터를 처음부터 발행한다.
* Hot Observable : 구독자가 존재 여부와 관계없이 데이터를 발행하는 Observable <br>
                    구독한 시점부터 Observable에서 발행한 값을 받는다.

### Subject 클래스
> 차가운 Observable을 뜨거운 Observable로 바꿔주는 클래스로 Observable의 속성과 구독자 속성이 모두 있다.  

#### AsyncSubject
> Observable에서 발행한 마지막 데이터를 얻어올 수 있는 클래스

#### BehaviorSubject
> 구독자가 구독을 하면 가장 최근 값 혹은 기본 값을 넘겨주는 클래스

#### PublishSubject
> 구독자가 subscribe() 함수를 호출하면 값을 발행하는 가장 평범한 클래스

#### ReplaySubject 클래스
> 구독자가 새로 생기면 항상 데이터의 처음부터 끝까지 발행하는 것을 보장해주는 클래스  
> 모든 데이터 내용을 저장해두는 과정 중 메모리 누수가 발생할 가능성을 염두해야함

#### ConnectableObservable 클래스 
> connect()함수를 호출한 시점부터 subscribe() 함수를 호출한 구독자에게 데이터를 발행하는 클래스

## 3. 연산자

### 제네릭 함수형 인터페이스
|인터페이스 이름|포함 메서드|설명|
|:--:|:--:|:--:|
Predicate\<T>|boolean test(T t)|t값을 받아서 참이나 거짓을 반환
Consumer\<T>|void accept(T t)|t값을 받아서 처리, 반환값은 없음
Function\<T, R>|R apply(T t)|t값을 받아서 결과를 R타입으로 반환

### 생성 연산자
> just(), create(), fromXXX(), interval(), range(), timer(), intervalRange(), defer(), repeat()

### 변환 연산자
>  map(), flatMap(), concatMap(), switchMap(), groupBy(), scan(), buffer(), window()

### 필터 연산자
> filter(), take(), skip(), distinct()

### 결합 연산자
> zip(), conbineLatest(), merge(), concat()

### 조건 연산자
> amb(), takeUntil(), skipUntil(), all()

### 에러 처리 연산자
> onErrorReturn(), onErrorResumeNext(), retry(), retryUntil()

### 기타 연산자
> subscribe(), subscribeOn(), observeOn(), reduce(), count(), delay(), timeInterval()

## 4. 스케줄러
1. 스케줄러는 RxJava코드를 어느 스레드에서 실행할지 지정할 수 있다.
2. subscribe() 함수와 observeOn() 함수를 모두 지정하면 Observable에서 데이터 흐름이 발생하는 스레드와 처리된 결과를 구독자에게 발행하는 스레드를 분리할 수 있다.
3. subscribeOn() 함수만 호출하면 Observable의 모든 흐름이 동일한 스레드에서 실행된다.
4. 스케줄러를 별도로 지정하지 않으면 현재(main) 스레드에서 동작을 실행한다.

### 뉴 스레드 스케줄러 | newThread()
> 뉴 스레드 스케줄러는 요청을 받을 때마다 새로운 스레드를 생성

### 계산 스케줄러 | computation()
> CPU에 대응하는 계산용 스케줄러, 내부적으로 스레드 풀을 생성하며 스레드 개수는 기본적으로 프로세서 개수와 동일

### IO 스케줄러 | io()
> 네트워크상의 요청을 처리하거나 각종 입·출력 작업을 실행하기 위한 스케줄러, 필요할 때마다 스레드를 계속 생성

### 트램펄린 스케줄러 | trampoline()
> 현재 스레드에 무한한 크기의 대기 행렬을 생성하는 스케줄러

### 싱글 스레드 스케줄러 | single()
> RaJava 내부에서 단일 스레드를 별도로 생성하여 구독 작업을 처리, 여러 번 구독 요청이 와도 공통으로 사용
