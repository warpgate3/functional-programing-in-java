자바 개발자로서 함수형 프로그래밍을 익숙하지 않고 잘 모른다. 하지만 내가 이해하고 있는 수준내에서 함수형 프로그래밍을 자바 코드를 예제로 설명해보려한다.

## 함수형 프로그래밍

자바 코드를 예제로 들기 전에 먼저 함수형 프로그래밍에 대해서 알아보자. 함수형 프로그래밍이라고 특별할 건 없고 우리가 익숙하게 알고 있는 객체지향프로그래밍이나 절차지향적 프로그래밍과 같은 프로그래밍 패러다임중 하나이다. 객체지향프로그래밍이 객체간 메세지와 협력 관계의 정의로 이루어졌다면 함수형 프로그래밍은 단순히 함수들의 조합으로 이루어진다. 그 함수들은 외부와의 관계는 없고 단지 함수 자신만으로 존재한다. 하지만 함수들의 조합이라는 말은 쉽지만 실제로 함수형 코딩을 잘 하는데는 꽤 높은 러닝 커브를 요구한다. 개인적인 생각에는 객체지향이나 절차지향언어보다 높은 수준의 추상화를 요구하기 때문에 코딩을 하기 앞서 함수형 개발을 위한 사고 능력부터 키워야 하기 때문이 아닐까 생각한다. 이 이야기는 뒤에 나올 예제 코드를 이야기 하면 자연스럽게 이해할 수 있을 것이다. 우선 함수형 코딩 구성하는 몇 가지 요소들에 대해서 알아보자

## 함수형 코딩 핵심 Keyword

객체지향 언어의 특징을 이야기 할 때 [객체지향의 4대(다형성, 추상화, 캡슐화, 상속성) 요소](https://info.keylimeinteractive.com/the-four-pillars-of-object-oriented-programming)나 [SOLID](https://en.wikipedia.org/wiki/SOLID) 와 같은 5대 원칙을 말한다. 마찬가지로 함수형 프로그래밍 역시 여러 특징들이 있다. 그 중 몇 가지 중요한 요소들을 정리해보겠다.

### Pure Function

순수 함수는 동일한 입력값에 대해서 항상 같은 값을 반환한다. 또한 전역 변수를 사용하거나 변경해서 예상하지 못한 Side effect를 발생하지 않는다. 아래는 자바 코드 예제이다.

```
private String name = "무명소졸"; //Not Pure 
public String greeting() { 
	return "Hello " + name; 
} //Pure function 

public static String greeting(String name) { 
	return "Hello" + name; 
}
```

순수 함수는 외부에 영향을 주거나 영향을 받지 않고 입력값이 같으면 늘 반환값도 일치함을 보장한다. 이런 예측 가능함이 프로그래머코드 작성할 때 상당한 안도감을 준다. (마치 private 메서드를 삭제하거나 수정할 때와 같이)

### No Iterate

for, while문과 같은 반복문을 사용하지 않는다. 반복문은 안에는 가변적인 값들과 처리에 대한 코드가 섞여 있다. 함수형에서는 반복문 대신 map, filter 같은 함수를 매개변수로 받는 메서드를 이용한다. 아래는 자바코드 예제이다.

```
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

//for loop
for (int idx = 0; idx < numbers.size(); idx++) {
    System.out.println(numbers.get(idx));
}

//functional
numbers.forEach((num) -> {
    System.out.println(num);
});
```

for loop 문에서는 idx 와 numbers 의 사이즈와 비교하면서 idx 값을 계속해서 증가시키면서 숫자값을 가져오고 표준 출력을한다. forEach문은 심플하다. 숫자를 입력받고 해당 값을 출력하는 함수를 인수로 전달하면 된다. 

### High Order Function

고차 함수는 말이 어려워 보이지만 간단한 내용이다. 함수를 인자로 받거나 함수를 반환 값으로 이용할 수 있는 것을 말한다. 함수로 반환할 경우 정의하기 어려운 클로저(closure) 개념도 나온다. 아래는 고차 함수와 클로저를 포함한 자바 예제 코드이다.

```
Function<String, Function<String, String>> greeting = (greetingText) -> {
    return (name) -> {
        return greetingText + " " + name;
    };
};

Function<String, String> hello = greeting.apply("Hello");
Function<String, String> hi = greeting.apply("HI");

System.out.println(hello.apply("무명소졸"));
System.out.println(hi.apply("무명소졸"));   

>>>>
Hello 무명소졸
HI 무명소졸
```

 greeting 함수는 인사말을 입력받고 함수를 반환한다. 반환하는 함수는 이름을 인자로 받으며 상위 함수의 입력받은 인사말을 출력하는 함수이다.

여기서 내부 함수에서 외부 함수의 값(greetingType)에 접근하고 scope가 종료해도 계속 접근할 수 있는걸 클로저라고 한다. 예제 에서 서로 다른 인사말 (Hello, Hi) 를 각 각 유지하고 있다. 자세히 언급하지는 않겠지만 클로저 또한 함수형 언어를 구성하는 중요한 컨셉중에 하나이다. 

Immutability

불변성은 변할 수 없는 값을 의미한다. 자바에서는 final 변수를 선언해서 만드는데 그렇다고 final 이 모든 변수에 불변성을 보장하는 것은 아니다. 아래는 자바 예제 코드이다.

```
final String name = "무명소졸";
name = "무명소졸2"; //compile error
System.out.println(name);

final List<String> alphabets = Arrays.asList("a", "b", "c");

//but change value
alphabets.add("d");
```

값 타입의 변수들은 final 로 선언하면 값 변경시 compile error 오류가 발생하지만 alphabets와 같은 참조 변수들은 final 선언으로 재할당을 막을수 있지만 원소에 값을 추가할 수 있다. 이렇다면 alphabets 은 불변성이 있다 말할 수 없다.  자바와 같은 경우 Collection 객체들은 아래와 같은 방법으로 불변성객체로 만들 수 있다.

```
final List<String> alphabets = Collections.unmodifiableList(Arrays.asList("a", "b", "c"));
alphabets.add("d");
```

Runtime 시에 아래와 같은 오류를 발생시킨다.

일반적인 변수 선언외에도 함수로 전달되는 객체들 또한 원본 객체의 값을 변경하면 안 되고 복사한 값을 변경해서 반환한다. 물론 오버헤드는 피할 수 없다. 자바스크립트와 같은 다른 함수형 언어에서는 오버헤드를 줄이기 위해 [영속자료구조(Persistent Data Structures)](https://en.wikipedia.org/wiki/Persistent_data_structure)와 같은 방법을 제공한다. 이런 데이터 불변성이 병렬 처리에서의 데이터 상태에 대한 안전성을 보장한다. 아래는 리스트에 새로운 요소를 추가하면서 불변성을 보장하는 자바 코드의 예제이다.

```
public static void main(String[] args) {
    List<String> alphabets = Arrays.asList("a", "b", "c");
    List<String> newAlphabets = add(alphabets, "d");

    System.out.println(alphabets); //[a, b, c]
    System.out.println(newAlphabets);//[a, b, c, d]
}

public static List<String> add(List<String> strings, String text) {
    List<String> dest = new ArrayList<>();
    for (String string : strings) { //overhead 리스트를 순회하면서 원소들을 복사한다. (DeepCopy) 
        dest.add(string);
    }

    dest.add(text);
    return dest;
}
```

Why Functional Programing ?

함수형 언어는 객체지향과 같은 언어보다 좀 더 오랜 역사를 가진다. 그런데 절차지향언어나 객체지향언어보다 많이 쓰이지 못했다. 아마도 절차지향이나 객체지향 언어가 좀 더 사람의 사고의 흐름과 비슷해 이해하기 쉽고 굳이 함수형 프로그래밍에 필요성도 못 느꼈을 수도 있다. 그럼에도 불구하고 최근 몇 년 사이에 함수형 코딩이 주목 받는 이유는 함수형이 주는 가독성과 함수를 이용한 코드 재사용성도 있지만 무엇보다도 빅데이터 시대가 도래하고 그런 페타급 이상의 빅데이터를 처리하기 위해서 단일 프로세스로 처리하는 것 보다는 멀티코어를 이용한 병렬 처리하는 것이 비용이나 속도면에서 더 유리하기 때문이 아닐까 하는 생각이 든다. 함수형 프로그래밍은 전역 상태를 허용하지 않는다. 그래서 병렬 프로세스나 쓰레드에 안전하다. 또한 추상화레벨이 높다. 추상화는 복잡한 것을 숨기고 필요한 정보만 나타내는 것이다. 프로그래밍에서 복잡한 것은 상태와 변화하는 값들이다. 이런 부분들이 사라지면 로직의 골격만 남기때문에 전체적인 흐름을 파아하는데 더 용이할 수 있다. 주저리 주저리 글을 썼는데 사실 잘 와 닿지 않을 수 있다. 이제 명령형으로 된 간단한 자바 코드를 함수형 형태로 변경해보면서 코드로 직접 느껴보겠다.

"1부터 10까지 짝수인 값을 출력하시오"

### 명령형 프로그래밍(Imperative Programing)

정말 간단한 로직이다. 이제 막 시작한 개발자들이라도 이 정도 로직은 쉽게 작성할 수 있다. 아마도 아래 정도의 코드가 될 것이다.

```
for (int i = 1; i < 11; i ++) { 
	if (i % 2 == 0) { 
    	System.out.println(i); 
    } 
 }
```

여기서 요구사항이 나와 홀수를 출력하거나 1부터 10이 아니고 100까지 출력하게 변경할려면 로직 자체를 변경해야 한다.

```
for (int i = 1; i < 100; i ++) { 
	if (i % 2 != 0) {//홀수 검사 
    	System.out.println(i); 
     } 
} 
/* 
만약 1부터 100 은 홀수 200부터 300은 짝수 출력처럼 
2개의 흐름이 필요하면 아래와 같이 중복코드가 들어갈 것이다.
(물론 메서드로 모듈화 할수는 있겠지만..) */ 

for (int i = 200; i < 300; i ++) { 
	if (i % 2 == 0) {//짝수 검사 
    System.out.println(i); 
    } 
}
```

이제 이 코드를 함수형 프로그래밍 형태로 변경해보겠다. 함수형 프로그래밍에서는 어떻게(HOW) 보다 무엇을(WHAT) 에 더 집중한다. WHAT의 정의하는 과정이 추상화 과정으로 생각할 수도 있다. 위 로직에서 무엇은 아래 3가지 정도로 정의해보겠다.

-   1..10 까지 숫자 iterate
-   짝수 여부 검사
-   표준출력(System.out.println)

#### 1..10까지 숫자 iterate

시작값과 종료값 2개의 입력값을 받는 함수이다.(자바 함수형 인터페이스 사용에 대한 자세한 내용은 생략하겠다.)

```
public static void main(String[] args) { 
	iterator.accept(0, 10); 
} 

static BiConsumer<Integer, Integer> iterator = (start, end) -> { 
	for (int n = start; n <= end; n++) { 
    	if (n % 2 == 0) { 
        	System.out.println(n); 
         } 
    } 
};
```

#### 짝수 여부 검사

위에 반복문을 함수로 작성한 것은 메서드로 분리한 것과 별반 다를게 없다. 이제 여기서 출력 여부를 검사하는 validation 함수를 인자로 추가해 보겠다. 그 전에 인터페이스를 먼저 정의 해야된다. 위에서 사용한 BiConsumer 인터페이스는 자바에 미리 만들어놓은 람다로 사용하기 위한 인터페이스이다. java.util.function 패키지 안에는 자주 사용할만한 인터페이스들이 정의되어 있다.


하지만 여기서 만들 인터페이스는 3개의 인자를 받기 때문에 직접 정의해야된다. 아래는 3개의 인자를 받는 람다 인터페이스이다.

```
interface ThreeConsumer<T1, T2, T3> { 
	void accept(T1 t1, T1 t2, T3 t3); 
}
```

위 인터페이스를 이용해 함수를 정의해보겠다. 3번째 인자는 짝수 여부를 체크하는 함수를 인자로 받는 java.util.functions.IntPredicate 인터페이스를 이용하면된다.

```
static ThreeConsumer<Integer, Integer, IntPredicate> fori = (startNum, endNum, validator) -> { 
	for (int i = startNum; i <= endNum; i++) { 
    	if (validator.test(i)) { 
        	System.out.println(i); 
         } 
     } 
};
```

이제 실행시켜보자 3번째 람다식이고 축약해서 아래와 같이 간단히 표시할 수 있다.

```
fori.accept(1, 10, (n) -> { return n % 2 == 0; }); 

//아래와 같이 간단한 형태로 표현할 수 있다. 
fori.accept(1, 10, (n) -> n % 2 == 0);
```

이제 여기서 200 부터 300 까지 홀수만 출력한다면 해당하는 함수만 변경해서 인자로 전달하면 된다.

```
//짝수 검사 
fori.accept(1, 10, (n) -> n % 2 == 0); 

//홀수 검사 
fori.accept(100, 200, (n) -> n % 2 != 0);
```

이제 표준 출력을 했던 부분도 인자로 받도록 변경하겠다. 인자가 4개이기 때문에 FourthConsumer 인터페이스를 정의해서 사용하자 validation 체크 함수는 java.util.functions.IntConsumer 를 이용하면 된다.

```
interface FourthConsumer<T1, T2, T3, T4> { 
	void accept(T1 t1, T1 t2, T3 t3, T4 t4); 
} 

static FourthConsumer<Integer, Integer, IntPredicate, IntConsumer> foriaction = (startNum, endNum, validator, action) -> { 
	for (int i = startNum; i < endNum; i++) { 
    	if (validator.test(i)) { 
        	action.accept(i); 
         } 
     } 
} 

//실행 
foriaction.accept(1, 10 , (n) -> n % 2 == 0, System.out::println);
```

아래와 같이 lambda 를 사용하지 않고 함수를 선언하면 좀 더 가독성을 높일수 있다. 또한 함수 조합으로 여러 가지 기능을 만들어낼 수 있다.

```
foriaction.accept(1, 10, isEven, print); 

//짝수가 아닌 5 이상을 출력하고 싶을 때 
foriaction.accept(1, 10, isGraterThanFive, print); 

//stdout 이 아닌 파일 쓰기 
foriaction.accept(1, 10, isEven, fileWriter); 

static IntConsumer print = System.out::println; 
static IntPredicate isEven = (n) -> n % 2 == 0; 
static IntPredicate isGraterThanFive = (n) -> n > 5; 
static Consumer fileWriter = (n) -> {/*file writer 로직*/};
```

이쯤 되면 뭐 함수형 프로그래밍의 장점이 어렴풋이 느껴지는 것 같다. 그런데 뭔가 불만스러운 부분이 있다. 인자가 너무 많다. 여기서 추가로 무언가를 더 해야 된다면 아래와 같이 되는건가?

```
//인수가 6개이다 ;; 무엇을 하는 함수인가 

foriactionmore.accept(1, 10, isEven, print, more, moreAndMore);
```

#### Currying

위와 같은 상황을 해결하기 위해서 함수형 언어에서는 Currying을 사용할 수 있다. Currying 은 특별한 API 이나 메서드는 아니고 인자를 여러개 받는 함수를 분리해서 체이닝 시키는 일종의 기법이다. 자세한 설명은 구글에 검색해보면 자세한 자료를 찾을 수 있으니 검색하길 바란다. 아래는 Currying 기법을 이용한 자바 코드이다.

```
IntConsumer print = System.out::println; 

IntPredicate isEven = (n) -> n % 2 == 0; 
IntPredicate isGraterThanFive = (n) -> n > 5; 

Function<IntPredicate, BiConsumer<Integer, Integer>> printer = curryFor.apply(print); 

BiConsumer<Integer, Integer> evener = printer.apply(isEven); 

BiConsumer<Integer, Integer> grater = printer.apply(isGraterThanFive); 

System.out.println("짝수 출력"); 
evener.accept(1, 10); 
System.out.println("5이상 출력"); 
grater.accept(1, 10); 


//define function 
static Function<IntConsumer, Function<IntPredicate, BiConsumer<Integer, Integer>>> curryFor = (action) -> (predicate) -> (startNum, endNum) -> { 
	for (int i = startNum; i <= endNum; i++) { 
    	if (predicate.test(i)) { 
        	action.accept(i); 
         } 
     } 
};
```

복잡해 보이지만 한 개씩 살펴보면 이해할 수 있을 것이다. 핵심은 아래 체이닝된 함수 정의이다.


### Java8 Stream

자바8부터 함수형 프로그래밍을 지원하기 위한 개념이 들어갔고 우리가 익히 알고있는 Stream 이라는 클래스를 지원한다. 위에서 for문을 위해서 정의했던 함수는 사실 InStream 으로 대치 가능하다. 아래 코드는 Stream API를 이용해서 구현한 코드이다. parallel() 를 이용하면 병렬처리 또한 쉽게 가능하다.

```
IntConsumer print = System.out::println; 
IntPredicate isGraterThanFive = (n) -> n > 5; 

IntStream.range(1, 10) 
.parallel() // 병렬처리 
.filter(isGraterThanFive) .forEach(print);
```

### 마무리

간단한 비지니스를 자바를 이용해 함수형 방식으로 작성해봤다. 고작 for문 로직인데 눈이 어지럽다. 하지만 높은 가독성과 코드의 재사용성 그리고 병렬처리의 용이함등 한 번 잘 작성해놓으면 분명히 장점이 있는 것 같다. 물론 실무에서 사용할법한 비지니스들은 더 높은 수준의 추상화 능력이 필요하겠지만...
