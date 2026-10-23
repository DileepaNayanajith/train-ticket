import java.io.*;

/** Java 15 contestant starter. Input parsing only; implement solve(). */
public class Solution {
    static final class TrainInput {
        String name;
        int capacityFirst, capacitySecond;
        int[] stations;
    }

    static final class OperationInput {
        int parent;
        String type;
        // BOOK: id, train, class, from, to
        // PAY/CANCEL: id; REBOOK: id, train, class; CHECK: train, class, from, to
        int[] values;
    }

    static final class FastInput {
        private final InputStream input = new BufferedInputStream(System.in, 1 << 16);
        private int first() throws IOException {
            int c;
            do { c = input.read(); } while (c >= 0 && c <= 32);
            if (c < 0) throw new EOFException("Unexpected end of input");
            return c;
        }
        String next() throws IOException {
            StringBuilder token = new StringBuilder();
            int c = first();
            do { token.append((char)c); c = input.read(); } while (c > 32);
            return token.toString();
        }
        int nextInt() throws IOException {
            int c = first();
            long number = 0;
            do {
                if (c < '0' || c > '9') throw new IOException("Expected decimal integer");
                number = number * 10 + c - '0';
                if (number > Integer.MAX_VALUE) throw new IOException("Integer out of range");
                c = input.read();
            } while (c > 32);
            return (int)number;
        }
    }

    static void solve(TrainInput[] trains, OperationInput[] operations, StringBuilder output) {
        // Both arrays are 1-indexed; index 0 of trains/operations is unused.
        // Version 0 is the empty initial state. Discover source-derived rules first.
        // Append exactly one answer and '\n' for each operation in input order.
        throw new UnsupportedOperationException("Implement the contestant solution here");
    }

    public static void main(String[] args) throws Exception {
        FastInput input = new FastInput();
        int trainCount = input.nextInt(), operationCount = input.nextInt();
        TrainInput[] trains = new TrainInput[trainCount + 1];
        for (int i = 1; i <= trainCount; i++) {
            TrainInput train = new TrainInput();
            train.name = input.next();
            int stationCount = input.nextInt();
            train.capacityFirst = input.nextInt();
            train.capacitySecond = input.nextInt();
            train.stations = new int[stationCount];
            for (int j = 0; j < stationCount; j++) train.stations[j] = input.nextInt();
            trains[i] = train;
        }
        OperationInput[] operations = new OperationInput[operationCount + 1];
        for (int i = 1; i <= operationCount; i++) {
            OperationInput operation = new OperationInput();
            operation.parent = input.nextInt();
            operation.type = input.next();
            int count;
            switch (operation.type) {
                case "BOOK": count = 5; break;
                case "PAY": case "CANCEL": count = 1; break;
                case "REBOOK": count = 3; break;
                case "CHECK": count = 4; break;
                default: throw new IOException("Unknown operation");
            }
            operation.values = new int[count];
            for (int j = 0; j < count; j++) operation.values[j] = input.nextInt();
            operations[i] = operation;
        }
        StringBuilder output = new StringBuilder(operationCount * 10);
        solve(trains, operations, output);
        System.out.print(output);
    }
}
