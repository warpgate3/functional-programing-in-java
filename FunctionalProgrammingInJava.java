package info.m2sj;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class FunctionalProgrammingInJava {
    public static void main(String[] args) {
        //sort of java functions
        IntConsumer print = System.out::println;
        IntPredicate isGraterThanFive = (n) -> n > 5;
        IntPredicate isEven = (n) -> n % 2 == 0;

        //plain functional
        foriaction.accept(1, 10, isEven, print);

        //currying 을 이용한 함수 조합
        Function<IntPredicate, BiConsumer<Integer, Integer>> printer = curryFor.apply(print);
        BiConsumer<Integer, Integer> evenPrinter = printer.apply(isEven);
        BiConsumer<Integer, Integer> graterPrinter = printer.apply(isGraterThanFive);
        System.out.println("짝수 출력");
        evenPrinter.accept(1, 10);
        System.out.println("5이상 출력");

        graterPrinter.accept(1, 10);
        //stream 을 이용
        IntStream.range(1, 10)
                .parallel()
                .filter(isGraterThanFive)
                .forEach(print);
    }

    static Function<IntConsumer, Function<IntPredicate, BiConsumer<Integer, Integer>>> curryFor
            = (action) -> (predicate) -> (startNum, endNum) -> {
        for (int i = startNum; i <= endNum; i++) {
            if (predicate.test(i)) {
                action.accept(i);
            }
        }
    };

    static ThreeConsumer<Integer, Integer, IntPredicate> fori = (startNum, endNum, validator) -> {
        for (int i = startNum; i <= endNum; i++) {
            if (validator.test(i)) {
                System.out.println(i);
            }
        }
    };

    static IntConsumer fileWriter = (n) -> System.out.println(Thread.currentThread().getName() + ":Sending file-->" + n);

    interface ThreeConsumer<T1, T2, T3> {
        void accept(T1 t1, T1 t2, T3 t3);
    }

    interface FourthConsumer<T1, T2, T3, T4> {
        void accept(T1 t1, T1 t2, T3 t3, T4 t4);
    }

    static FourthConsumer<Integer, Integer, IntPredicate, IntConsumer> foriaction = (startNum, endNum, validator, action) -> {
        for (int i = startNum; i <= endNum; i++) {
            if (validator.test(i)) {
                action.accept(i);
            }
        }
    };
}
