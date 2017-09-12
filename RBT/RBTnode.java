/**
 * Created by Universe_Lu on 3/15/16.
 */
public class RBTnode{

    int ID;
    int count;
    Color color;

    RBTnode leftchild;
    RBTnode rightchild;
    RBTnode parent;

    RBTnode(int ID, int count) {
        this.ID = ID;
        this.count = count;
        this.color = color.red;
    }

    RBTnode uncle(RBTnode node) {
        if (node != null && node.parent != null && node.parent.parent != null) {
            if (node.parent == node.parent.parent.rightchild) {
                return node.parent.parent.leftchild;
            } else {
                return node.parent.parent.rightchild;
            }
        } else {
            return null;
        }
    }

    RBTnode grandparent(RBTnode node) {
        if (node != null && node.parent != null && node.parent.parent != null) {
            return node.parent.parent;
        } else {
            return null;
        }
    }

    RBTnode Right_Minimum(RBTnode node) {
        RBTnode Right_Min = null;
        if (node != null) {
            Right_Min = node.rightchild;
            while (Right_Min != null && Right_Min.leftchild != null) {
                Right_Min = Right_Min.leftchild;
            }
        }
        return Right_Min;
    }

    RBTnode Left_Maxmum(RBTnode node){
        RBTnode Left_Max = null;
        if(node != null){
            Left_Max = node.leftchild;
            while (Left_Max != null && Left_Max.rightchild != null){
                Left_Max = Left_Max.rightchild;
            }
        }
        return Left_Max;
    }

    RBTnode sibling(RBTnode node) {
        RBTnode sibling = null;
        if (node != null && node.parent != null) {
            if (node == node.parent.rightchild) {
                sibling = node.parent.leftchild;
                return sibling;
            } else if (node == node.parent.leftchild) {
                sibling = node.parent.rightchild;
                return sibling;
            } else return null;
        } else
            return null;
    }

    RBTnode tree_successor(RBTnode node){
        //RBTnode successor;
        if (node.rightchild != null){
            RBTnode successor = node.Right_Minimum(node);
            return successor;
        }else{
            RBTnode temp = node.parent;
            while( temp != null && node== temp.rightchild){
                node = temp;
                temp = temp.parent;
            }
            return temp;
        }
    }


    RBTnode tree_predecessor(RBTnode node){
        if (node.leftchild != null){
            RBTnode predecessor = node.Left_Maxmum(node);
            return  predecessor;
        } else {
            RBTnode temp = node.parent;
            while(temp!= null && node == temp.leftchild){                 // when node.parent = null or node is temp's right child
                node = temp;                                              // we find the predecessor of the node, it's temp
                temp = temp.parent;
            }
            return temp;
        }
    }

}
enum Color{
    red, black
}