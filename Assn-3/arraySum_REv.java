import mpi.*

public class ArraySum {
    public static void main(String args[]){
        MPI.Init(args);
        int size=MPI.COMM_WORLD.size;
        int rank = MPI.COMM_WORLD.Rank;
        int sendBuffer[] = new int[] {10,20,30,40,50,60,708,90,120,140};
        int length= sendBuffer.length;
        int elementsPerProcess = length/size;
        int root=0;
        int recvBuffer[] = new int[elementsPerProcess];
        MPI.Scatter(sendBuffer,0,elementsPerProcess,MPI.INT,recvBuffer,0,elementsPerProcess,MPI.INT,root);

        int startIndex = root+elementsPerProcess;
        int endIndex = Math.min(startIndex+elementsPerProcess, length);
        int sum=0;
        for(int i=startIndex;i<endIndex;i++){
            sum+=sendBuffer[i];
        }
        System.out.println("RANK: "+rank+" SUM: "+sum);
        int Sum[] = new int[]{sum};
        int ans[] = new int[size];
        MPI.Gather(Sum,0,1,MPI.INT,ans,0,1,MPI.INT,root);
        if(rank==root){

        }
        MPI.Finalize();
    }
}