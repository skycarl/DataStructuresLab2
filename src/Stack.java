/**
 * This is the Stack class. It allows items to be pushed, popped, peeked at, and
 * there is also a method to check whether the stack is empty.
 *
 * @author Skyler Carlson
 * @version 2.1
 * @since 2019-02-16
 */
public class Stack {

  private int[] stackContents;
  private int maxsize;
  private int stackTop;

  /**
   * Constructor to initialize a new stack, if a size parameter is specified
   *
   * @param maxsize Maximum size for the stack
   */
  public Stack(int maxsize) {
    this.maxsize = maxsize;
    this.stackContents = new int[maxsize];
    this.stackTop = -1; // Initialize the stack to be empty
  }

  /**
   * This method pushes a new item onto the stack.
   *
   * @param element The character to be pushed onto the stack
   * @throws StackOverflow
   */
  public void push(int element) throws StackOverflow {
    if ((stackTop + 1) >= maxsize) {
      //System.out.println("Error: Stack overflow");
      //System.exit(1);
      throw new StackOverflow();
    } else {
      this.stackContents[++stackTop] = element;
    }
  }

  /**
   * This method pops an item from the top of the stack (returns it and
   * deletes it from the top).
   *
   * @return The character that has been popped from the stack
   * @throws StackUnderflow
   */
  public int pop() throws StackUnderflow {

    if (isEmpty() == true) {
      throw new StackUnderflow();
    }
    // If there is no error, pop the stack
    return stackContents[stackTop--];
  }

  /**
   * This method returns the top element of the stack but does not delete it.
   *
   * @return The character that is at the top of the stack
   */
  public int peek() {
    if (isEmpty() == true) {
      System.out.println("Error: Stack underflow");
      System.exit(1);
    }

    // If there is no error, peek at the top of the stack
    return stackContents[stackTop];
  }

  /**
   * This method checks whether the stack is empty and returns a boolean.
   *
   * @return boolean indicating whether the stack is empty.
   */
  public boolean isEmpty() {
    if (stackTop == -1) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * This method clears the stack.
   */
  public void clear() {
    for (int i = 0; i < maxsize; i++) {
      stackContents[i] = ' ';
    }
    stackTop = -1;
  }
}

