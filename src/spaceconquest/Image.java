package spaceconquest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Image extends JPanel {
  BufferedImage image;

  public Image(BufferedImage image) {
    this.image = image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    int x = (getWidth() - image.getWidth()) / 2;
    int y = (getHeight() - image.getHeight()) / 2;
    g.drawImage(image, x, y, this);
  }
}
