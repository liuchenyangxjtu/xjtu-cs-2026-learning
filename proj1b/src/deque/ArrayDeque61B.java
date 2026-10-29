package deque;

import java.lang.Math;

import java.util.ArrayList;
import java.util.List;
// 0 0 0 0 0 0 0 0
// 0 1 2 3 4 5 6 7
//-8-7-6-5-4-3-2-1
//0 1 2  3 4 5 6
//-3-2-1 0 1 2 3


/*不变式：
  1. 队首元素位于 sizeminus + 1，队尾元素位于 sizeplus - 1；
  2. 元素个数 = sizeplus - sizeminus - 1；
  3. 所有真实下标都通过 floorMod(i, numbers) 映射到 [0, numbers)，*/


public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] items;//初始数组，长度记为 numbers（初始 8）
    private int numbers=8;
    private int sizeplus=0;
    private int sizeminus=-1;

    public ArrayDeque61B() {
        items =(T[]) new Object[numbers];
    }

    @Override
    public void addFirst(T x) {
        if (sizeplus-sizeminus-1<numbers){// 情况一：数组没满
            items[Math.floorMod(sizeminus,numbers)]=x;//sizeminus 指向"队首前的一格"，直接写这一格，再把指针往前移一格
            sizeminus--;
        }else {
            resizingup();//情况二：数组已满，先扩容
            sizeminus = -2;//把现有元素搬到两倍大的新数组（元素被摆到 0..numbers-1）
            sizeplus = numbers;//扩容后重新设置前后指针
            numbers = numbers*2;
            items[Math.floorMod(-1,numbers)] = x;
        }
    }

    @Override
    public void addLast(T x) {
        if (sizeplus - sizeminus - 1 < numbers) {
            items[Math.floorMod(sizeplus,numbers)]=x;
            sizeplus++;
        }else {
            resizingup();
            sizeminus = -1;
            sizeplus = numbers+1;
            items[numbers] = x;
            numbers = numbers*2;
        }

    }

    // 遍历方式：从队首的下一格开始，一直到 sizeplus 之前，
    // 每一步都用 floorMod 映射回真实下标。

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
       for(int i =sizeminus+1;i <sizeplus;i++){
           returnList.add(items[Math.floorMod(i,numbers)]);
       }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sizeplus-sizeminus-1 == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int size() {
        return sizeplus-sizeminus-1;
    }

    @Override
    public T removeFirst() {
        if (sizeminus<-1) {  // 队首绕到了数组尾部，下标要用 floorMod 处理
            T a = items[Math.floorMod(sizeminus + 1, numbers)];
            items[Math.floorMod(sizeminus + 1, numbers)] = null;//做空指针
            sizeminus++;
            resizingdown();
            return a;
        }else if (sizeplus>sizeminus+1){// 队首在普通下标上，没有从队尾循环
            T a =items[sizeminus+1];
            items[sizeminus+1]=null;
            sizeminus++;
            resizingdown();
            return a;
        }else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (sizeplus>0){
            T a =items[sizeplus-1];
            items[sizeplus-1]=null;
            sizeplus--;
            resizingdown();
            return a;
        }else if (sizeminus<sizeplus-1){
            T a =items[Math.floorMod(sizeplus-1,numbers)];
            items[Math.floorMod(sizeplus-1,numbers)] = null;
            sizeplus--;
            resizingdown();
            return a;
        }else {
            return null;
        }
    }

    @Override
    public T get(int index) {
       if (index<0||index>sizeplus-sizeminus-2){
           return null;
       }else {
           return items[Math.floorMod(sizeminus+index+1,numbers)];
       }//用floorMod映射到真实下标
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resizingup(){
        T [] a = (T[]) new Object[numbers*2];//数组变大2倍
        for (int i = 0;i < numbers;i++){
            a[i] = get(i);//遍历数组
        }
        items = a;//赋予
    }

    private void resizingdown(){
        if (numbers > 8 && sizeplus-sizeminus-1 <= numbers/4){//缩容条件
            T [] a = (T[]) new Object[numbers/4];//缩小数组
            for (int i = 0;i < sizeplus-sizeminus-1;i++){
                a[i] = get(i);//遍历数组
            }
            items = a;
            sizeplus = sizeplus-sizeminus-1;
            sizeminus = -1;
            numbers/=4;
        }
    }
}
