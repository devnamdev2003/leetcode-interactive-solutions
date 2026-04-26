### Coding Hands-on - Question 3

A linear sequence of N positions is defined, where each position i (from 1 to N) contains an integer value V[i].

Two robots, A and B, are initially placed at the extreme ends of the sequence. Robot A starts at position 1, and Robot B starts at position N. Upon being placed, both robots immediately collect the values at their respective starting positions.

The robots operate in alternating turns, with Robot A always taking the first turn. On its turn, the active robot must choose to move toward the opponent's current position by a distance of either 1 or 2 steps. If a robot chooses to move a distance of 1 to a new cell, it collects the value contained in that cell. If a robot chooses to move a distance of 2, it bypasses the intermediate cell and only collects the value at its final destination.

The game concludes immediately when a robot’s chosen move would cause it to occupy or pass the current position of its opponent. In this final, game-ending move, the moving robot does not collect any additional points, regardless of the value at the destination.

Robot A acts to maximize its own total score. Robot B, on the other hand, acts to minimize Robot A's total score.

Find the maximum total score Robot A can guarantee for itself, assuming Robot B plays optimally to minimize Robot A's score.

#### Notes:

* If a robot’s move results in position ≥ opponent’s position, the game ends and no value is collected.
* Only Robot A’s score is considered in the final result.

---

### Input Format

The first line contains an integer, N, denoting the number of positions in the sequence.

Each line i of the N subsequent lines (where 0 ≤ i < N) contains an integer, V[i].

---

### Constraints

1 <= N <= 500
-10^9 <= V[i] <= 10^9

---

### Sample Test Cases

#### Case 1

Input:

```
5
10
2
100
3
20
```

Output:

```
110
```

Explanation:
Robot A starts by collecting 10. On its first turn, A moves two steps to position 3, collecting 100 for a total of 110. Robot B cannot make any subsequent move that would reduce A's score, as any optimal counter by B would either end the game or allow A to maintain its score.

---

#### Case 2

Input:

```
7
1
10
-5
20
-10
50
2
```

Output:

```
31
```

Explanation:
Robot A starts by collecting 1. A then moves one step to position 2, collecting 10 for a total of 11. Robot B moves to position 6, collecting 50. A then moves two steps to position 4, collecting 20 for a total of 31, at which point B can only end the game without further reducing A's score.

---

#### Case 3

Input:

```
6
100
1
1000
1
1
50
```

Output:

```
1101
```

Explanation:
Robot A starts by collecting 100. A then moves one step to position 2, collecting 1, for a total of 101. Robot B moves one step to position 5, collecting 1. A then moves one step to position 3, collecting 1000 for a total of 1101, which B cannot prevent.