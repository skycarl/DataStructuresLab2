/**
 * This is the class file for the custom StackOverflow exception that is thrown when a value is pushed to the stack,
 * but there is no more room on the stack. This can indicate that the postfix expression is too long.
 *
 * @author Skyler Carlson
 * @version 1.0
 * Date: 2019-03-01
 */

public class StackOverflow extends Exception {

  /**
   * Default constructor.
   */
  public StackOverflow() {
  }

  /**
   * This method returns information about the stack overflow.
   *
   * @return String output giving notice that stack overflow has occurred.
   */
  @Override
  public String toString() {
    return "Exception: stack overflow. Input expression could be too long.";
  }
}
