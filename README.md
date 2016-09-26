# Princeton-Algorithms-assignment-3-Pattern-Recognition
Two ways to solve the collinear points problem. Here shows the problems I met when doing this homework.  
Score: 99/100

## Point.java
* **Interface: comparator**
```java
public int compareTo(Point that) 
```
This API is use to override the defaut comparator. When you call the `sort()` function, the function will search for this comparator. However, we need another comparator to implement the sort over points' slop. As a result, the assignment requires another function API:
```java
public Comparator<Point> slopeOrder() 
```
This function API is different from the code in the course slide. This function return a new kind of comparator. When we want to implement the sorting of slope in the `FastCollinearPoint.java`, we code like this:
```java
Arrays.sort(copyPoints, point.slopeOrder());
```
Here is the full code of the second comparator:
```java
public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point po1, Point po2) {
                double slope1 = slopeTo(po1);
                double slope2 = slopeTo(po2);
                if (slope1 == slope2) return 0;
                if (slope1 < slope2) return -1;
                return 1;
            }
        };
    }
```
* **How to change the size of arrays and How to return the array properly**  
Because of the fixed API definition, I simply use an `ArrayList<T>` and `ArrayList.toArray` to solve the first problem.
```java
ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();  
lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);
```
I use `clone()` to return the array. But I still have bugs in the Test 10 of `BruteCollinearPoints.java`.  
## FastCollinearPoints.java
* **How to find only unique line segments**  
When I first met the duplicate line segments bug, I find all the way to delete the duplicate line segment. This solution mainly compares the duplicate line segments to the existed line segment. I read lots of people at the course discussion group using hash or extra memory to complete this function. However, we should think of the problem from another aspect.  
We shoud not let the duplicate line segment be selected. The duplicate line segment happens at the begin and end point of one line. SO if we just let one point be selected and the other one fails. Then we don't have to delete the other point. I add just one constraint to the selection of line segment:
```java
if (count >= 3) {
    Arrays.sort(copyPoints, i - count, i);
    if (point.compareTo(copyPoints[i - count]) < 0)
        storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
```
We simply constraint that only if the current iterator point is the smallest point of the line segment, we put the line segment into the arraylist.
