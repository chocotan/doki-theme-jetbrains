/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Chris Magnussen and Elior Boukhobza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

/*
 * Created by JFormDesigner on Fri Jun 29 18:52:29 IDT 2018
 */

package io.acari.DDLC.wizard.steps;

import com.intellij.ide.customize.AbstractCustomizeWizardStep;
import com.intellij.ui.components.JBScrollPane;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author Elior Boukhobza
 */
public class DDLCWizardWelcomePanel extends AbstractCustomizeWizardStep {
  public DDLCWizardWelcomePanel() {
    initComponents();
  }

  @Override
  protected String getTitle() {
    return "Welcome";
  }

  @Override
  protected String getHTMLHeader() {
    return "<html><body><h1>Welcome to the DDLC Theme Wizard!</h1></body></html>";
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    final ResourceBundle bundle = ResourceBundle.getBundle("messages.DDLCWizardBundle");
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    JLabel icon = new JLabel();
    JScrollPane scrollPane1 = new JBScrollPane();
    JTextArea textArea2 = new JTextArea();

    //======== this ========
    setMinimumSize(new Dimension(400, 90));
    setLayout(new MigLayout(
        "fill,hidemode 3,alignx center",
        // columns
        "[fill]",
        // rows
        "[199]" +
            "[]"));
    add(icon, "cell 0 0 9 1,align center center,grow 0 0");

    //======== scrollPane1 ========
    {
      scrollPane1.setBorder(null);

      //---- textArea2 ----
      textArea2.setText(bundle.getString("DDLCWizardWelcomePanel.textArea2.text"));
      textArea2.setWrapStyleWord(true);
      textArea2.setLineWrap(true);
      textArea2.setFont(UIManager.getFont("Label.font"));
      textArea2.setBackground(UIManager.getColor("Panel.background"));
      textArea2.setEditable(false);
      textArea2.setBorder(null);
      scrollPane1.setViewportView(textArea2);
    }
    add(scrollPane1, "cell 0 1");
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
    final ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/wizard/logo.png"))
        .getImage().getScaledInstance(800, 474, Image.SCALE_SMOOTH));
    icon.setIcon(imageIcon);
  }

  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
