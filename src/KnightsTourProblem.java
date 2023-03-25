import java.util.Scanner;

public class KnightsTourProblem {
    int FRI = 0;

    int ANTALL_MULIGE_FLYTT = 8;

    int n, teller;
    int L[][];
    int antRuter;

    int dI[] = {2, 2, 1, -1, -2, -2, -1, 1};
    int dJ[] = {-1, 1, 2, 2, 1, -1, -2, -2};

    public KnightsTourProblem(int n) {
        this.n = n;
        this.antRuter = n * n;
        L = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                L[i][j] = FRI;
    }

    boolean finnVei(int i, int j) {

        L[i][j] = ++teller;

        if (teller == antRuter) {
            return true;
        }


        for (int k = 0; k < ANTALL_MULIGE_FLYTT; k++) {
            int nyI = i + dI[k];
            int nyJ = j + dJ[k];

            if (nyI >= 0 && nyI < n && nyJ >= 0 && nyJ < n && L[nyI][nyJ] == FRI) {
                if (finnVei(nyI, nyJ)) {
                    return true;
                }
            }
        }

        L[i][j] = FRI;
        teller--;
        return false;
    }

    public void printOut() {
        int max = String.valueOf(teller).length();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(L[i][j]);
                int spaces = max - String.valueOf(L[i][j]).length() + 1;
                for (int k = 0; k < spaces; k++)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
    public static void main (String argv[])
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Hvilken nXn størrelse vil du ha på sjakkbrettet?: ");
        int n = scanner.nextInt();

        KnightsTourProblem brett = new KnightsTourProblem(n);

        boolean funnetVei = brett.finnVei(0, 0);

        if (funnetVei) {
            System.out.println("\nLøsning funnet:\n");
            brett.printOut();
        }
        else {
            System.out.println("Fant ingen vei gjennom labyrinten");
        }
    }
}