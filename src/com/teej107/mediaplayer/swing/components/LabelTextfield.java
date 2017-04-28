package com.teej107.mediaplayer.swing.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/26/2017.
 */
public class LabelTextfield extends JComponent
{
	private JTextField textField;
	private JLabel label;

	public LabelTextfield(String label, int width)
	{
		setPreferredSize(new Dimension(width, 25));
		setLayout(new BorderLayout());
		this.textField = new JTextField();
		this.label = new JLabel(label + ": ");
		add(this.label, BorderLayout.LINE_START);
		add(this.textField, BorderLayout.CENTER);
	}

	public LabelTextfield(String label)
	{
		this(label, 150);
	}

	public JTextField getTextField()
	{
		return textField;
	}

	public JLabel getLabel()
	{
		return label;
	}
}
