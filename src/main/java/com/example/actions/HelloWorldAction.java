/*
 * パッケージ名は、生成したプラグインのパッケージ名よりも下に移してください。
 * プラグインのパッケージ名=> com.example
 *   com.change_vision.astah.extension.plugin => X
 *   com.example                              => O
 *   com.example.internal                     => O
 *   learning                                 => X
 */
package com.example.actions;


import javax.swing.JOptionPane;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class HelloWorldAction implements IPluginActionDelegate {

  public Object run(IWindow window) throws UnExpectedException {
      try {
          JOptionPane.showMessageDialog(window.getParent(), "Hello World");

      } catch (Exception e) {
        JOptionPane.showMessageDialog(window.getParent(), "Exception occured", "Alert", JOptionPane.ERROR_MESSAGE);
          throw new UnExpectedException();
      }
      return null;
  }
}
