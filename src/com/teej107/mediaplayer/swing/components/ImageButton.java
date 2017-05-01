package com.teej107.mediaplayer.swing.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/29/2017.
 */
public class ImageButton extends JButton
{
	public ImageButton(ImageIcon icon, ImageIcon press, Dimension preferredDimension)
	{
		setPreferredSize(preferredDimension);
		setBorderPainted(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setIcon(icon);
		setPressedIcon(press);
	}
}
