import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;


public class ResultLayout extends JDialog {
    private JLabel lbResult;
    private JButton btnClose;
    private boolean succeeded;
    private double averageTime;
    private double value;

    Login L = new Login();
    DecimalFormat df = new DecimalFormat("####0.00");

    public ResultLayout(Frame parent) {

        super(parent,"Places Project:", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        value = L.calculateAverage();
        lbResult = new JLabel("Your average time taken was: " + df.format(value));

        try {
            clearFile();
            saveFile();
        } catch (Exception x) {
            throw new RuntimeException(x); // Throw run-time because can't do IO
        }
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 4;
        panel.add(lbResult, cs);

        succeeded = true;

        btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel bp = new JPanel();
        bp.add(btnClose);
        getRootPane().setDefaultButton(btnClose);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void clearFile() throws IOException {
        PrintWriter pw = new PrintWriter("userInformation.txt");
        pw.close();
    }

    public void saveFile() throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter("userInformation.txt",true);
            pw = new PrintWriter(fw);

            Login.averageLetterTimes.set(Login.position, RoundTo2Decimals(value));

                for (int i = 0; i < Login.usernames.size(); i++){
                    pw.write(Login.usernames.get(i) + "\n");
                    pw.write(Login.passwords.get(i) + "\n");
                    pw.write(Login.averageLetterTimes.get(i) + "\n");
                }
            pw.close();
            fw.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }


    double RoundTo2Decimals(double value) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(value));
    }



    public boolean isSucceeded() {
        return succeeded;
    }


}