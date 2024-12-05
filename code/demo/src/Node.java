/**
 * @author Armand Bonn
 * Node class to be used in Cartesian Tree, all general types with comparable can be used 
 */
public class Node<K extends Comparable<K>, P extends Comparable<P>> {
    // Attributes of node
    private K keyValue;
    private P priorityValue;
    private Node<K, P> rightNode;
    private Node<K, P> leftNode;
    private Node<K, P> parentNode;

    /**
     * Initiator of Node object
     * @param keyValue Key value (any type)
     * @param priorityValue Priority value (any type)
     */
    public Node(K keyValue, P priorityValue){
        this.keyValue = keyValue;
        this.priorityValue = priorityValue;
        this.rightNode = null;
        this.leftNode = null;
        this.parentNode = null;
    }

    /**
     * Add right node child
     * @param rightNode Node object reference
     */
    public void addRightNode(Node<K, P> rightNode){
        this.rightNode = rightNode;
    }

    /**
     * Add left node child
     * @param leftNode Node object reference
     */
    public void addLeftNode(Node<K, P> leftNode){
        this.leftNode = leftNode;
    }

    /**
     * Add parent node
     * @param parentNode Node Object
     */
    public void addParentNode(Node<K, P> parentNode){
        this.parentNode = parentNode;
    }

    public K getKeyValue(){
        return keyValue;
    }

    public P getPriorityValue(){
        return priorityValue;
    }

    public Node<K, P> getLeftNode(){
        return leftNode;
    }

    public Node<K, P> getRightNode(){
        return rightNode;
    }

    public Node<K, P> getParentNode(){
        return parentNode;
    }

    @Override
    public String toString() {
        return keyValue + ": " + priorityValue;
    }

}
