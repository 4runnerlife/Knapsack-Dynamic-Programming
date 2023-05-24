import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class KnapsackDP {
    static int numberOfItems;
    static int maxWeight;
    static int tableReferences =0;
    static int counterWeight=0;
    static int counterValue=0;

    /*
    This method takes two arrays, one with the value of the items, one with the weight of the items.  It
    also takes the max weight of the knapsack as well as the total number of items.  This will use dynamic
    programing to build the optimal value table and the decision table.
     */
    static int knapSack( int weight[], int value[],int maxWeight, int numberOfItems) {
        int optimalValue[][] = new int[numberOfItems+1][maxWeight+1];
        int decisionTable[][] = new int[numberOfItems + 1][maxWeight + 1];
        for (int i =0; i <= numberOfItems; i++) {
            for (int w = 0; w <= maxWeight; w++) {
                if (i == 0 || w == 0) {
                    optimalValue[i][0] = 0;
                    decisionTable[i][w]=0;
                    tableReferences-=1;
                }else if (weight[i - 1] <= w) {
                    optimalValue[i][w] = Math.max(value[i - 1] + optimalValue[i - 1][w - weight[i - 1]], optimalValue[i - 1][w]);
                    if((value[i - 1] + optimalValue[i - 1][w - weight[i - 1]])>optimalValue[i - 1][w]){
                        decisionTable[i][w]=1;
                        tableReferences+=1;
                    }else{
                        decisionTable[i][w]=0;
                        tableReferences+=1;
                    }
                }else
                    optimalValue[i][w] = optimalValue[i - 1][w];
                tableReferences+=1;
            }
        }
        System.out.println("Optimal Solution: ");
        System.out.println("Total Weight: " + maxWeight);
        System.out.println("Optimal Value:" +optimalValue[numberOfItems][maxWeight]);
        System.out.println("Number of table references: "+tableReferences );
        return optimalValue[numberOfItems][maxWeight];
    }
    /*
    This method takes two arrays, one with the value of the items, one with the weight of the items.  It
    also takes the max weight of the knapsack as well as the total number of items.  This will use dynamic
    programing to build the optimal value table and the decision table.  This method will also print the optimal
    and decision tale to two files,KnapsackDP-VTable and KnapsackDP-DTable respectfully.

     */
    static int knapSack1( int weight[], int value[],int maxWeight, int numberOfItems) throws FileNotFoundException {

        int optimalValue[][] = new int[numberOfItems+1][maxWeight+1];
        int decisionTable[][] = new int[numberOfItems + 1][maxWeight + 1];
        for (int i =0; i <= numberOfItems; i++) {
            for (int w = 0; w <= maxWeight; w++) {
                if (i == 0 || w == 0) {
                    optimalValue[i][0] = 0;
                    decisionTable[i][w]=0;
                    tableReferences-=1;
                }else if (weight[i - 1] <= w) {
                    optimalValue[i][w] = Math.max(value[i - 1] + optimalValue[i - 1][w - weight[i - 1]], optimalValue[i - 1][w]);
                    if((value[i - 1] + optimalValue[i - 1][w - weight[i - 1]])>optimalValue[i - 1][w]){
                        decisionTable[i][w]=1;
                        tableReferences+=1;
                    }else{
                        decisionTable[i][w]=0;
                        tableReferences+=1;
                    }
                }else
                    optimalValue[i][w] = optimalValue[i - 1][w];
                tableReferences+=1;
            }
        }
         int optimalValue2[][] = new int[numberOfItems][maxWeight];
        int decisionTable2[][] = new int[numberOfItems][maxWeight];

        for(int m=1;  m<=numberOfItems; m++){
            for(int n=1; n <=maxWeight-1; n++){
                int temp = optimalValue[m][n];
                int temp2 = decisionTable[m][n];
                optimalValue2[m-1][n-1]= temp;
                decisionTable2[m-1][n-1]=temp2;
            }
        }
        for(int i=0; i<numberOfItems; i++){
            optimalValue2[i][maxWeight-1]= optimalValue[i][maxWeight];
            decisionTable2[i][maxWeight-1]=decisionTable[i][maxWeight];
        }
        optimalValue2[0][maxWeight-1]= optimalValue[1][maxWeight];
        decisionTable2[0][maxWeight-1]=decisionTable[1][maxWeight];

        String fileName ="KnapsackDP-VTable";
        PrintStream ps = new PrintStream(fileName);

        for (int m = 0; m < numberOfItems; m++) {
            for (int j = 0; j < maxWeight; j++) {
                ps.append(optimalValue2[m][j] + " ");
            }
            ps.append("\n");
        }
        String fileName2 ="KnapsackDP-DTable";
        PrintStream ps2 = new PrintStream(fileName2);

        for (int m = 0; m < numberOfItems; m++) {
            for (int j = 0; j <maxWeight; j++) {
                ps2.append(decisionTable2[m][j] + " ");
            }
            ps2.append("\n");
        }

        int optimalSolution [] = new int[numberOfItems];
        int tempWeight2 =maxWeight;
        int bestResult = optimalValue[numberOfItems][maxWeight];
        for (int i = numberOfItems; i > 0 && bestResult > 0; i--) {
            if (bestResult == optimalValue[i - 1][tempWeight2])
                continue;
            else {
                optimalSolution[i]=1;
                bestResult = bestResult - value[i - 1];
                tempWeight2 = tempWeight2 - weight[i - 1];
            }
        }

        System.out.println("Optimal Solution: ");
        System.out.print("{");
        String place ="";
        for(int i =0; i <numberOfItems; i++){
            if(optimalSolution[i] ==1){
                    System.out.print(place + i);
                    place =",";
            }
        }
        System.out.print("}");
        System.out.println("");
        System.out.println("Total Weight: " + maxWeight);
        System.out.println("Optimal Value: " +optimalValue[numberOfItems][maxWeight]);
        System.out.println("Number of table references: "+tableReferences );
        return optimalValue[numberOfItems][maxWeight];
    }

    public static void main(String args[]) throws FileNotFoundException {

        //Checks the length of the command line arguments to make sure they are correct.
        if (args.length <= 2) {
            System.out.println("Too few Command Line Arguments entered.");
            printUsage();
            return;
        }

        //Checks the length of the command line arguments to make sure they are correct.
        if (args.length > 6) {
            System.out.println("Too many Command Line Arguments entered.");
            printUsage();
            return;
        }

        /*These parse the command line arguments to get the number of items, max weight, as well as the values and weights
        of the items.
         */
        try {
            numberOfItems = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            printUsage();
            return;
        }
        try {
            maxWeight = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            printUsage();
            return;
        }

        File file = new File(args[2]);
        int[] weight = new int[numberOfItems];
        for(int i =0; i<numberOfItems; i++) {
            weight[i] = -1;
        }
        try {
            Scanner fileScan = new Scanner(file);
            while (fileScan.hasNextLine()) {
                for(int i =0; i<numberOfItems; i++) {
                    if(fileScan.hasNextInt()) {
                        int temp = fileScan.nextInt();
                        weight[i] = temp;
                        counterWeight++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            printUsage();
            return;
        }
        for(int i =0; i<numberOfItems; i++) {
            if(weight[i]==-1){
                printUsage();
                return;
            }
        }

        File file2 = new File(args[3]);
        int[] value = new int[numberOfItems];
        for(int i =0; i<numberOfItems; i++) {
            value[i] = -1;
        }
        try {
            Scanner fileScan = new Scanner(file2);
            while (fileScan.hasNextLine()) {
                for(int i =0; i<numberOfItems; i++) {
                    if(fileScan.hasNextInt()) {
                        int temp = fileScan.nextInt();
                        value[i] = temp;
                        counterValue++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            printUsage();
            return;
        }for(int i =0; i<numberOfItems; i++) {
            if(value[i]==-1){
                printUsage();
                return;
            }
        }

        //This checks to make sure the to files are the same length
        if(weight.length !=value.length){
            printUsage();
            return;
        }
        /*This checks to make sure the number of items in the commandline arguments
        match the number of items in the text files.
         */
        if((weight.length !=numberOfItems) || (value.length !=numberOfItems)){
            printUsage();
            return;
        }
        if((numberOfItems<counterWeight) || (numberOfItems<counterValue)){
            printUsage();
            return;
        }

        //This checks the debug level for the program.
        if(args.length==5){
            if(args[4].contentEquals("0")){
                knapSack(weight, value, maxWeight, numberOfItems);
            } else if(args[4].contentEquals("1")){
                knapSack1(weight,value,maxWeight,numberOfItems);
            } else{
                printUsage();
                return;
            }
        }

    }
        /*If the user enters information that fails the above checks, this will print instructions
        on how to use the program.
         */
    private static void printUsage() {
        System.out.println("java Knapsack Dynamic Programing Command Line Arguments");
        System.out.println("The fist argument must be the number of items");
        System.out.println("The second argument will be the maximum weight capacity of the knapsack ");
        System.out.println("The third argument must be a .txt file that has the weight of the items");
        System.out.println("The fourth argument must be a .txt file that has the value of the items");
        System.out.println("The files for the third and fourth arguments must contain the same number of lines");
        System.out.println("The fifth argument must be a [0|1] 0 prints the summary to the console.  1 prints the summary " +
                "and the  print the\n" +
                "optimal value table and the decision table to two files KnapsackDP-VTable and\n" +
                "KnapsackDP-DTable");
    }
}

