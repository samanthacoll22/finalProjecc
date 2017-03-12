/**
 * Created by Samantha on 12/03/2017.
 */
import com.sun.javafx.binding.StringFormatter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RegisterLayout extends JDialog  {

    private static JTextField tfUsername;
    private static JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private boolean succeeded;

    public RegisterLayout(Frame parent) {
        super(parent, "Register", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnRegister = new JButton("Register");

        btnRegister.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e){
                if (!Login.authenticate(getUsername(), getPassword())) {
                    JOptionPane.showMessageDialog(RegisterLayout.this,
                            "Hi " + getUsername() + "! You have successfully registered as a new user.",
                            "Register",
                            JOptionPane.INFORMATION_MESSAGE);
                    try {
                        saveFile();
                    }
                    catch (Exception x) {
                        throw new RuntimeException(x); // Throw run-time because can't do IO
                    }
                        succeeded = true;
                    dispose();
 //

                } else {
                    JOptionPane.showMessageDialog(RegisterLayout.this,
                            "These details are already registered. Please cancel and login or create a new profile below.",
                            "Register",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;

                }
                            }
        });


        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnRegister);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public static String getUsername() {
        return tfUsername.getText().trim();
    }

    public static String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }


    public static void saveFile() throws IOException {
        FileWriter fw = null;
            PrintWriter pw = null;
            try {
                fw = new FileWriter("userInformation.txt",true);
                pw = new PrintWriter(fw);

                pw.write("\n" + getUsername() + "\n" + getPassword());
                pw.close();
                fw.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }
}


