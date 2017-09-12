/**
 * Created by Universe_Lu on 3/15/16.
 */
public class RBtree {
     RBTnode root;
    // insert node to the binary search tree
    void insertNode(int ID, int count){
        RBTnode newNode = new RBTnode(ID, count);
        if (root == null){                                                        // newNode is the root node
            newNode.color = Color.black;
            root = newNode;
        }
        else{
            RBTnode parent = null;                                                // newNode is not the root node
            RBTnode temproot = root;
            while(temproot != null){
                parent = temproot;
                if (ID < temproot.ID){
                    temproot = temproot.leftchild;
                } else{
                    temproot = temproot.rightchild;
                }
            }
            //newNode.parent = parent;
            if (ID < parent.ID){
                parent.leftchild = newNode;
            } else if(ID > parent.ID){
                parent.rightchild = newNode;
            } else{
                //System.out.println("Error, the node has been inserted." );
            }
            newNode.parent = parent;
        }
        newNode.leftchild  = null;
        newNode.rightchild  = null;
        setNode1(newNode);                                                          // set the node to be redblack tree node
       //if (root != null) printtest2(root, root.ID, 0);
    }

    // Caes1  the first node inserted, and make it black
    void setNode1(RBTnode node){
        if (node == null){                                                          // Null node
            //System.out.println("setNode1: It's a null node." );
        } else{
           if (node.parent == null){                                               // First node, set the node root, make it black
               node.color = Color.black;
               //System.out.println("setNode1: It's the first node" + ", ID:" + node.ID + ", color:" + node.color);
           } else {
               if (node.parent.color== Color.black) {                              // Node's parent is black node. Done!
                   //System.out.println("setNode1: Node's parent is black node. Done!" + " ID:" + node.ID + ", color:" + node.color);
               } else {
                   setNode2(node);                                                  // Node's parent is RED, Node is at least Third level node
               }
           }
        }
    }

    // case2  XYr: uncle is red node
    void setNode2(RBTnode node){
        RBTnode uncle = node.uncle(node);
        if ( uncle != null&&uncle.color == Color.red) {                           // parent is red node, uncle is red node

            //System.out.println("setNode2: Insert the node" + ", ID:" + node.ID + ", color:" + node.color);
           // System.out.println("parent is red , still need re-balance");
            node.parent.color = Color.black;
           // System.out.println("setNode2: change the parent color" + ", ID:" + node.parent.ID + ", color:" + node.parent.color);
            uncle.color = Color.black;
           // System.out.println("setNode2: change the uncle color" + ", ID:" + uncle.ID + ", color:" + uncle.color);
            RBTnode grandparent = node.grandparent(node);
            grandparent.color = Color.red;
            setNode1(grandparent);                                               //  grandparent may be not red-black node again
                                                                                 //  re-balance grandparent
        } else{
            setNode3(node);                                                      // parent is red, uncle is black or null
        }

    }

    // case3  LLb,RRb,LRb,RLb: uncle is null or black
    void setNode3(RBTnode node){
        RBTnode grandparent = node.grandparent(node);
        RBTnode parent = node.parent;
        // LRb case,  LR rotation
               if(node == parent.rightchild && parent== grandparent.leftchild){
                   node.color = Color.black;
                   grandparent.color = Color.red;
                  // System.out.println("setNode3: grandparent" + ", ID:" + grandparent.ID + ", color:" + grandparent.color);
                   //System.out.println("setNode3: parent" + ", ID:" + parent.ID + ", color:" + parent.color);
                   LRrotation(node, parent, grandparent);
                   //System.out.println("setNode3: Insert the node" + ", ID:" + node.ID + ", color:" + node.color);
               }
        //RLr case, RL rotation
               if(node == parent.leftchild && parent == grandparent.rightchild){
                   node.color = Color.black;
                   grandparent.color = Color.red;
                  // System.out.println("setNode3: grandparent" + ", ID:" + grandparent.ID + ", color:" + grandparent.color);
                  // System.out.println("setNode3: parent" + ", ID:" + parent.ID + ", color:" + parent.color);
                   RLrotation(node, parent, grandparent);
                   //System.out.println("setNode3: Insert the node" + ", ID:" + node.ID + ", color:" + node.color);
               }
        //LLr case, LL rotation
               if(node == parent.leftchild && parent == grandparent.leftchild){
                   parent.color = Color.black;
                   grandparent.color = Color.red;
                  // System.out.println("setNode3: grandparent" + ", ID:" + grandparent.ID + ", color:" + grandparent.color);
                   //System.out.println("setNode3: parent" + ", ID:" + parent.ID + ", color:" + parent.color);
                   LLrotation(parent, grandparent);
                   //System.out.println("setNode3: Insert the node" + ", ID:" + node.ID + ", color:" + node.color);
               }
        //RRr case, RR rotation
               if(node == parent.rightchild && parent == grandparent.rightchild){
                   parent.color = Color.black;
                   grandparent.color = Color.red;
                   //System.out.println("setNode3: grandparent" + ", ID:" + grandparent.ID + ", color:" + grandparent.color);
                   //System.out.println("setNode3: parent" + ", ID:" + parent.ID + ", color:" + parent.color);
                   RRrotation(parent, grandparent);
                   //System.out.println("setNode3: Insert the node" + ", ID:" + node.ID + ", color:" + node.color);
               }
    }

    /*
    LL rotation
     */
    void LLrotation(RBTnode pnode, RBTnode gpnode){
        gpnode.leftchild = pnode.rightchild;
        if(pnode.rightchild != null){
            pnode.rightchild.parent = gpnode;
        }
        pnode.parent = gpnode.parent;
        if(pnode.parent == null)  root = pnode;
        else if (gpnode == gpnode.parent.leftchild) gpnode.parent.leftchild = pnode;
        else gpnode.parent.rightchild = pnode;
        pnode.rightchild = gpnode;
        gpnode.parent =pnode;
    }

    /*
    RR rotation
     */
    void RRrotation(RBTnode pnode, RBTnode gpnode){
        gpnode.rightchild = pnode.leftchild;
        if(pnode.leftchild!= null){
            pnode.leftchild.parent=gpnode;
        }
        pnode.parent = gpnode.parent;
        if(pnode.parent == null)  root = pnode;
        else if (gpnode == gpnode.parent.leftchild) gpnode.parent.leftchild = pnode;
               else gpnode.parent.rightchild = pnode;
        pnode.leftchild = gpnode;
        gpnode.parent =pnode;
    }

    /*
    RL rotation
     */
    void RLrotation(RBTnode node, RBTnode pnode, RBTnode gpnode){
        pnode.parent = node;
        pnode.leftchild = node.rightchild;
        if (node.rightchild!= null){
            node.rightchild.parent = pnode;
        }
        node.rightchild = pnode;
        gpnode.rightchild = node.leftchild;
        if(node.leftchild != null){
            node.leftchild.parent = gpnode;
        }
        node.leftchild = gpnode;

        node.parent = gpnode.parent;
        gpnode.parent = node;
        if (node.parent!= null) {
            if (gpnode == node.parent.leftchild) node.parent.leftchild = node;
            if (gpnode == node.parent.rightchild) node.parent.rightchild = node;
        } else
         root = node;
    }

    /*
    LR rotation
     */
    void LRrotation(RBTnode node, RBTnode pnode, RBTnode gpnode){
        pnode.parent = node;
        pnode.rightchild = node.leftchild;
        if (node.leftchild!= null){
            node.leftchild.parent = pnode;
        }
        node.leftchild = pnode;
        gpnode.leftchild = node.rightchild;
        if(node.rightchild != null){
            node.rightchild.parent = gpnode;
        }
        node.rightchild = gpnode;

        node.parent = gpnode.parent;
        gpnode.parent = node;
        if (node.parent!= null) {
            if (gpnode == node.parent.leftchild) node.parent.leftchild = node;
            if (gpnode == node.parent.rightchild) node.parent.rightchild = node;
        } else
            root = node;
    }

    /*
     * Binary search tree: finding the node with the given ID.
     */
    RBTnode find(int ID) {
        if (root != null) {
            RBTnode tempnode = root;                                           // First find the node to delete
            while (tempnode != null && tempnode.ID != ID) {
                if (ID < tempnode.ID) {
                    tempnode = tempnode.leftchild;
                } else {
                    tempnode = tempnode.rightchild;
                }
            }
            if (tempnode != null) {                                           // node isn't null implies we've found the node
                //System.out.println("We have found the node, ID:" +tempnode.ID + ", color:"+ tempnode.color );
                return tempnode;
            } else {
               // System.out.println("The node dose not exist.");
                return null;
            }
        } else {
            //System.out.println("The tree dose not exist.");
            return null;
        }
    }

    /*
     * Binary search tree: finding the node with the given ID.
     * If ID is in the Tree , return the node; if the ID is not in the tree, return the ID's parent in the tree
     */
    RBTnode findLost(int ID) {
        if (root != null) {
            RBTnode tempnode = root;                                     // First find the node to delete
            RBTnode tempparent= null;
            while (tempnode != null && tempnode.ID != ID) {
                if (ID < tempnode.ID) {
                    tempparent = tempnode;
                    tempnode = tempnode.leftchild;
                } else {
                    tempparent = tempnode;
                    tempnode = tempnode.rightchild;
                }
            }
            if (tempnode != null) {                                     // node isn't null implies we've found the node
                //System.out.println("We have found the node, ID:" +tempnode.ID + ", color:"+ tempnode.color );
                return tempnode;
            } else {                                                    // The node dose not exist, found the parent of that nun-exist node
                //System.out.println("The node dose not exist, found the parent of that nun-exist node");
                return tempparent;
            }
        } else {
            //System.out.println("The tree dose not exist.");
            return null;
        }
    }


    /*
     Delete the finding node
     */
    void deleteNode(RBTnode node){
        if (node != null){
            if (node.leftchild == null && node.rightchild == null) {          //  node having no children
                if (node.color == Color.black) {                              // and this node is black
                    if (node.parent != null) {
                        RBTnode leafNode;
                        if (node == node.parent.leftchild) {
                            leafNode = new RBTnode(-1, -1);                       // add a dummy node
                            leafNode.color = Color.black;
                            node.parent.leftchild = leafNode;
                            leafNode.parent = node.parent;
                            setDelete1(leafNode);
                            leafNode.parent.leftchild = null;                    // delete the leaf node

                        } else {
                            leafNode = new RBTnode(-1, -1);                        // add a dummy node
                            leafNode.color = Color.black;
                            node.parent.rightchild = leafNode;
                            leafNode.parent = node.parent;
                            setDelete1(leafNode);
                            leafNode.parent.rightchild = null;                    // delete the leaf node

                        }
                    } else root = null;                                           // root node is deleted
                } else {                                                         // node has no children , and node is red node
                    if (node == node.parent.leftchild) node.parent.leftchild = null;
                    else node.parent.rightchild = null;
                }
            } else {
                RBTnode SucNode = node;
                Color DelColor = SucNode.color;
                RBTnode successor;
                if (node.leftchild == null) {                                 // node only has right child
                    successor = node.rightchild;
                    RB_Transplant(node, node.rightchild);
                    if (DelColor == Color.black) setDelete1(successor);
                } else if (node.rightchild == null) {                          // node only has left child
                    successor = node.leftchild;
                    RB_Transplant(node, node.leftchild);
                    if (DelColor == Color.black) setDelete1(successor);
                } else {                                                      // node has left and right child
                    SucNode = node.Right_Minimum(node);
                    DelColor = SucNode.color;
                    successor = SucNode.rightchild;
                    if (successor != null) {
                        if (SucNode.parent == node)
                            successor.parent = SucNode;                        // Sucnode is directly successor of node
                        else {                                                      // Sucnode is not directly successor of node
                            RB_Transplant(SucNode, SucNode.rightchild);
                            SucNode.rightchild = node.rightchild;
                            SucNode.rightchild.parent = SucNode;
                        }
                        RB_Transplant(node, SucNode);
                        SucNode.leftchild = node.leftchild;
                        SucNode.leftchild.parent = SucNode;
                        SucNode.color = node.color;
                        if (DelColor == Color.black) setDelete1(successor);
                    } else node.ID=SucNode.ID;                                  //Sucnode is the successor without any children
                           node.count = SucNode.count;
                           deleteNode(SucNode);
                }
            }
        }
    }

     /*
         transplant the  node
     */
    void RB_Transplant(RBTnode pnode, RBTnode node){
        if (pnode.parent ==null) root = node;
        else if (pnode == pnode.parent.leftchild) pnode.parent.leftchild = node;
        else pnode.parent.rightchild = node;
        node.parent = pnode.parent;
    }

        /*      different ways to deal with  the deleting NODE
        if(node.leftchild != null && node.rightchild != null){                // Case0 : delete the node having 2 children
            RBTnode predecessor = node.predecessor(node);
            node.ID = predecessor.ID;                                         // replace the node with the predecessor using ID and count
            node.count = predecessor.count;                                   // then the deletion changed to deleting the predecessor
            deleteNode(predecessor);
        } else {
            if (node.leftchild == null && node.rightchild == null) {          // Case1: delete a node having no children
                // Case1: delete a node having no children
                if (node.parent != null) {
                    RBTnode leafNode;
                    if (node == node.parent.leftchild) {
                        leafNode = new RBTnode(-1, -1);                       // add a dummy node
                        leafNode.color = Color.black;
                        node.parent.leftchild = leafNode;
                        leafNode.parent = node.parent;
                        setDelete1(leafNode);
                        leafNode.parent.leftchild = null;                    // delete the leaf node

                    } else {
                        leafNode = new RBTnode(-1, -1);                        // add a dummy node
                        leafNode.color = Color.black;
                        node.parent.rightchild = leafNode;
                        leafNode.parent = node.parent;
                        setDelete1(leafNode);
                        leafNode.parent.rightchild = null;                    // delete the leaf node
                    }
                } else root = null;                                           // root node is deleted
            } else {                                                          // Case2 and Case3: delete a node having noe child
                  if (node.leftchild != null) {
                    RBTnode lchild = node.leftchild;
                    if (node.parent != null) {
                        if (node == node.parent.leftchild) {
                            lchild.parent = node.parent;
                            node.parent.leftchild = lchild;
                        } else {
                            lchild.parent = node.parent;
                            node.parent.rightchild = lchild;
                        }
                    } else  lchild.parent = null;
                    //Case2.1: Red node with one left-child deleted, no re-balancing needed              // having left child
                    if (node.color == Color.red)
                        System.out.println("Red node deleted, no re-balancing needed.");

                    //Case2.2: Black node with one left-child deleted deleted, re-balancing is needed.
                    if (node.color == Color.black) {
                        System.out.println("Red node deleted, no re-balancing needed.");
                        setDelete1(lchild);
                    }
                  }
                    else {
                    RBTnode rchild = node.rightchild;
                      if (node.parent != null) {
                          if (node == node.parent.leftchild) {                                                 // having right child
                              rchild.parent = node.parent;
                              node.parent.leftchild = rchild;
                          } else {
                              rchild.parent = node.parent;
                              node.parent.rightchild = rchild;
                          }
                      } else rchild.parent = null;
                    //Case3.1: Red node with one left-child deleted, no re-balancing needed
                    if (node.color == Color.red)
                        System.out.println("Red node deleted, no re-balancing needed.");

                    //Case3.2: Black node with one left-child deleted deleted, re-balancing is needed.
                    if (node.color == Color.black) {
                        System.out.println("Red node deleted, re-balancing needed.");
                        setDelete1(rchild);
                    }
                }
            } */

        //if (root != null) printtest(root, root.ID, 0);



    /*
    child of the deleted node is a red node, make it black
     */
    void setDelete1(RBTnode node) {
        if (node != null) {
            if (node.color == Color.red) {
                node.color = Color.black;
            } else {
                if (node.parent == null) {
                    setDelete2(node);
                } else setDelete3(node);
            }
        }
    }


    /*
    child of the deleted node is a black root, entire tree is deficient. Done!
     */
    void setDelete2(RBTnode node){
        root = node;
        System.out.println("Child of the deleted node is a black root, entire tree is deficient. Done!");

    }

   /*
    Rb0 OR Lb0  case1: parent is black, sibling is black, then change the sibling color to red, then re-balance parent
                case2: parent is red, sibling is black, then change the sibling color to red, change the parent to black, done!
     */
    void setDelete3(RBTnode node){
        RBTnode sibling = node.sibling(node);
        if (sibling != null) {
            if (node.parent.color ==Color.black && sibling.color == Color.black
                    && (sibling.leftchild == null || sibling.leftchild.color == Color.black)
                    && (sibling.rightchild == null || sibling.rightchild.color == Color.black)) {
                sibling.color = Color.red;
                setDelete1(node.parent);
            } else if (node.parent.color ==Color.red && sibling.color == Color.black
                    && (sibling.leftchild == null || sibling.leftchild.color == Color.black)
                    && (sibling.rightchild == null || sibling.rightchild.color == Color.black)) {
                sibling.color = Color.red;
                node.parent.color = Color.black;
                //System.out.println("Deficiency eliminated, Done!");
            } else {
                setDelete4(node);
            }
        } else setDelete1(node.parent);
    }

    /*
    Rb1  case1 sibling is black, left-child of sibling is red, LL rotation
    Rb1  case2 sibling is black, right-child of sibling is red, LR rotation
    Rb2        sibling is black, left-child and right-child are red, LR rotation
    Lb1  case1 sibling is black, left-child of sibling is red, RL rotation
    Lb1  case2 sibling is black, right-child of sibling is red, RR rotation
    Lb2        sibling is black, left-child and right-child are red, RL rotation
     */
    void setDelete4( RBTnode node){
        RBTnode sibling = node.sibling(node);
        // Rb cases
        if (node == node.parent.rightchild){
            if(sibling.color ==Color.black && (sibling.leftchild != null && sibling.leftchild.color == Color.red)
                    && (sibling.rightchild == null || sibling.rightchild.color == Color.black)){                            // Rb1 case1
                sibling.leftchild.color = Color.black;
                sibling.color = node.parent.color;
                node.parent.color = Color.black;
                LLrotation(sibling, node.parent);
            } else if (sibling.color == Color.black &&(sibling.leftchild == null || sibling.leftchild.color == Color.black)
                    && (sibling.rightchild != null && sibling.rightchild.color == Color.red)){                                // Rb1 case2
                      sibling.rightchild.color = node.parent.color;
                      node.parent.color = Color.black;
                      LRrotation(sibling.rightchild, sibling, node.parent);
            } else if(sibling.color == Color.black && (sibling.leftchild != null && sibling.leftchild.color == Color.red)
                    && (sibling.rightchild != null && sibling.rightchild.color == Color.red)) {                                   //Rb2
                      sibling.rightchild.color = node.parent.color;
                      node.parent.color = Color.black;
                      LRrotation(sibling.rightchild, sibling, node.parent);
            } else setDelete5(node);
        }
        //Lb cases
        if (node == node.parent.leftchild){
            if(sibling.color ==Color.black && (sibling.leftchild == null || sibling.leftchild.color == Color.black)
                    && (sibling.rightchild != null && sibling.rightchild.color == Color.red)){                                 //Lb1 case1
                sibling.rightchild.color = Color.black;
                sibling.color = node.parent.color;
                node.parent.color = Color.black;
                RRrotation(sibling, node.parent);
            } else if (sibling.color ==Color.black && (sibling.leftchild != null && sibling.leftchild.color == Color.red)
                    && (sibling.rightchild == null || sibling.rightchild.color == Color.black)){                              //Lb1 case2
                      //sibling.color = node.parent.color;
                      sibling.leftchild.color = node.parent.color;
                      node.parent.color = Color.black;
                      RLrotation(sibling.leftchild, sibling, node.parent);
            } else if (sibling.color ==Color.black && (sibling.leftchild != null && sibling.leftchild.color == Color.red)
                    && (sibling.rightchild != null && sibling.rightchild.color == Color.red)){                                  //Lb2
                      sibling.leftchild.color = node.parent.color;
                      node.parent.color = Color.black;
                      RLrotation(sibling.leftchild, sibling, node.parent);
            } else setDelete5(node);
        }
    }

    /*
    Rr(n)  can be changed to Rbn questions
     */
    void setDelete5(RBTnode node){
        RBTnode sibling = node.sibling(node);
        //Rr() cases
        if (node == node.parent.rightchild) {

            if (node.parent.color == Color.black && sibling.color == Color.red) {         //  change sibling's to black,  parent node to red,
                sibling.color = Color.black;                                               //then make LL rotation
                node.parent.color = Color.red;
                LLrotation(sibling, node.parent);
                setDelete1(node);                                                         // change to the question that sibling is black or null
            }
        }
        // Lr() cases
        if (node == node.parent.leftchild) {

            if (node.parent.color == Color.black && sibling.color == Color.red) {         // change sibling's to black,  parent node to red
                                                                                          // then make RR rotation
                sibling.color = Color.black;
                node.parent.color = Color.red;
                RRrotation(sibling, node.parent);
                setDelete1(node);                                                         // change to the question that sibling is black or null
            }
        }
    }



    /*
   Print the nodes with the in-order looking , and their children relationship
    */
    void printtest2(RBTnode treeroot,int ID, int direction) {

        if(treeroot != null) {

            if(direction==0)    // tree鏄牴鑺傜偣
               System.out.printf("%2d(B) is root\n", treeroot.ID);
            else                // tree鏄垎鏀妭鐐�
             System.out.printf("%2d(%s) is %2d's %6s child\n", treeroot.ID, treeroot.color , ID, direction==1?"right" : "left");
            printtest2(treeroot.leftchild, treeroot.ID, -1);
            printtest2(treeroot.rightchild, treeroot.ID, 1);
        }
    }


    /*
    Print the nodes with the in-order looking
     */

    void printtest(RBTnode treeroot){

        if(treeroot != null) {
            printtest(treeroot.leftchild) ;
            System.out.println(treeroot.ID + " " + treeroot.color + " " + " " + treeroot.count);
            printtest(treeroot.rightchild);
        }
    }


    /*
     * Increase the count of the event theID by m. If theID is not present,
     * insert it. Print the count of theID after the addition. Time complexity:
     * O(log n).
     */
    void increase(int theID, int countIncreaseBy) {
        RBTnode NodeFound = find(theID);
        if (NodeFound != null) {
            NodeFound.count += countIncreaseBy;
        } else {
            insertNode(theID, countIncreaseBy);
            NodeFound = find(theID);
        }
        System.out.println(NodeFound.count);
    }

    /*
	 * Decrease the count of theID by m. If theID鈥檚 count becomes less than or
	 * equal to 0, remove theID from the counter. Print the count of theID after
	 * the deletion, or 0 if theID is removed or not present. Time complexity:
	 * O(log n).
	 */
    void reduce(int theID, int decreaseCountBy) {
        RBTnode NodeFound = find(theID);
        if (NodeFound != null) {
            if (NodeFound.count <= decreaseCountBy) {
                deleteNode(NodeFound);
                System.out.println(0);
            } else {
                NodeFound.count -= decreaseCountBy;
                System.out.println(NodeFound.count);
            }
        } else {
            // theID is not present. Print zero.
            System.out.println(0);
        }
    }

    /*
	 * Print the count of theID. If not present, print 0. Time complexity: O(log n).
	 */
    void count(int theID) {
        RBTnode NodeFound = find(theID);
        if (NodeFound != null) {
            System.out.println(NodeFound.count);
        } else {
            System.out.println(0);
        }
    }



     /*
	 * Print the ID and the count of the event with the lowest ID that is
	 * greater that theID. Print 鈥�0 0鈥�, if there is no next ID. Time complexity:
	 * O(log n).
	 */

    RBTnode next(int theID) {
        RBTnode NodeFound = find(theID);
        if (NodeFound != null) {
            RBTnode successorOfNodeFound = NodeFound.tree_successor(NodeFound);
            if (successorOfNodeFound != null) {
                System.out.println(successorOfNodeFound.ID + " " + successorOfNodeFound.count);
                return successorOfNodeFound;
            } else {
                System.out.println("0 0");
                return null;
            }
        } else {         // the ID is not in the tree, so find the next ID in the tree
            RBTnode theIDParent = findLost(theID);
            RBTnode successorOftheID ;
            if (theID < theIDParent.ID){
                successorOftheID = theIDParent;
            } else {
                successorOftheID = theIDParent.tree_successor(theIDParent);
            }
            if (successorOftheID != null){
                System.out.println(successorOftheID.ID + " " + successorOftheID.count);
                return successorOftheID;
            } else {
                System.out.println("0 0");
                return null;
            }
        }
    }

    /*
    Print the ID and the count of the event with the greatest key that is less that theID.
    Print 鈥�0 0鈥�, if there is no previous ID.  Time complexity: O(log n)
     */
    RBTnode previous(int theID){
        RBTnode NodeFound = find(theID);
        if (NodeFound != null) {
            RBTnode predecessorOfNodeFound = NodeFound.tree_predecessor(NodeFound);
            if (predecessorOfNodeFound != null) {
                System.out.println(predecessorOfNodeFound.ID + " " + predecessorOfNodeFound.count);
                return predecessorOfNodeFound;
            } else {
                System.out.println("0 0");
                return null;
            }
        } else {         // the ID is not in the tree, so find the previous ID in the tree
            RBTnode theIDParent = findLost(theID);
            RBTnode predecessorOftheID ;
            if (theID > theIDParent.ID){
                predecessorOftheID = theIDParent;
            } else {
                predecessorOftheID = theIDParent.tree_predecessor(theIDParent);
            }
            if (predecessorOftheID != null){
                System.out.println(predecessorOftheID.ID + " " + predecessorOftheID.count);
                return predecessorOftheID;
            } else {
                System.out.println("0 0");
                return null;
            }
        }
    }


     /*
          Print the total count for IDs between ID1 and ID2 inclusively. Note, ID1 鈮� ID2
          Time complexity:  O(log n + s) where s is the number of IDs in the range.
     KEY: Find the lowest common ancestor of ID1 and ID2 , then according to the route that from ID1 to lca and ID2 to lca,traversal the node's subtree in the route.
     */

    RBTnode previousInLCA (int theID){                      // the ID is not in the Tree, find the ID's previous node
        RBTnode theIDParent = findLost(theID);
        RBTnode predecessorOftheID ;
        if (theID > theIDParent.ID){
            predecessorOftheID = theIDParent;
        } else {
            predecessorOftheID = theIDParent.tree_predecessor(theIDParent);
        }
        if (predecessorOftheID != null){
            //System.out.println(predecessorOftheID.ID + " " + predecessorOftheID.count);
            return predecessorOftheID;
        } else {
            //System.out.println("0 0");
            return null;
        }
    }

    RBTnode nextInLCA(int theID){                             // the ID is not in the Tree, find the ID's next node
        RBTnode theIDParent = findLost(theID);
        RBTnode successorOftheID ;
        if (theID < theIDParent.ID){
            successorOftheID = theIDParent;
        } else {
            successorOftheID = theIDParent.tree_successor(theIDParent);
        }
        if (successorOftheID != null){
            //System.out.println(successorOftheID.ID + " " + successorOftheID.count);
            return successorOftheID;
        } else {
            //System.out.println("0 0");
            return null;
        }
    }

    /*
    lca in a binary search tree
     */
    RBTnode findLCA (RBTnode node1, RBTnode node2){
        RBTnode lcaNode = root;
        while( lcaNode.ID < node1.ID &&lcaNode.ID < node2.ID) {
            lcaNode = lcaNode.rightchild;
        }
        while( lcaNode.ID > node1.ID &&lcaNode.ID > node2.ID) {
            lcaNode = lcaNode.leftchild;
        }
        return lcaNode;
    }

    /*
     count the total sum of the entire tree
     */
     int sum=0;
     public void countTree(RBTnode lcaroot, int ID1, int ID2) {    
        /* base case */
        if (lcaroot == null) {
            return ;
        }
        
        // if ancestor is in range, then add the ancestor to sum
        if (ID1 <= lcaroot.ID && lcaroot.ID <= ID2) {
			sum = sum + lcaroot.count;   
        }
        // If ancestor is bigger than k1, then only we can go left subtree 
        if (ID1 < lcaroot.ID) {
        	countTree(lcaroot.leftchild, ID1, ID2);
        }
        // If ancestor is smaller than k2, then only we can go right subtree 
        if ( lcaroot.ID<ID2) {
        	countTree(lcaroot.rightchild, ID1, ID2);
        }
    }
     
    /*
     count all the node's count between node1 and node2 , node1 and node2 have the lowest common ancestor lca
     */
   
    public void inrange( int ID1, int ID2) {
    	if (ID1 > ID2) {                              //ID1 >ID2, null result
    		System.out.println("0");
    		return;
    	}
		RBTnode node1 = find( ID1);
		RBTnode node2 = find( ID2);
		//id1node is not in key use next function to find next node in tree
		if (node1 == null) {
			node1 = nextInLCA(ID1);
		}
		//node2 is not in key use next function to find node in tree
		if (node2 == null) {
			node2 = previousInLCA(ID2);
		}
		if (node1 == null || node2 == null) {          //node1's next node dose not exist, means node1 is bigger than biggest tree node
			System.out.println("0");                   //node2's next node dose not exist, means node1 is smaller than smallest tree node
			return;
		}
		if (node1.ID > node2.ID){
			System.out.println("0");                   // node1 and node2 are in the two adjacent nodes, null results
    		return;
		}
		RBTnode lca = findLCA(node1,node2);	
		countTree(lca, ID1, ID2);
		System.out.println(sum);
		sum = 0;
	}
	

    /*
    initialize the sorted array to be the red-black tree
     */
    RBTnode ArrayToRBTree(int IDArray[], int countArray[],int quantity ){
        if(quantity <= 0) throw new IllegalArgumentException();
        int log_quantity = 31 - Integer.numberOfLeadingZeros(quantity) ;                  // function of log2 , get the MaxTreeHeight
        int MaxTreeHeight =  log_quantity + 1;                                            // function of log2 , get the MaxTreeHeight
        root = sortedArrayToBST(IDArray,countArray,0,quantity-1,1, MaxTreeHeight);
        return root;
    }

    RBTnode sortedArrayToBST(int IDArray[], int countArray[], int start, int end, int nodeHeight, int MaxTreeHeight){
        if (start>end) {
            return null;
        }
        int mid = start+(end-start)/2;
        RBTnode rootIni = new RBTnode(IDArray[mid], countArray[mid]);
        rootIni.color = Color.black;
        rootIni.leftchild = sortedArrayToBST(IDArray, countArray, start, mid-1, nodeHeight+1, MaxTreeHeight);
        rootIni.rightchild = sortedArrayToBST(IDArray, countArray,mid+1, end, nodeHeight+1, MaxTreeHeight);
        if (rootIni.leftchild != null){
            rootIni.leftchild.parent = rootIni;
        }
        if (rootIni.rightchild != null){
            rootIni.rightchild.parent = rootIni;
        }
        if (nodeHeight == MaxTreeHeight){
            rootIni.color = Color.red;
        }
        return rootIni;
    }


}





























