import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GUI {
    public JPanel root;
    private JButton callButton;
    private JButton quitButton;
    private JPanel bottom;
    private JPanel up;
    private JTextField textField1;
    private JLabel label;
    IpClient ipClient;


    public GUI(IpClient ipClient) {
        this.ipClient = ipClient;
        //call other
        callButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String host = textField1.getText();
                String ip = Utility.getIp(host);
                int port = Utility.getPort(host);
                try {
                    if (port == -1) {
                        ipClient.callbyP2P(ip);
                    } else {
                        ipClient.callbyP2P(ip, port);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }

    public static int showOptionDialog() {
        int reply = JOptionPane.showConfirmDialog(null, "是否接听", "来电", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            return 1;
        } else {
            return 0;
        }
    }
}
