### Coding Hands-on - Question 1

In satellite signal processing, a stream of N data packets is received. Each packet i is classified into a category A[i] (ranging from 0 to C-1) and carries a specific signal weight W[i] representing its data density.

A monitoring window [l, r] (the contiguous block of packets from index l to r) is considered frequency-balanced if there is at least one category that appears in strictly more than half of the packets in that window. In other words, some category is a strict majority in the window.

Find the maximum total weight (the sum of W[i] for all packets in the window) over all possible frequency-balanced windows.

---

### Input Format

The first line contains a integer, N, denoting the total number of data packets.

The second line contains a integer, C, denoting the total number of possible categories.

Each line i of the N subsequent lines (where 0 ≤ i < N) contains a integer, A[i].

Each line i of the N subsequent lines (where 0 ≤ i < N) contains a integer, W[i].

---

### Constraints

1 <= N <= 10^5
1 <= C <= 10^5
-10^9 <= A[i] <= 10^9
-10^9 <= W[i] <= 10^9

---

### Sample Test Cases

#### Case 1

Input:

```
3
3
1
2
1
10
10
10
```

Output:

```
30
```

Explanation:
Selecting the entire window of three packets allows category 1 to appear twice to form a strict majority, yielding a total accumulated weight of 30.

---

#### Case 2

Input:

```
3
4
1
2
3
100
100
100
```

Output:

```
100
```

Explanation:
With completely unique categories of 1, 2, and 3, no category can achieve a strict majority in any window larger than a single packet, making the optimal choice any individual packet with its maximum weight of 100.

---

#### Case 3

Input:

```
10
5
0
1
2
3
4
0
1
2
3
4
10
10
10
10
10
10
10
10
10
10
```

Output:

```
10
```

Explanation:
Because matching categories are spaced widely apart, no category appears frequently enough to exceed half the length of any multiple packet window, restricting the selection to a single packet and capping the maximum weight at 10.
