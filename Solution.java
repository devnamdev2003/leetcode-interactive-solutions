import java.util.*;

class Solution {
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        Integer[] indices = new Integer[n];
        
        // Step 1: Initialize an array of original indices
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Step 2: Sort the indices based on the starting positions
        Arrays.sort(indices, (a, b) -> Integer.compare(positions[a], positions[b]));

        Stack<Integer> stack = new Stack<>();

        // Step 3: Simulate collisions using a Stack
        for (int currentIndex : indices) {
            if (directions.charAt(currentIndex) == 'R') {
                // Moving right, push to stack to wait for potential collisions
                stack.push(currentIndex);
            } else {
                // Moving left, process collisions with any 'R' robots on the stack
                while (!stack.isEmpty() && healths[currentIndex] > 0) {
                    int topIndex = stack.peek();
                    
                    if (healths[topIndex] < healths[currentIndex]) {
                        // The 'R' robot dies, 'L' robot loses 1 health
                        healths[currentIndex] -= 1;
                        healths[topIndex] = 0;
                        stack.pop();
                    } else if (healths[topIndex] > healths[currentIndex]) {
                        // The 'L' robot dies, 'R' robot loses 1 health
                        healths[topIndex] -= 1;
                        healths[currentIndex] = 0;
                        // 'L' robot is dead, no need to check further collisions for it
                    } else {
                        // Both robots have the same health and destroy each other
                        healths[currentIndex] = 0;
                        healths[topIndex] = 0;
                        stack.pop();
                    }
                }
            }
        }

        // Step 4: Collect surviving robots in original order
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (healths[i] > 0) {
                result.add(healths[i]);
            }
        }
        
        return result;
    }
}