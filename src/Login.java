/**
 * Created by Samantha on 01/03/2017.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Login {

    private static ArrayList<String> usernames = new ArrayList<String>();
    private static ArrayList<String> passwords = new ArrayList<String>();

    public static void userInformation () throws IOException{
        Scanner s = new Scanner(new File("userInformation.txt"));
//        s.useDelimiter(",");
        String u, p;

        while(s.hasNext()){
            u = s.nextLine();
            p = s.nextLine();
            usernames.add(u);
            passwords.add(p);
        }
        s.close();
        System.out.println(usernames);
        System.out.println(passwords);
    }

    public static boolean authenticate(String name, String pass) {
        if (usernames.contains(name) && passwords.contains(pass)) {
            Integer position = getUsernamePos(name);
            if(pass.equals(passwords.get(position))){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        final JFrame frame = new JFrame("Places Project");
        final JButton btnLogin = new JButton("Click to login");
        final JButton btnRegister = new JButton("Click to register");

        userInformation();

        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        LoginLayout loginDlg = new LoginLayout(frame);
                        loginDlg.setVisible(true);
                        if(loginDlg.isSucceeded()) {
                            try {
                                InputLayout inputDlg = new InputLayout(frame);
                                inputDlg.setVisible(true);
                            }
                            catch (Exception x) {
                                throw new RuntimeException(x); // Throw run-time because can't do IO
                            }
                        }

                    }
                });

        btnRegister.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        RegisterLayout registerDlg = new RegisterLayout(frame);
                        registerDlg.setVisible(true);
                        if(registerDlg.isSucceeded()) {
                            try {
                                InputLayout inputDlg = new InputLayout(frame);
                                inputDlg.setVisible(true);
                            } catch (Exception x) {
                                throw new RuntimeException(x); // Throw run-time because can't do IO
                            }
                        }
                    }
                });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.getContentPane().add(btnRegister);
        frame.setVisible(true);
    }

    private static int getUsernamePos(String name) {
        return usernames.indexOf(name);
    }

}
