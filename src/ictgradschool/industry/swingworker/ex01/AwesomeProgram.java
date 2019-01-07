package ictgradschool.industry.swingworker.ex01;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AwesomeProgram implements ActionListener {
    private JLabel progressLabel = new JLabel();
    private JLabel myLabel = new JLabel();
    private JButton myButton = new JButton();
    /** Called when the button is clicked. */
    @Override
    public void actionPerformed(ActionEvent e) {
        myButton.setEnabled(false);
// Start the SwingWorker running
        MySwingWorker worker = new MySwingWorker();
        worker.execute();
// When the SwingWorker has finished, display the result in
// myLabel.
//        int result = worker.get();
        myButton.setEnabled(true);
//        myLabel.setText("Result: " + result);
    }


    private class MySwingWorker extends SwingWorker<Integer, Void> {
        protected Integer doInBackground(){
            int result = 0;
            for (int i = 0; i < 100; i++) {
// Do some long-running stuff
//                result += doStuffAndThings();
// Report intermediate results
                progressLabel.setText("Progress: " + i + "%");
            }
            return result;
        }
    }
}

