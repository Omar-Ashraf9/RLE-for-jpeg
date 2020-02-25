import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frmRleHuffmanEncoding;
	private JTextField textField;
	private JTextField textField_1;
	public JButton btnDecompression;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmRleHuffmanEncoding.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRleHuffmanEncoding = new JFrame();
		frmRleHuffmanEncoding.setTitle("RLE- HUFFMAN ENCODING FOR JPEG");
		frmRleHuffmanEncoding.setBounds(100, 100, 597, 336);
		frmRleHuffmanEncoding.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRleHuffmanEncoding.getContentPane().setLayout(null);
		
		textField = new JTextField();
 		textField.setBounds(122, 36, 399, 30);
		frmRleHuffmanEncoding.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(122, 101, 399, 30);
		frmRleHuffmanEncoding.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnCompression = new JButton("Compression");
		btnCompression.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String in = textField.getText();
				String[] values = in.split(",");
				try {
					textField_1.setText(Main.compression(values));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnDecompression.setEnabled(true);
			}
		});
		btnCompression.setFont(new Font("Gadugi", Font.PLAIN, 12));
		btnCompression.setBounds(122, 232, 115, 30);
		frmRleHuffmanEncoding.getContentPane().add(btnCompression);
		
		btnDecompression = new JButton("Decompression");
		btnDecompression.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textField_1.setText(Main.decompression());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnDecompression.setEnabled(false);
			}
			
		});
		btnDecompression.setFont(new Font("Gadugi", Font.PLAIN, 12));
		btnDecompression.setBounds(406, 232, 115, 30);
		frmRleHuffmanEncoding.getContentPane().add(btnDecompression);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setFont(new Font("Georgia", Font.PLAIN, 12));
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInput.setBounds(10, 36, 102, 27);
		frmRleHuffmanEncoding.getContentPane().add(lblInput);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setFont(new Font("Gadugi", Font.PLAIN, 12));
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(10, 101, 102, 30);
		frmRleHuffmanEncoding.getContentPane().add(lblOutput);
	}
}
