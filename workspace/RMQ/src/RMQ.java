import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class RMQ {

    /**
     * the main function.
     * @param args commandline args
     */
    public static void main(final String[] args) {

        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(System.in));
        try {
            while (true) {
                String line = reader.readLine();

                if (line.startsWith("END")) {
                    break;
                }

                // first line n m
                String[] splittedLine = line.split(" ");
                long n = Long.parseLong(splittedLine[0]);
                int m = Integer.parseInt(splittedLine[1]);

                // second line h
                line = reader.readLine();
                String[] strh = line.split(" ");
                long[] h = new long[m];
                for (int i = 0; i < m; i++) {
                    h[i] = Long.parseLong(strh[i]);
                }

                // start with skyline
                long[] r = new long[(int) n];
                r = generateH(n, m, h);
                // print(r);
                System.out.println(skyline(r, n));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // long[] h = {3, 6, 5, 6, 2, 4};
        // long n = 6;
        // int m = 6;
        // long[] h = {5,0,7,0,2,6,2};
        // long n = 7;
        // int m = 7;
        // long[] h = {3, 6, 5, 6, 2, 6, 5, 6, 5, 6, 2, 4};
        // long n = 12;
        // int m = 12;
        // long[] h = {2, 6, 5, 2, 3, 6, 2, 6, 5};
        // long n = 8;
        // int m = 8;
        // long[] h = {1048589, 2097165};
        // long n = 100000;
        // int m = 2;

        // long[] r = new long[(int)n];
        // r = generateH(n,m,h);
        // print(r);
        // System.out.println(Arrays.toString(r));

        // System.out.println("MAXarea: "+skyline(r, n));
    }

    /**
     * Solves the skyline problem. A skyline is the outline formed by a group of
     * buildings against the sky. A certain city has a beautiful skyline that's
     * visible to everybody as they approach it by car. You have bought the
     * rights to place an advertisement over it, and you would like to do so
     * while preserving the shape of the city. The skyline is formed by n
     * buildings, all with a width of 1 and each with a different height. You
     * will place your ad on a rectangle of maximum area that is fully contained
     * within the interior of the skyline.
     *
     * @param r
     *            the array containing the building heights
     * @param n
     *            the number of buildings
     * @return the maximum area on the buildings
     */
    public static long skyline(final long[] r, final long n) {
        if (n == 1) {
            return r[(int) n - 1];
        } else {
            int[][] sparseTable = process2(r, r.length);

            int minimum = min(0, (int) n - 1, r, sparseTable);
            ArrayList<int[]> interval = new ArrayList<int[]>();
            long area = n * r[minimum];

            if (minimum == 0) {
                if (r[0] > area) {
                    area = r[0];
                }
            } else {
                int[] links = new int[2];
                links[0] = 0;
                links[1] = minimum - 1;
                interval.add(links);
            }

            if (minimum == n - 1) {
                if (r[minimum] > area) {
                    area = r[minimum];
                }
            } else {
                int[] rechts = new int[2];
                rechts[0] = minimum + 1;
                rechts[1] = (int) n - 1;
                interval.add(rechts);
            }

            // System.out.println("intervall: 0,"+(n-1)+" min: "+minimum+"  area: "+area);
            while (!interval.isEmpty()) {

                int[] tmp = interval.remove(0);
                int minTmp = min(tmp[0], tmp[1], r, sparseTable);

                long tmpArea = r[minTmp] * (tmp[1] - tmp[0] + 1);
                if (tmpArea > area) {
                    area = tmpArea;
                }
                // System.out.println("intervall: "+tmp[0]+","+tmp[1]+" min: "+minTmp+"  area: "+area+" tmparea: "+tmpArea);

                // links
                if (tmp[0] == minTmp || tmp[0] == minTmp - 1) {
                    if (r[tmp[0]] > area) {
                        area = r[tmp[0]];
                    }
                } else {
                    int[] linksTmp = new int[2];
                    linksTmp[0] = tmp[0];
                    linksTmp[1] = minTmp - 1;
                    interval.add(linksTmp);
                    // System.out.println("added l interval: "+linksTmp[0]+","+linksTmp[1]);
                }

                // rechts
                if (minTmp == tmp[1] || minTmp + 1 == tmp[1]) {
                    if (r[tmp[1]] > area) {
                        area = r[tmp[1]];
                    }
                } else {
                    int[] rechtsTmp = new int[2];
                    rechtsTmp[0] = minTmp + 1;
                    rechtsTmp[1] = tmp[1];
                    interval.add(rechtsTmp);
                    // System.out.println("added r interval: "+rechtsTmp[0]+","+rechtsTmp[1]);
                }
            }
            return area;
        }
    }

    /**
     * used to preprocess the n heights out of the m given start vectors in h.
     *
     * @param n
     *            number of buildings
     * @param m
     *            number of elements in h
     * @param h
     *            start vector with m inital heights
     * @return the buildingheights array with length n
     */
    public static long[] generateH(final long n, final int m, final long[] h) {
        int j = 0;
        int s = 0;
        long[] r = new long[(int) n];
        for (int i = 0; i <= n - 1; ++i) {
            r[i] = h[j];
            s = (j + 1) % m;
            h[j] = ((h[j] ^ h[s]) + 13) % 835454957;
            j = s;
        }
        return r;
    }

    /**
     * RMQ function. searches the range minimum in r in O(1)
     *
     * @param i
     *            start of the query range
     * @param j
     *            end of the query range
     * @param r
     *            the range to be searched in
     * @param M
     *            the preprocessed sparse matrix
     * @return the index of the minimum in r between i and j
     */
    public static int min(final int i, 
                            final int j, 
                            final long[] r, 
                            final int[][] M) {

        int k = (int) ((Math.log(j - i + 1) / Math.log(2)));
        int mini = M[(int) (j - (1 << k) + 1)][k]; // 1<<k = 2^k

        if (r[(int) M[i][k]] <= r[(int) mini]) {
            return M[i][k];
        } else {
            return mini;
        }

    }

    /**
     * preprocesses the array A for quicker RMQ in O(n*log(n)).
     *
     * @param A
     *            the given array
     * @param N
     *            size of the array
     * @return the sparse Matrix used in RMQ
     */
    public static int[][] process2(final long[] A, final int N) {
        int i, j;

        int[][] M = new int[N][(int) Math.ceil(Math.log(N) / Math.log(2)) + 1];
        // initialize M for the intervals with length 1
        for (i = 0; i < N; i++) {
            M[i][0] = i;
        }
        // compute values from smaller to bigger intervals
        for (j = 1; 1 << j <= N; j++) {
            for (i = 0; i + (1 << j) - 1 < N; i++) {
                if (A[(int) M[i][j-1]] < A[(int) M[i + (1 << (j - 1))][j-1]]){
                    M[i][j] = M[i][j - 1];
                } else {
                    M[i][j] = M[i + (1 << (j - 1))][j - 1];
                }
            }
        }
        return M;
    }

    /**
     * function to visualize the skyline.
     *
     * @param r
     *            the array with the building heights
     */
    public static void print(final long[] r) {
        // find max in array
        long n = r.length;
        long[] neu = new long[(int) n];
        neu = r.clone();
        Arrays.sort(neu);
        long maxheight = neu[(int) (n - 1)];

        // draw skyline
        for (long i = maxheight; i >= 1; i--) {
            System.out.print("|");
            for (long k = 0; k < n; k++) {
                if (r[(int) k] / i > 0) {
                    System.out.print("▓|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
        System.out.print("|");
        for (int i = 0; i < n; i++) {
            System.out.print(i / 10 + "|");
        }
        System.out.println();
        System.out.print("|");
        for (int i = 0; i < n; i++) {
            System.out.print(i % 10 + "|");
        }
        System.out.println();
    }

}
