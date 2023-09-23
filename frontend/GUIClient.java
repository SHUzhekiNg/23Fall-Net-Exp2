package frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import core.TCPClient;
import core.UDPClient;

public class GUIClient extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton chooseButton;
    private JButton uploadButtonTCP;
    private JButton uploadButtonUDP;
    private JLabel selectedFileLabel;
    private File selectedFile;
    private String status;
    public GUIClient() throws HeadlessException{
        this.setTitle("GUIClient");
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(185, 213, 165));
        jPanel.setLayout(new GridBagLayout());

        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);
        addComponent(jPanel, usernameLabel, 0, 0);
        addComponent(jPanel, usernameField, 1, 0);

        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);
        addComponent(jPanel, passwordLabel, 0, 1);
        addComponent(jPanel, passwordField, 1, 1);

        JButton loginButton = new JButton("登录");
        addComponent(jPanel, loginButton, 0, 2, 1, 1);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    
                    status = TCPClient.TCPLogin(username, password);
                    if (status.equals("true")) {
                        // JOptionPane.showMessageDialog(GUIClient.this, "登录成功");
                        dispose();
                        createMainPage();
                    } else {
                        JOptionPane.showMessageDialog(GUIClient.this, "登录失败，请检查用户名和密码");
                    }
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
        });

        this.add(jPanel);
        this.setVisible(true);
    }

    private void addComponent(JPanel panel, JComponent component, int gridx, int gridy) {
        addComponent(panel, component, gridx, gridy, 1, 1);
    }

    private void addComponent(JPanel panel, JComponent component, int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5); // 添加一些间距
        panel.add(component, constraints);
    }

    private void createMainPage() {
        JFrame mainFrame = new JFrame("Main Page");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        selectedFileLabel = new JLabel("选择的文件: ");
        panel.add(selectedFileLabel, gbc);

        gbc.gridy++;
        chooseButton = new JButton("选择文件");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    selectedFileLabel.setText("选择的文件: " + selectedFile.getName());
                }
            }
        });
        panel.add(chooseButton, gbc);

        gbc.gridy++;
        uploadButtonTCP = new JButton("TCP上传");
        uploadButtonTCP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    status = TCPClient.TCPFileUpload(selectedFile);
                    JOptionPane.showMessageDialog(GUIClient.this, "上传成功");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GUIClient.this, "上传失败");
                }
            }
        });
        panel.add(uploadButtonTCP, gbc);

        gbc.gridy++;
        uploadButtonUDP = new JButton("UDP上传");
        uploadButtonUDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    status = UDPClient.UDPFileUpload(selectedFile);
                    JOptionPane.showMessageDialog(GUIClient.this, "上传成功");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GUIClient.this, "上传失败");
                }
            }
        });
        panel.add(uploadButtonUDP, gbc);
        
        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args){
        new GUIClient();
    }
}
