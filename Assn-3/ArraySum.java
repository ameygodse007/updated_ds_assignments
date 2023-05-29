import mpi.*;

public class ArraySum {
    public static void main(String args[]) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int root = 0;

        int sendBuffer[] = new int[] { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 120, 140 };
        int length = sendBuffer.length;
        int elementsPerProcess = length / size;
        int recvBuffer[] = new int[elementsPerProcess];

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, elementsPerProcess, MPI.INT, recvBuffer, 0, elementsPerProcess, MPI.INT,
                root);

        int sum = 0;
        int startIndex = rank * elementsPerProcess;
        int endIndex = Math.min(startIndex + elementsPerProcess, length);
        for (int i = startIndex; i < endIndex; i++) {
            sum += sendBuffer[i];
        }
        System.out.println("Rank: " + rank + " Sum: " + sum);

        int sumBuff[] = new int[] { sum };
        int ans[] = new int[size];

        MPI.COMM_WORLD.Gather(sumBuff, 0, 1, MPI.INT, ans, 0, 1, MPI.INT, root);

        if (root == rank) {
            int finalSum = 0;
            for (int i = 0; i < size; i++) {
                finalSum += ans[i];
            }
            System.out.println("Ans: " + finalSum);
        }

        MPI.Finalize();
    }
}
