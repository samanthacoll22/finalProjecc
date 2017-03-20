/**
 * Created by Samantha on 01/03/2017.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class Login {

    public static ArrayList<String> usernames = new ArrayList<String>();
    public static ArrayList<String> passwords = new ArrayList<String>();
    public static ArrayList<Double> averageTimes = new ArrayList<Double>();
    public static ArrayList<Double> averageLetterTimes = new ArrayList<Double>();

    public static String user;
    public static int position;

    public static void userInformation() throws IOException{
        Scanner s = new Scanner(new File("userInformation.txt"));
        String u, p;
        Double t;

        while(s.hasNext()){
            u = s.next();
            p = s.next();
            t = s.nextDouble();
            usernames.add(u);
            passwords.add(p);
            averageLetterTimes.add(t);
        }
        s.close();
        System.out.println(usernames);
        System.out.println(passwords);
        System.out.println(averageLetterTimes);
    }

    public static boolean authenticate(String name, String pass) {
        if (usernames.contains(name) && passwords.contains(pass)) {
           position = getUsernamePos(name);
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
                            InputLayout inputDlg = null;
                            for (int i = 0; i < Simulator.noOfUserWords; i++) {
                                try {
                                    inputDlg = new InputLayout(frame);
                                    inputDlg.setVisible(true);
                                } catch (Exception x) {
                                    throw new RuntimeException(x); // Throw run-time because can't do IO
                                }
                            }
                            if (inputDlg.isSucceeded()) {
                                ResultLayout resultDlg = new ResultLayout(frame);
                                resultDlg.setVisible(true);
                                if(resultDlg.isSucceeded()) {
                                    SimulationLayout simulationDlg = new SimulationLayout(frame);
                                    simulationDlg.setVisible(true);
                                    if(simulationDlg.isSucceeded()){
                                        GraphPanel.createAndShowGui();
                                    }
                                }
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
                            InputLayout inputDlg = null;
                            for (int i = 0; i < Simulator.noOfUserWords; i++) {
                                try {
                                    inputDlg = new InputLayout(frame);
                                    inputDlg.setVisible(true);
                                } catch (Exception x) {
                                    throw new RuntimeException(x); // Throw run-time because can't do IO
                                }
//                                System.out.println(averageTimes);
                            }
                            if (inputDlg.isSucceeded()) {
                                ResultLayout resultDlg = new ResultLayout(frame);
                                resultDlg.setVisible(true);
                                if(resultDlg.isSucceeded()){
                                    SimulationLayout simulationDlg = new SimulationLayout(frame);
                                    simulationDlg.setVisible(true);
                                    if(simulationDlg.isSucceeded()){
                                        GraphPanel.createAndShowGui();
                                    }
                                }
                            }

                        }
                    }
                });

//        frame.add(new JLabel(new ImageIcon("logo.png")));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.getContentPane().add(btnRegister);
        frame.setVisible(true);
    }

    public static int getUsernamePos(String name) {
        return usernames.indexOf(name);
    }

    public static String getCurrentUser(){
        user = usernames.get(position);
        System.out.println(position);
        return user;
    }


    public double calculateAverage() {
        if (averageTimes == null || averageTimes.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (Double t : averageTimes) {
            sum += t;
        }
        return sum / averageTimes.size();
    }
}
