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

### 팩토리 함수
> just(), create(), fromArray(), fromIterable(), fromCallable(), inteval(), range()등이 있음

### Single 클래스
> 1개의 데이터만 발행하도록 한정하며 데이터가 하나가 발행과 동시에 종료(onSuccess) 됨

### Maybe 클래스
> Single과 비슷하게 0 혹은 1개의 데이터를 발행하고 종료함 Single 클래스에 onComplete 이벤트가 추가된 형태

### Hot Observable과 Cold Observable
* Cold Observable : Observable을 선언하고 팩토리 함수를 호출해도 옵서버가 subscribe() 함수를 호출하여 구독하지 않으면 데이터를 발행하지 않음 <br>
                    구독자가 구독하면 준비된 데이터를 처음부터 발행한다.
* Hot Observable : 구독자가 존재 여부와 관계없이 데이터를 발행하는 Observable <br>
                    구독한 시점부터 Observable에서 발행한 값을 받는다.
