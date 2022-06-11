/**
 * Stavros Nikolaidis
 * ΑΕΜ: 3975
 * email: stavniko@csd.auth.gr
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//class for the points
class Pair{

    private final int x;
    private final int y;

    // constructor
    Pair(int a, int b){
        this.x = a;
        this.y = b;
    }

    // getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}


public class Skyline {

    /**
     *
     * merge function from the source: https://www.geeksforgeeks.org/merge-sort/
     * Lightly changed for the ArrayList type of the param arr.
     *
     * @param arr the arraylist to be merged.
     * @param l the index of the start of the (sub)array.
     * @param m the mid index of the (sub)array.
     * @param r the index of the end of the (sub)array.
     */
    public static void merge(ArrayList<Pair> arr, int l, int m, int r)
    {

        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        ArrayList<Pair> L = new ArrayList<>();
        ArrayList<Pair> R = new ArrayList<>();

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L.add(arr.get(l + i));
        for (int j = 0; j < n2; ++j)
            R.add(arr.get(m + 1 + j));

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L.get(i).getX() < R.get(j).getX()) {
                arr.set(k, L.get(i));
                i++;
            }
            else if (L.get(i).getX() == R.get(j).getX()){
                if(L.get(i).getY() <= R.get(j).getY()) {
                    arr.set(k, L.get(i));
                    i++;
                }
                else{
                    arr.set(k, R.get(j));
                    j++;
                }
            }
            else{
                arr.set(k, R.get(j));
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr.set(k, L.get(i));
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr.set(k, R.get(j));
            j++;
            k++;
        }
    }

    /**
     *
     * Main function that sorts arr[l..r] using
     * merge()
     * sort function from the source: https://www.geeksforgeeks.org/merge-sort/
     * Lightly changed for the ArrayList type of the param arr.
     *
     * @param arr the arraylist to be sorted.
     * @param l the index of the start of the (sub)array.
     * @param r the index of the end of the (sub)array.
     */
    public static void sort(ArrayList<Pair> arr, int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    public static void main(String[] args) {

        // Reading the file
        try (BufferedReader buffer = new BufferedReader(new FileReader(args[0]))){

            int n = Integer.parseInt(buffer.readLine()); // number of points we have
            String line;    // var to get each separate line

            ArrayList<Pair> pairs = new ArrayList<>(); // creating an arraylist of points

            // getting all the points from the txt
            while((line = buffer.readLine()) != null){
                String[] s1 = line.split(" "); // putting the line's ints to an array
                int x = Integer.parseInt(s1[0]);   // getting x
                int y = Integer.parseInt(s1[1]);   // getting y
                pairs.add(new Pair(x,y));          // making new point
            }

            sort(pairs, 0, n-1); // sorting the pairs O(n*log(n)). This is an important step because the method we use later to solve the problem can't work without it.

            ArrayList<Pair> skyline = new ArrayList<>();  // creating the arraylist of the skyline

            Pair flag = pairs.get(0); // setting the first point as flag, the flag is the current point that it's not dominated by any other point
            skyline.add(flag);
            for (Pair a : pairs) {
                if(a.getX() < flag.getX() || a.getY() < flag.getY()) // checking if we need to change the flag
                {
                    flag = a;
                    skyline.add(flag); // if we need we add the new flag to the skyline
                }
            }   // O(n)

            // printing the results
            for (Pair a: skyline) {
                System.out.println(a.getX() + " " + a.getY());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
