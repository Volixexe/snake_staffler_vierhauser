# htlsnake

Algorithm Idea:

```
appleCount

appleCount % 2 == 1 ? appleCount + 3 : appleCount + 2

gird = appleCount/2

// count 5 -> 8 => grid 4x4
// count 4 -> 6 => grid 3x3
// count 3 -> 6 => grid 3x3
// count 2 -> 4 => grid 2x2
// count 1 -> 4 => grid 2x2

grid[x][y]

ouucipied = -1

// is ouucipied
grid[x1][y1] = -1

// add Apple
grid[x2][y2] += 1

// hit apple

grid[x2][y2] -= 1

//Returns gird with lowest 
getLowets()
```
