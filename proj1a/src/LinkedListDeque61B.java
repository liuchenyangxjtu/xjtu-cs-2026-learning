import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B <T> implements Deque61B<T> {
    private class Node{
        public  Node prev;//指向前一个节点
        public T item;//当前的值
        public Node next;//智轩后一个节点
        public Node(Node a,T b,Node c){
            this.prev =a;
            this.item =b;
            this.next =c;
        }
    }
    //定义一个节点类
    /*设计构造函数*/
    public int size =0;
    Node sentinel = new Node(null,null,null);
    public LinkedListDeque61B(){
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        //前后都指向本身，构成循环
    }

    @Override
    public void addFirst(T x) {
        Node old =sentinel.next;
        Node add =new Node(sentinel,x,old);
        old.prev =add;
        sentinel.next =add;
        size++;
        /*出现新节点add，它前边是哨兵，后边是原来的哨兵的下一个节点，由此定义出add，并保存Old，最后将他们连接*/
    }

    @Override
    public void addLast(T x) {
        Node old =sentinel.prev;
        Node add =new Node(old,x,sentinel);
        old.next =add;
        sentinel.prev =add;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T>returnList = new ArrayList<>();
        Node p =sentinel;
        while (p.next.item!=null){//我的错误：p.next到哨兵时不是null,p.next.item才是！
            returnList.add(p.next.item);
            p=p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size==0) {
            return true;
        } else {
            return false;
        }
    }
/*用size判断是否含有*/
    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next==sentinel){
            return null;
        }
        T a =sentinel.next.item;
        Node first =sentinel.next;
        sentinel.next =sentinel.next.next;
        sentinel.next.prev=sentinel;//上一步已经让第二节变成了第一节，所以此时只需要一个next
        first.next=null;//删除指针
        first.prev=null;//删除指针
        size--;
        return a;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel){
            return null;
        }
        T a =sentinel.prev.item;
        Node lastest =sentinel.prev;
        sentinel.prev=sentinel.prev.prev;
        sentinel.prev.next=sentinel;
        size--;
        lastest.prev=null;//删除指针
        lastest.next=null;//删除指针
        return a;
    }

    @Override
    public T get(int index) {
        if (index<0||index>=size){//边界条件
        return null;
        }
        Node p=sentinel.next;
        for (int i=0;i<index;i++){
            p=p.next;//正常迭代
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        Node p =sentinel.next;
        if (index<0||index>size){
            return null;
        }else {
            return getRecursive2(p,index);//需要一个另外的函数让递归成立，因为p无法调用原函数
        }
    }
    public T getRecursive2(Node p,int index){
        if (index==0){
            return p.item;
        }else return getRecursive2(p.next,index-1);//正常递归
    }
}
