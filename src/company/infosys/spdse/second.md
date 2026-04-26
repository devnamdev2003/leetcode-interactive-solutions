### Coding Hands-on - Question 2

You are given a string S of length N, consisting of lowercase English letters. Alongside the string, you are provided with an array of integers w, also of length N, where w[i] represents the weight of the character S[i].

Your task is to find a subsequence of S that is a palindrome and has the maximum possible total weight. The weight of a subsequence is the sum of the weights of all characters chosen for that subsequence.

A subsequence is formed by deleting zero or more characters from the original string. A palindrome is a sequence of characters that reads the same forwards and backwards.

Find the maximum total weight of a palindromic subsequence.

---

### Input Format

The first line contains an integer, N, denoting the length of the string.

The second line contains the string S.

Each line i of the N subsequent lines (where 0 ≤ i < N) contains an integer, w[i].

---

### Constraints

1 <= N <= 1000
N <= |S| <= N
0 <= w[i] <= 10^5

---

### Sample Test Cases

#### Case 1

Input:

```
3
aba
1
10
1
```

Output:

```
12
```

Explanation:
The entire string "aba" is a palindrome. Selecting all characters yields a total weight of 1 + 10 + 1 = 12, which is the maximum possible.