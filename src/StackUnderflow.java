/**
 * This is the class file for the custom StackUnderflow exception that is thrown when the stack is popped, but there
 * are no values to be popped. This can indicate an invalid postfix expression.
 *
 * @author Skyler Carlson
 * @version 1.0
 * Date: 2019-03-01
 */

public class StackUnderflow extends Exception {

  /**
   * Default constructor.
   */
  public StackUnderflow() {
  }

  /**
   * This method prints
   *
   * @return String output giving information that stack underflow has occurred.
   */
  @Override
  public String toString() {
    return "Exception: stack underflow. Potential invalid input expression.";
  }
}
