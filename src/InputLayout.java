/**
 * Created by Samantha on 12/03/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InputLayout extends JDialog implements KeyListener {
    private JTextField tfInput;
    private JLabel lbPlace;
    private JButton btnNext;
    private JButton btnCancel;
    private boolean succeeded;

    RandomPlace rp = new RandomPlace();
    Login L = new Login();


    public InputLayout(Frame parent) throws FileNotFoundException {

        super(parent, "Places Project:", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        String randomPlace = rp.getRandomPlace();
        lbPlace = new JLabel("Please type the following word below then press enter or 'Next': "  + randomPlace);
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 4;
        panel.add(lbPlace, cs);

        tfInput = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;
        panel.add(tfInput, cs);

        tfInput.addKeyListener(this);
        btnNext = new JButton("Next");

        final long[] startTime = {(System.currentTimeMillis())};

        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double endTime = (System.currentTimeMillis());
                double totalTime = (endTime - startTime[0]) / 1000;
                double averageTime = totalTime / randomPlace.length();
                if (getCurrentInput().equals(randomPlace)) {
                    L.averageTimes.add(averageTime);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(InputLayout.this,
                            "Incorrect spelling",
                            "Word Typing",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfInput.setText("");
                    succeeded = false;
                    startTime[0] = (System.currentTimeMillis());
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
        bp.add(btnNext);
        bp.add(btnCancel);
        getRootPane().setDefaultButton(btnNext);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void keyPressed(KeyEvent e) {
        // rfjodbfidbgfldbgdsljb
    }

    public void keyReleased(KeyEvent e) {
        //ighopbskbgs

    }

    public void keyTyped(KeyEvent e) {
//        System.out.println(getCurrentInput());
    }

    public String getCurrentInput() {
        return tfInput.getText().trim();
    }

    public boolean isSucceeded() {

        return succeeded;
    }
}