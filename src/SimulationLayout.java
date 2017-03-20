import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Samantha on 13/03/2017.
 */

    public class SimulationLayout extends JDialog {
        private JLabel lbSimulationTitle;
        private JButton btnGraph;
        private boolean succeeded;

        StateTransition s = new StateTransition();

        public SimulationLayout(Frame parent) {

            super(parent, "Simulation", true);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();

            lbSimulationTitle = new JLabel("Simulation below is taking your user input and running the word: \n" + Simulator.place + ", " + Simulator.simulations + " times." );
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 4;
            panel.add(lbSimulationTitle, cs);

            btnGraph = new JButton("Graph of Simulation");
            btnGraph.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    succeeded = true;
                    dispose();
                }
            });

            SimulationLayout.runSimulation();

            JPanel bp = new JPanel();
            bp.add(btnGraph);
//            bp.add(btnSimulation);
            getRootPane().setDefaultButton(btnGraph);

            getContentPane().add(panel, BorderLayout.CENTER);
            getContentPane().add(bp, BorderLayout.PAGE_END);

            pack();
            setResizable(false);
            setLocationRelativeTo(parent);
        }

        public static String runSimulation(){
            try {
                Simulator.main(null);
            } catch (Exception x) {
                throw new RuntimeException(x);
            }
            return null;
        }

    public boolean isSucceeded() {

        return succeeded;
    }

    }

