package ui;

import java.awt.Color;
import javax.swing.*;
import game.Player;

public class PlayerPanel extends JPanel {

    private Player player;

    public PlayerPanel(Player player) {
        this.setBackground(Color.GREEN);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
    
}
