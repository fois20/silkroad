import java.util.*;

public class Test {

    private List<Integer> robotPositions = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();

    static class Store {
        int position;
        int tenges;
        Store(int p, int c) {
            position = p;
            tenges = c;
        }
    }

    public void addRobot(int x) {
        robotPositions.add(x);
    }

    public void addStore(int x, int c) {
        stores.add(new Store(x, c));
    }

    // Returns both max profit and robot→store assignments
    public Result moveRobots() {
        if (robotPositions.isEmpty() || stores.isEmpty())
            return new Result(0, new ArrayList<>());

        Collections.sort(robotPositions);
        stores.sort(Comparator.comparingInt(s -> s.position));

        int m = robotPositions.size();
        int k = stores.size();

        long[][] dp = new long[m + 1][k + 1];
        int[][] choice = new int[m + 1][k + 1]; 
        // 0 = from top (skip robot), 1 = from left (skip store), 2 = paired

        // Fill DP
        for (int i = 1; i <= m; i++) {
            int robotX = robotPositions.get(i - 1);
            for (int j = 1; j <= k; j++) {
                Store store = stores.get(j - 1);
                long pairProfit = store.tenges - Math.abs(robotX - store.position);

                // Case 1: skip robot
                long best = dp[i - 1][j];
                int move = 0;

                // Case 2: skip store
                if (dp[i][j - 1] > best) {
                    best = dp[i][j - 1];
                    move = 1;
                }

                // Case 3: pair them
                if (dp[i - 1][j - 1] + pairProfit > best) {
                    best = dp[i - 1][j - 1] + pairProfit;
                    move = 2;
                }

                dp[i][j] = best;
                choice[i][j] = move;
            }
        }

        long maxProfit = Math.max(0, dp[m][k]);

        // Now backtrack to recover the assignments
        List<Assignment> assignments = new ArrayList<>();
        int i = m, j = k;
        while (i > 0 && j > 0) {
            if (choice[i][j] == 2) { // paired
                int robotX = robotPositions.get(i - 1);
                Store store = stores.get(j - 1);
                long profit = store.tenges - Math.abs(robotX - store.position);
                assignments.add(new Assignment(robotX, store.position, profit));
                i--;
                j--;
            } else if (choice[i][j] == 0) {
                i--; // skipped robot
            } else {
                j--; // skipped store
            }
        }

        Collections.reverse(assignments); // from left to right
        return new Result(maxProfit, assignments);
    }

    // Classes to hold the output
    static class Assignment {
        int robotPos, storePos;
        long profit;
        Assignment(int r, int s, long p) {
            robotPos = r; storePos = s; profit = p;
        }
        public String toString() {
            return "Robot at " + robotPos + " → Store at " + storePos +
                   " (profit " + profit + ")";
        }
    }

    static class Result {
        long maxProfit;
        List<Assignment> assignments;
        Result(long p, List<Assignment> a) {
            maxProfit = p; assignments = a;
        }
    }

    // Demo
    public static void main(String[] args) {
        Test road = new Test();
        road.addRobot(0);
        road.addRobot(1);
        road.addRobot(2);
        road.addStore(3, 100);
        road.addStore(4, 100);
        road.addStore(5, 100);
        
        Result res = road.moveRobots();
        System.out.println("Max Profit = " + res.maxProfit);
        for (Assignment a : res.assignments) System.out.println(a);
    }
}
