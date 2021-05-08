/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-03-28 19:17
 * @packagename PACKAGE_NAME
 */
public class ListNodeTest {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode1=new ListNode(2);
        ListNode listNode2=new ListNode(3);
        ListNode listNode3=new ListNode(4);

        listNode2.next=listNode3;
        listNode1.next=listNode2;
        listNode.next=listNode1;


        print(listNode);
        reverse(listNode);
        print(listNode3);
    }

    static ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ret = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return ret;
    }

    static void print(ListNode head){
        if (head == null || head.next == null) {
            System.out.println(head.val);
        }else {
            print(head.next);
            System.out.println(head.val);
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
