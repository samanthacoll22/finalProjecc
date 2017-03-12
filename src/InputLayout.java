/**
 * Created by Samantha on 12/03/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class InputLayout extends JDialog {
    private JTextField tfInput;
    private JLabel lbPlace;
    private JButton btnNext;
    private JButton btnCancel;
    private boolean succeeded;

    StateTransition s = new StateTransition();


    public InputLayout(Frame parent) throws FileNotFoundException {
        super(parent, "Welcome" , true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        lbPlace = new JLabel("Please type the following word below: " + s.getRandomPlace());
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 4;
        panel.add(lbPlace, cs);

        tfInput = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;
        panel.add(tfInput, cs);

        btnNext = new JButton("Next");

        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

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

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}
