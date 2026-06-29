import java.util.*;

public class Dev {
    public static void main(String[] args) {
        Solution o = new Solution();
        int[][] maze = { { 1, 0, 0, 0 }, { 1, 1, 0, 1 }, { 1, 1, 0, 0 }, { 0, 1, 1, 1 } };
        System.out.println(o.ratInMaze(maze));
    }

}

class Solution {
    public ArrayList<String> ratInMaze(int[][] maze) {
        ArrayList<String> ans = new ArrayList<>();
        fun(maze, new int[maze.length][maze.length], new StringBuilder(), ans, 0, 0);
        return ans;
    }

    public void fun(int[][] maze, int[][] visited, StringBuilder s, List<String> ans, int i, int j) {
        int n = maze.length - 1;
        if (i == n && j == n) {
            ans.add(new String(s.toString()));
            return;
        }
        String move = "DLRT";
        for (char c : move.toCharArray()) {
            if (isValid(maze, i, j, c, visited)) {
                switch (c) {
                    case 'D':
                        visited[i][j] = 1;
                        s.append("D");
                        fun(maze, visited, s, ans, i + 1, j);
                        visited[i + 1][j] = 0;
                        s.deleteCharAt(s.length() - 1);
                        break;
                    case 'L':
                        visited[i][j] = 1;
                        s.append("L");
                        fun(maze, visited, s, ans, i, j - 1);
                        visited[i][j - 1] = 0;
                        s.deleteCharAt(s.length() - 1);
                        break;
                    case 'R':
                        visited[i][j] = 1;
                        s.append("R");
                        fun(maze, visited, s, ans, i, j + 1);
                        visited[i][j + 1] = 0;
                        s.deleteCharAt(s.length() - 1);
                        break;
                    case 'T':
                        visited[i][j] = 1;
                        s.append("T");
                        fun(maze, visited, s, ans, i - 1, j);
                        visited[i - 1][j] = 0;
                        s.deleteCharAt(s.length() - 1);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    public boolean isValid(int[][] maze, int i, int j, char c, int[][] visited) {
        switch (c) {
            case 'D':
                i = i + 1;
                if (i < maze.length && maze[i][j] == 1 && visited[i][j] != 1)
                    return true;
                break;
            case 'L':
                j = j - 1;
                if (j >= 0 && maze[i][j] == 1 && visited[i][j] != 1)
                    return true;
                break;
            case 'R':
                j = j + 1;
                if (j < maze.length && maze[i][j] == 1 && visited[i][j] != 1)
                    return true;
                break;
            case 'T':
                i = i - 1;
                if (i >= 0 && maze[i][j] == 1 && visited[i][j] != 1)
                    return true;
                break;
            default:
                break;
        }
        return false;
    }
}
