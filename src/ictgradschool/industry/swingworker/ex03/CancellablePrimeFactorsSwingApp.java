package ictgradschool.industry.swingworker.ex03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * Simple application to calculate the prime factors of a given number N.
 *
 * The application allows the user to enter a value for N, and then calculates 
 * and displays the prime factors. This is a very simple Swing application that
 * performs all processing on the Event Dispatch thread.
 *
 */
public class CancellablePrimeFactorsSwingApp extends JPanel {
	private PrimeFactorisationWorker prime;
	private JButton _startBtn;        // Button to start the calculation process.
	private JButton _abortBtn;        // Button to abort the calculation process.
	private JTextArea _factorValues;  // Component to display the result.

	public CancellablePrimeFactorsSwingApp() {
		// Create the GUI components.
		JLabel lblN = new JLabel("Value N:");
		final JTextField tfN = new JTextField(20);

		_startBtn = new JButton("Compute");
		_abortBtn = new JButton("Abort");
		_abortBtn.setEnabled(false);
		_factorValues = new JTextArea();
		_factorValues.setEditable(false);



		// Add an ActionListener to the start button. When clicked, the
		// button's handler extracts the value for N entered by the user from
		// the textfield and find N's prime factors.
		_startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String strN = tfN.getText().trim();

				long n = 0;

				try {
					n = Long.parseLong(strN);
				} catch(NumberFormatException e) {
					System.out.println(e);
				}

				prime = new PrimeFactorisationWorker(n);
				prime.execute();

				// Disable the Start button until the result of the calculation is known.
				_startBtn.setEnabled(false);

				// Enable the abort button until the result of the calculation is known.
				_abortBtn.setEnabled(true);

				// Clear any text (prime factors) from the results area.
				_factorValues.setText(null);

				// Set the cursor to busy.
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			}
		});

		_abortBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prime.cancel(true);
			}
		});

		// Construct the GUI.
		JPanel controlPanel = new JPanel();
		controlPanel.add(lblN);
		controlPanel.add(tfN);
		controlPanel.add(_startBtn);
		controlPanel.add(_abortBtn);

		JScrollPane scrollPaneForOutput = new JScrollPane();
		scrollPaneForOutput.setViewportView(_factorValues);

		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.NORTH);
		add(scrollPaneForOutput, BorderLayout.CENTER);
		setPreferredSize(new Dimension(500,300));
	}

	private class PrimeFactorisationWorker extends SwingWorker<List<Long>,Void>{
		Long N;

		public PrimeFactorisationWorker(Long N){
			this.N=N;
		}

		@Override
		protected List<Long> doInBackground() throws Exception {
			List<Long> primesFactorsList = new ArrayList<>();

			// Start the computation in the Event Dispatch thread.
			for (long i = 2; i * i <= N; i++) {

				// If i is a factor of N, repeatedly divide it out,
				// keep going while the worker hasn't been cancelled.
				while (N % i == 0 && !isCancelled()) {
					primesFactorsList.add(i);
					N = N / i;
				}
			}

			// if biggest factor occurs only once, n > 1
			if (N > 1) {
				primesFactorsList.add(N);
			}

			return primesFactorsList;
		}

		@Override
		protected void done() {
			// get the return value of the swingworker doInBackground
			// method and update the display (_factorValues) with the results.
			try {
				List<Long> factors = get();
				for (Long factor:factors
				) {
					_factorValues.append(factor.toString() + "\n");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (CancellationException e){
				//Handling the cancellation of the swingworker
				// via the abort button. sets everything back
				// to start conditions just like if it ended normally.

				// Re-enable the Start button.
				_startBtn.setEnabled(true);

				// Re-enable the Start button.
				_abortBtn.setEnabled(false);

				// Restore the cursor.
				setCursor(Cursor.getDefaultCursor());
			}
			// Re-enable the Start button.
			_startBtn.setEnabled(true);

			// Re-enable the Start button.
			_abortBtn.setEnabled(false);

			// Restore the cursor.
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Prime Factorisation of N");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new CancellablePrimeFactorsSwingApp();
		frame.add(newContentPane);

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

