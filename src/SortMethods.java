import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class SortMethods {

    // ---------- INSERTION SORT ------------ //

    public static void insertionSort(int[] A){
        int n = A.length;
        int key;

        for (int i = 1; i < n; i++) {
            key = A[i];
            int j = i;
            while (j > 0 && A[j-1] > key){
                A[j] = A[j-1];
                j--;
            }
            A[j] = key;
        }
    }

    // ---------- MERGE SORT ------------ //

    static int[] B;

    public static void mergeSort(int A[]) {
        B = new int[A.length];
        mergeSort(A, 0, A.length - 1);
    }

    public static void mergeSort(int[] A, int min, int max){
        if (min==max)
            return;

        int mid = (min + max) / 2;

        mergeSort(A, min, mid);
        mergeSort(A, mid + 1,max);
        merge(A, min, mid, max);

    }

    public static void merge(int[] A, int min, int mid, int max)
    {
        // Kopierer de to delene over i en temporær array
        for (int i = min; i <= max; i++)
            B[i] = A[i];

        // Fletter sammen de to sorterte halvdelene over i A
        int left = min, right = mid+1;
        for (int i = min; i <= max; i++)
        {
            if (right <= max)
            {
                if (left <= mid)
                {
                    if (B[left] > B[right])
                        A[i] = B[right++];
                    else
                        A[i] = B[left++];
                }
                else
                    A[i] = B[right++];
            }
            else
                A[i] = B[left++];
        }
    }

    public static void radixSort(int a[], int maksAntSiffer){

        int ti_i_m = 1;
        int n = a.length;

        Queue<Integer>[] Q = (Queue<Integer>[])(new Queue[10]);

        for (int i = 0; i < 10; i++)
            Q[i] = new LinkedList();

        for (int m = 0; m < maksAntSiffer; m++) {
            for (int i = 0; i < n; i++) {
                int siffer = (a[i] / ti_i_m) % 10;
                Q[siffer].add(Integer.valueOf(a[i]));
            }

            int j = 0;
            for (int i = 0; i < 10; i++)
                while (!Q[i].isEmpty())
                    a[j++] = (int) Q[i].remove();

            ti_i_m *= 10;
        }

    }

    // --------- QUICK SORT ---------------- //

    public static void quickSort(int A[], int min, int max){

        int indexOfPartition;

        //Bunnen av rekursjonen er at max og min har samme verdi
        //altså at vi ender opp med segmenter a 1 element.
        if(max - min > 0){
            //Deler opp array i 2 deler, en større enn partisjoneringselementet
            //og en mindre enn partisjoneringselementet
            indexOfPartition = partitionForQuickSort(A ,min, max);

            //Sorterer venstre del
            quickSort(A, min, indexOfPartition - 1);

            //Sorterer høyre del
            quickSort(A, indexOfPartition + 1, max);
        }
    }

    //Metode for selve partisjoneringen før quicksort:
    public static int partitionForQuickSort(int[]A, int min, int max){
        int left, right;
        int temp, partitionElement;

        partitionElement = A[min];

        left = min;
        right = max;

        while(left < right){
            while(A[left] <= partitionElement && left < right){
                left++;
            }
            while(A[right] > partitionElement){
                right--;
            }
            if(left<right){
                temp = A[left];
                A[left] = A[right];
                A[right] = temp;
            }
        }
        temp = A[min];
        A[min] = A[right];
        A[right] = temp;

        return right;
    }

    // ---------- PRINT ------------- //

    public static void printList(int[]A){
        for(int i=0; i<A.length; i++){
            System.out.print(A[i] + ", ");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\n----Test of sort algorithms----\n\n");
        System.out.print("\nHow many numbers do you want in your list?\n");
        int listLength = scanner.nextInt();
        int maxDigits = listLength * 2;
        int listToSort [] = new int[listLength];

        Random rand = new Random();
        for (int i = 0; i < listLength; i++) {
            listToSort[i] = rand.nextInt(maxDigits);
        }

        //System.out.print("OK, this is your unsorted list: ");
        //printList(listToSort);

        System.out.println("\nPlease choose preferred sorting algorithm:\n\n" +
                "1 - Insertion sort\n2 - Quicksort\n3 - Merge sort\n4 - Radix sort\n");
        int sortMethod = scanner.nextInt();

        System.out.println("\nPlease choose preferred type of test:\n\n" +
                "1 - Sort and show sorting method run time\n2 - Estimate the constant in front of the highest-order term in the expression for the workload of your chosen sorting method\n");
        int testType = scanner.nextInt();

        if (sortMethod == 1) {
            long time = 0;
            int n = listLength;
            if (testType == 1) {
                System.out.println("You chose insertion sort");
                long start = System.currentTimeMillis();
                insertionSort(listToSort);
                long end = System.currentTimeMillis();
                System.out.println("\nList sorted with insertion: \n");
                printList(listToSort);
                time = end - start;
                System.out.println("\nTotal sorting time: " + time + " ms");
            } else if (testType == 2) {
                long start = System.currentTimeMillis();
                insertionSort(listToSort);
                long end = System.currentTimeMillis();
                time = end - start;
                System.out.println("Constant: " + (float) time / ((float) n * n));

            }
        } else if (sortMethod == 2) {
            long time = 0;
            int n = listLength;
            if (testType == 1) {
                System.out.println("You chose quick sort");
                long start = System.currentTimeMillis();
                quickSort(listToSort, 0, listToSort.length - 1);
                long end = System.currentTimeMillis();
                System.out.println("\nList sorted with quick sort: ");
                printList(listToSort);
                time = end - start;
                System.out.println("\nTotal sorting time: " + time + " ms");
            }
            else if (testType == 2){
                //Beregn konstant
                long start = System.currentTimeMillis();
                quickSort(listToSort, 0, listToSort.length - 1);
                long end = System.currentTimeMillis();
                time = end - start;
                System.out.println("Constant: " + (float) time / ((float) n * Math.log(n)));

            }
        } else if (sortMethod == 3) {
            long time = 0;
            int n = listLength;
            if (testType == 1){
                System.out.println("You chose merge sort");
                long start = System.currentTimeMillis();
                SortMethods obj = new SortMethods();
                obj.mergeSort(listToSort);
                long end = System.currentTimeMillis();
                System.out.println("\nList sorted with merge sort: ");
                printList(listToSort);
                time = end - start;
                System.out.println("\nTotal sorting time: " + time + " ms");
            }
            else if (testType == 2){
                long start = System.currentTimeMillis();
                SortMethods obj = new SortMethods();
                obj.mergeSort(listToSort);
                long end = System.currentTimeMillis();
                time = end - start;
                System.out.println("Constant: " + (float) time / ((float) n * Math.log(n)));
            }
        } else {
            long time = 0;
            int n = listLength;
            if (testType == 1){
                System.out.println("You chose radix sort");
                long start = System.currentTimeMillis();
                SortMethods obj = new SortMethods();
                obj.radixSort(listToSort, String.valueOf(maxDigits).length());
                long end = System.currentTimeMillis();
                System.out.println("\nList sorted with radix sort: ");
                printList(listToSort);
                time = end - start;
                System.out.println("\nTotal sorting time: " + time + " ms");
            }
            else if (testType == 2){
                long start = System.currentTimeMillis();
                SortMethods obj = new SortMethods();
                obj.radixSort(listToSort, String.valueOf(maxDigits).length());
                long end = System.currentTimeMillis();
                time = end - start;
                System.out.println("Constant: " + (float) time / ((float) n * Math.log(n)));

            }

        }

    }
}

/* Kilder
 *
 * Høiberg, J. for Høgskolen i Østfold (2023). Programkode fra forelesninger:
 * - "Radixsortering av heltall" - radixSort2.java
 * - "Sekvensielle sorteringsmetoder" - sequentialSorting.java
 * - "Test av effektivitet av sorteringsalgoritmer" - sortingTest.java
 * - "Logaritmiske sorteringsmetoder" - logarithmicSorting.java
 * - "Test av utplukkssortering" - sortTest.java
 *
 * GeeksForGeeks(26 oktober, 2016). Generating random numbers in Java.
 * https://www.geeksforgeeks.org/generating-random-numbers-in-java/
 *
 *
 * */
