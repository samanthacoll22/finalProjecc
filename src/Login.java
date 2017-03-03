/**
 * Created by Samantha on 01/03/2017.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login {
    public static boolean authenticate(String username, String password) {
        // hardcoded username and password
        if (username.equals("sam") && password.equals("password")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("User Login for Places Project");
        final JButton btnLogin = new JButton("Click to login");

        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        LoginLayout loginDlg = new LoginLayout(frame);
                        loginDlg.setVisible(true);
                        // if logon successfully
                        if(loginDlg.isSucceeded()){
                            btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
                        }
                    }
                });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
    }
}
