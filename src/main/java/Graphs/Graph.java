package Graphs;




import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

class PriorityQueue {
    public ArrayList<Pairs<Integer, Integer>> heap;



    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public void add(Pairs p) {
        heap.add(p);
        upHeapify(heap.size() - 1);
    }


    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void upHeapify(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && heap.get(index).getSecond().compareTo(heap.get(parentIndex).getSecond()) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }


    private void swap(int i, int j) {
        Pairs temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

}
 class Pairs<User,Linkedlist> {
    private User first;
    private Linkedlist second;

    public Pairs(User first, Linkedlist second) {
        this.first = first;
        this.second = second;
    }

    public User getFirst() {
        return first;
    }

    public void setFirst(User first) {
        this.first = first;
    }

    public Linkedlist getSecond() {
        return second;
    }

    public void setSecond(Linkedlist second) {
        this.second = second;
    }
}
class Comment{
    String text;
    int noOfLikes;
    int commentor;
    int commentId;

    int replies;

    public Comment(String text,int commentor){
        this.noOfLikes=0;
        this.replies=0;
        this.text=text;

    }
    public void setCommentId(int len){
        this.commentId=len+1;

    }

}
class Post{
    int noOfLikes;
    String category;
    String content;
    ArrayList<User> likes;
    int postId;
    int noOfComments;

    ArrayList<Comment> com;
    Post(String category,String content){
        this.noOfLikes=0;
        this.category=category;
        this.content=content;
        this.likes=new ArrayList<User>();
        this.com=new ArrayList<>();
        this.noOfComments=0;


    }
    public void likePost(User obj){
        this.noOfLikes++;
        this.likes.add(obj);


    }

    public void setPostId(int len){
        postId=len+1;

    }

}
class User{
    public int uid;
//    String userName;
    public String bio;
    public String userName;
    public int noOfFollowers;
    public int noOfFollowing;
    public int noOfPosts;
    String accType;
    String date;
    public ArrayList<Post> posts;
    String location;
    private User next;
    private String pwd;
    User(){
        next=null;
    }
    User(int uid,String userName,String bio,String accType,String Location){
        this.userName=userName;
        this.uid=uid;
        this.bio=bio;
        this.accType=accType;
        this.location=location;
        this.posts=new ArrayList<>();
    }
    public void addPost(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter content of the post");
        String con=sc.nextLine();
        System.out.println("Enter category");
        String category=sc.nextLine();
        Post p=new Post(category,con);


        posts.add(p);
        p.setPostId(posts.size());
        this.noOfPosts++;

    }
    public int getlen(ArrayList<Post> p){
        return p.size();
    }




    public Post getPosts(int idx){
        return this.posts.get(idx-1);
    }
    public void setNext(User acc){
        this.next=acc;

    }
    public User getNext(){
        return this.next;
    }


}
class Linkedlist{
    private User head;
    private User next;

    Linkedlist(){
        head=null;
    }

    public User getHead(){
        return this.head;
    }
    public void addAtHead(User obj){
        obj.setNext(head);
        head=obj;

    }


}
class graph {
    Scanner sc = new Scanner(System.in);
    //no attributes as such
    int vertices;
    ArrayList<Pairs<User, Linkedlist>> al;


    public int getNonNull(ArrayList<Pairs<User, Linkedlist>> al){
        int i=0;
        for(i=0;i<al.size();i++){
            if(al.get(i)==null) break;
        }
        return i;
    }
    public void createAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter userName");
        String userName = sc.nextLine();
        System.out.println("Enter bio");
        String bio = sc.nextLine();
        System.out.println("Enter location ");
        String location = sc.nextLine();
        System.out.println("Enter account type");
        String acctype = sc.nextLine();
        User u = new User(getNonNull(al), userName, bio, acctype, location);
        Pairs<User, Linkedlist> p = new Pairs<>(u, new Linkedlist());


        al.add(p);


    }

    public void likePost(int ruid) {
        System.out.println("Enter user whose post you want to like");
        int uid = sc.nextInt();
        System.out.println("Enter post id of post that you want to like");
        int postidx = sc.nextInt();
        al.get(uid).getFirst().getPosts(postidx).noOfLikes++;
        al.get(uid).getFirst().getPosts(postidx).likes.add(al.get(ruid).getFirst());


    }

    public void addComment(int ruid) {
        System.out.println("Enter user on whose post you want to comment");
        int uid = sc.nextInt();
        System.out.println("Enter post id of post that you want to comment on");
        int postidx = sc.nextInt();
        System.out.println("Enter text of comment");
        String con = sc.next();
        Comment c = new Comment(con, ruid);
        c.setCommentId(al.get(uid).getFirst().posts.size());
        al.get(uid).getFirst().getPosts(postidx).com.add(c);
        al.get(uid).getFirst().getPosts(postidx).noOfComments++;


    }

    public void updateConnections(int uid, ArrayList<Pairs<Integer, Integer>> all) {
        Linkedlist ll = al.get(uid).getSecond();
        User temp = ll.getHead();
        while (temp != null) {
            User obj = temp;
            all.get(obj.uid).setFirst(obj.uid);
            all.get(obj.uid).setSecond(all.get(obj.uid).getSecond() + 1);

            temp.setNext(temp.getNext());
        }
    }

    public User getMutuals(int uid) {
        PriorityQueue p = new PriorityQueue();
        Linkedlist ll = al.get(uid).getSecond();
        User temp = ll.getHead();
        ArrayList<Pairs<Integer, Integer>> all = new ArrayList<>(vertices);
        while (temp != null) {
            updateConnections(temp.uid, all);
            temp.setNext(temp.getNext());

        }
        for (int i = 0; i < all.size(); i++) {
            p.heap.add(all.get(i));

        }
        int ansid = p.heap.get(0).getFirst();
        return al.get(ansid).getFirst();

    }

    public void likeComment(int ruid) {
        System.out.println("Enter user on whose post you want to interact with ");
        int uid = sc.nextInt();
        System.out.println("Enter post id of post whose comment you want to interact with");
        int postidx = sc.nextInt();
        System.out.println("Enter commentid");
        int cid = sc.nextInt();
        al.get(uid).getFirst().getPosts(postidx).com.get(cid-1).noOfLikes++;


    }

    public void createAdjacencyList() {
        //int vertices,edges;
        System.out.println("Enter number of users");
        this.vertices = sc.nextInt();
        System.out.println("Enter number of Connections");
        int edges = sc.nextInt();
        System.out.println("Do you want path to be bidirectional?");
        boolean flag = sc.nextBoolean();

        al = new ArrayList<Pairs<User, Linkedlist>>();
        //each cell will have null so,
//        for (int i = 0; i < vertices; i++) {
//            al.get(i).setSecond(new Linkedlist());//every cell will refer to empty LL ab
//        }


    }


    public void sendRequest(int sid, int ruid) {
        if (acceptRequest(sid, ruid)) {
            al.get(sid).getSecond().addAtHead(al.get(ruid).getFirst());
            al.get(sid).getFirst().noOfFollowing++;
            al.get(ruid).getFirst().noOfFollowers++;
        } else {
            System.out.println("Request not accepted");
        }

    }

    public boolean acceptRequest(int sid, int rid) {
        if (al.get(rid).getFirst().accType.equals("Public")) return true;
        System.out.println("Do you want to accept the request sent by" + al.get(sid).getFirst().userName);
        Scanner sc = new Scanner(System.in);
        boolean ans = sc.nextBoolean();
        return ans;

    }
    public void addPost(){
        System.out.println("Enter acc where post is to be added");
        int uid=sc.nextInt();
        al.get(uid).getFirst().addPost();
    }

    public static void main(String[] args) {
       graph g=new graph();

       g.createAdjacencyList();
       g.createAccount();
       g.createAccount();
       g.createAccount();
       g.sendRequest(0,1);
       g.addPost();
       g.likePost(0);
       g.addComment(0);
       g.likeComment(0);
        System.out.println(g.getMutuals(0).uid);;


    }

}
