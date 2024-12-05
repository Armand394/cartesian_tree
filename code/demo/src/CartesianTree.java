import java.util.NoSuchElementException;

/** @author Armand Bonn
 * Cartesian tree class taking types of keys (K) and priorities (P).
 */
public class CartesianTree<K extends Comparable<K>, P extends Comparable<P>>{
    private Node<K, P> rootNode;

    /**
     * initiator cartesian tree
     */
    public CartesianTree(){
        this.rootNode = null;
    }

    /**
     * Check if a Cartesian tree is empty
     * @return True if it is empty
     */
    public boolean isTreeEmpty(){
        return rootNode == null;
    }

    /**
     * Insert node value only considering the keys and not priorities (no rotations)
     * @param keyValue Key value of node to be inserted
     * @param priorityValue Priority value of node (default - not used)
     */
    public void insertNodeKey(K keyValue, P priorityValue){

        // If tree is empty update root node
        if(rootNode == null){
            rootNode = new Node<>(keyValue, priorityValue);
            return;
        }

        // Find lead node where to insert new node
        Node<K, P> leafNode = findleafNode(keyValue, rootNode);
        // Create new  node
        Node<K, P> newNode = new Node<>(keyValue, priorityValue);
        // Add parent node of new node
        newNode.addParentNode(leafNode);

        // Insert new node as child relative to key value
        if(keyValue.compareTo(leafNode.getKeyValue()) < 0){
            leafNode.addLeftNode(newNode);
        }
        else{            
            leafNode.addRightNode(newNode);
        }
    }

    /**
     * Insert node in tree considering key and priority value
     * @param keyValue Key value of node to be inserted
     * @param priorityValue Priority value of node
     */
    public void insertNode(K keyValue, P priorityValue){

        // If tree is empty update root node
        if(rootNode == null){
            rootNode = new Node<>(keyValue, priorityValue);
            return;
        }

        // Find lead node where to insert new node
        Node<K, P> leafNode = findleafNode(keyValue, rootNode);
        // Create new  node
        Node<K, P> newNode = new Node<>(keyValue, priorityValue);
        // Add parent node of new node
        newNode.addParentNode(leafNode);

        // Insert new node as child relative to key value
        if(keyValue.compareTo(leafNode.getKeyValue()) < 0){
            leafNode.addLeftNode(newNode);
        }
        else{            
            leafNode.addRightNode(newNode);
        }
        
        // Rotate newly inserted node up until priority property is not violated
        while(newNode.getPriorityValue().compareTo(newNode.getParentNode().getPriorityValue()) < 0){
            // Get parent node with higher priority value
            Node<K, P> parentNode = newNode.getParentNode();

            // Rotate according to keyvalue
            if(newNode.getKeyValue().compareTo(parentNode.getKeyValue()) > 0){
                rotateLeft(newNode, parentNode);
            } else{
                rotateRight(newNode, parentNode);
            }

            // If the inserted node does not have a parent anymore, break
            if (newNode.getParentNode() == null){
                break;
            }
        }
        
    }
    
    /**
     * Rotate node to the right
     * @param currentNode Node higher in the tree which needs to go up
     * @param parentNode Parent node who is rotated down
     */
    public void rotateRight(Node<K, P> currentNode, Node<K, P> parentNode){

        // Update left node of the parent node to be right node of current
        parentNode.addLeftNode(currentNode.getRightNode());

        // If the new left node of parent is not null update the parent reference of the former
        if(parentNode.getLeftNode() != null){
            parentNode.getLeftNode().addParentNode(parentNode);
        }

        // Add the parent node to be the right node of the current
        currentNode.addRightNode(parentNode);

        // Update the parent references
        currentNode.addParentNode(parentNode.getParentNode());
        parentNode.addParentNode(currentNode);

        // Update the parent's old parent child reference to be the newly rotated node
        if (currentNode.getParentNode() != null){
            if (currentNode.getParentNode().getRightNode() == parentNode){
                currentNode.getParentNode().addRightNode(currentNode);
            } else{
                currentNode.getParentNode().addLeftNode(currentNode);
            }
        } else {
            rootNode = currentNode;
        }

    }

    public void rotateLeft(Node<K, P> currentNode, Node<K, P> parentNode){

        // Update right node of the parent node to be left node of current
        parentNode.addRightNode(currentNode.getLeftNode());

        
        // If the new right node of parent is not null update the parent reference of the former
        if(parentNode.getRightNode() != null){
            parentNode.getRightNode().addParentNode(parentNode);
        }

        // Add the parent node to be the left node of the current
        currentNode.addLeftNode(parentNode);

        // Update the parent references
        currentNode.addParentNode(parentNode.getParentNode());
        parentNode.addParentNode(currentNode);

        // Update the parent's old parent child reference to be the newly rotated node
        if (currentNode.getParentNode() != null){
            if (currentNode.getParentNode().getRightNode() == parentNode){
                currentNode.getParentNode().addRightNode(currentNode);
            } else{
                currentNode.getParentNode().addLeftNode(currentNode);
            }
        } else {
            rootNode = currentNode;
        }

    } 


    /**
     * Recursive function to find the leaf node where to insert a new node through the key value
     * @param keyValue Key value of node going to be inserted
     * @param currentNode Current node to consider if key is on the right or left
     * @return Next node to be considered in the tree by Binary Search Tree properties
     */
    public Node<K, P> findleafNode(K keyValue, Node<K, P> currentNode){
        
        if (keyValue.compareTo(currentNode.getKeyValue()) < 0 && currentNode.getLeftNode() != null) {
            return findleafNode(keyValue, currentNode.getLeftNode());

        } else if (keyValue.compareTo(currentNode.getKeyValue()) > 0  && currentNode.getRightNode() != null) {
            return findleafNode(keyValue, currentNode.getRightNode());
        }

        return currentNode;
    }

    /**
     * Function to delete a node in the tree
     * @param keyValue Key value of the node to delete
     * @param priorityValue Priority value of the node to delete
     */
    public void deleteNode(K keyValue, P priorityValue){
        // Find the node in the tree
        Node<K, P> nodeToDelete = findNode(keyValue);

        // If node is not found throw an error
        if(nodeToDelete == null){
            throw new NoSuchElementException("Element not found: (" + keyValue + ": " + priorityValue + ")");
        }

        // Access children of node to delete
        Node<K, P> rightNode = nodeToDelete.getRightNode();
        Node<K, P> leftNode = nodeToDelete.getLeftNode();

        // While node to delete is not a leaf, rotate it
        while (rightNode != null || leftNode != null){

            // If has two children, rotate with respect to highest priority value, else rotate with only child
            if(rightNode != null && leftNode != null){
                if(leftNode.getPriorityValue().compareTo(rightNode.getPriorityValue()) < 0){
                    rotateRight(leftNode, nodeToDelete);
                } else{
                    rotateLeft(rightNode, nodeToDelete);
                }
            } else if (leftNode == null){
                rotateLeft(rightNode, nodeToDelete);
            } else{
                rotateRight(leftNode, nodeToDelete);
            }

            // Update current children of node to delete after rotation
            rightNode = nodeToDelete.getRightNode();
            leftNode = nodeToDelete.getLeftNode();

        }

        // If node to delete is root node after all rotation, set tree as empty
        if(nodeToDelete == rootNode){
            rootNode = null;
            return;
        }

        // Remove the reference of the node to delete in the tree (when it has become a leaf)
        if (nodeToDelete.getParentNode().getRightNode() == nodeToDelete){
            nodeToDelete.getParentNode().addRightNode(null);
        } else{
            nodeToDelete.getParentNode().addLeftNode(null);
        }

    }

    /**
     * Recusrive function to find node in a tree based on key values
     * @param keyValue Key value of node to be found
     * @return The node found in the tree
     */
    public Node<K, P> findNode(K keyValue){
        return findNodeRec(keyValue, rootNode);
    }

    /**
     * Recursive function to find the node
     * @param keyValue key value of the node to find
     * @param currentNode current node considerent in the tree
     * @return null if not found, current node if found, or left- or right sub tree if further in the tree
     */
    public Node<K, P> findNodeRec(K keyValue, Node<K, P> currentNode){

        if(currentNode == null){
            return currentNode;
        } else if(currentNode.getKeyValue().equals(keyValue)){
            return currentNode;
        }

        if(keyValue.compareTo(currentNode.getKeyValue()) < 0){
            return findNodeRec(keyValue, currentNode.getLeftNode());
        } else{
            return findNodeRec(keyValue, currentNode.getRightNode());
        }
    }   


    /**
     * Get height of the tree
     * @return Height of the tree
     */
    public int getHeight(){
        // If root node is not null start recursive function to get height, else return -1
        if (rootNode != null){
            return getHeightRecursive(rootNode);
        } else{
            return -1;
        }
    }

    public int getHeightRecursive(Node<K, P> currentNode){
        // Start of with leaf node that has height of 0 (1 + max)
        int leftHeight = -1;
        int rightHeight = -1;

        // Get height of left subtree
        if (currentNode.getLeftNode() != null){
            leftHeight = getHeightRecursive(currentNode.getLeftNode());
        }
        // Get height of right subtree
        if (currentNode.getRightNode() != null){
            rightHeight = getHeightRecursive(currentNode.getRightNode());
        }

        // Return the max of left and right subtree + extra depth of 1
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Print pre-order traversal tree (start from root and go from left to right)
     */
    public void printTreePreOrderTraversal(){
        printTreeRecursive(rootNode);
    }

    /**
     * Recursive function to print tree in pre-order
     * @param currentNode Node to be printed and iterated
     */
    public void printTreeRecursive(Node<K, P> currentNode){
        
        // If node is null return
        if(currentNode == null){
            return;
        }

        // Print current node
        System.out.println(currentNode);

        // Iterate over left subtree
        printTreeRecursive(currentNode.getLeftNode());

        // Iterate over right subtree
        printTreeRecursive(currentNode.getRightNode());
        
    }

    /**
     * Print in order traversal (start from most left leaf and go down to the right in the tree)
     */
    public void printInOrder() {
        printInOrderRecursive(rootNode);
    }


    /**
     * Recursive function to print tree in in-order
     * @param currentNode Node to be printed and iterated
     */
    private void printInOrderRecursive(Node<K, P> currentNode) {
        
        // If node is null return
        if (currentNode == null) {
            return;
        }

        // Iterate over left subtree
        printInOrderRecursive(currentNode.getLeftNode());
        
        // Print current node
        System.out.println(currentNode);
        
        // Iterate over right subtree
        printInOrderRecursive(currentNode.getRightNode());
    }


    /**
     * Verify if Cartesian tree does hold all properties
     * @return True if all properties hold
     */
    public boolean verifyCartiesanTree() {
        return cartiesianTreeVerification(rootNode);
    }

    /**
     * Recursive function to verify if properties are met in all the nodes
     * @param currentNode Node to check properties
     * @return False if properties are not met
     */
    public boolean cartiesianTreeVerification(Node<K, P> currentNode) {
        
        // If at lead node, return
        if (currentNode == null) {
            return true;
        }

        // Check properties for left node if it is not null
        if (currentNode.getLeftNode() != null) {
            if (currentNode.getLeftNode().getKeyValue().compareTo(currentNode.getKeyValue()) > 0) {
                return false;
            }
            if (currentNode.getLeftNode().getPriorityValue().compareTo(currentNode.getPriorityValue()) <  0) {
                return false;
            }
        }
        // Check properties for right node if it not null
        if (currentNode.getRightNode() != null) {
            if (currentNode.getRightNode().getKeyValue().compareTo(currentNode.getKeyValue()) < 0) {
                return false;
            }
            if (currentNode.getRightNode().getPriorityValue().compareTo(currentNode.getPriorityValue()) < 0) {
                return false;
            }
        }
    
        // Verify the properties for both left- and right susbtrees
        return cartiesianTreeVerification(currentNode.getLeftNode()) && cartiesianTreeVerification(currentNode.getRightNode());
    }
    

}