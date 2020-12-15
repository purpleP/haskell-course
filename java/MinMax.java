import java.util.*;
import java.util.stream.Collectors;

public class MinMax {
    static class Result<T extends Comparable<T>> {
        public T min;
        public T max;

        public Result(T min, T max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Result pair = (Result) o;
            return Objects.equals(this.min, pair.min)
                && Objects.equals(this.max, pair.max);
        }

        @Override
        public int hashCode() {
            return Objects.hash(min, max);
        }
    }

    private static final Random RAND = new Random();

    public static void main(String[] args) {
        int iterations = Integer.parseInt(args[0]);
        for (int j = 1; j < args.length; j++) {
            bench(Integer.parseInt(args[j]), iterations);
        }
    }

    static List<Integer> randList(int listSize) {
        int max = RAND.nextInt();
        int min = RAND.nextInt();
        if (max < min) {
            int temp = max;
            max = min;
            min = temp;
        }
        List<Integer> input = RAND.ints(listSize - 2, min, max).boxed().collect(Collectors.toList());
        input.add(min);
        input.add(max);
        Collections.shuffle(input);
        return input;
    }

    static void bench(int size, int iterations) {
        var buffer = new StringBuffer(); 
        for (int i = 0; i < iterations; i++) {
            var l = randList(size);
            var t1 = System.nanoTime();
            minMax(l.iterator());
            var elapsed = System.nanoTime() - t1;
            buffer.append("\t").append(elapsed);
        }
        System.out.printf("%d%s\n", size, buffer);
    }

    public static <T extends Comparable<T>> Optional<Result<T>> minMax(Iterator<T> input) {
        if (!input.hasNext()) {
            Optional.empty();
        }
        T firstElement = input.next();
        Result pair = new Result(firstElement, firstElement);
        while (input.hasNext()) {
            T hi = input.next();
            T lo = input.hasNext() ? input.next() : firstElement;
            T temp;
            if (hi.compareTo(lo) < 0) {
                temp = lo;
                lo = hi;
                hi = temp;
            }
            if (pair.min.compareTo(lo) > 0) {
                pair.min = lo;
            }
            if (pair.max.compareTo(hi) < 0) {
                pair.max = hi;
            }
        }
        return Optional.of(pair);
    }

}
