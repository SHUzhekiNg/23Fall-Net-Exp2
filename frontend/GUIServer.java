package frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.ServerBooter;


public class GUIServer extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public GUIServer() throws HeadlessException{
        this.setTitle("GUIServer");
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(185, 213, 165));
        jPanel.setLayout(new GridBagLayout());

        // 创建用户名标签和文本框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);
        addComponent(jPanel, usernameLabel, 0, 0);
        addComponent(jPanel, usernameField, 1, 0);

        // 创建密码标签和密码框
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);
        addComponent(jPanel, passwordLabel, 0, 1);
        addComponent(jPanel, passwordField, 1, 1);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        addComponent(jPanel, loginButton, 0, 2, 1, 1);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // 这里可以添加验证逻辑，比如检查用户名和密码是否正确
                // 这里只是一个示例，不进行实际验证
                if (username.equals("admin") && password.equals("123")) {
                    dispose();
                    createMainPage();
                } else {
                    JOptionPane.showMessageDialog(GUIServer.this, "登录失败，请检查用户名和密码");
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

    private static Object[][] parseData(List<String> lines) {
        Object[][] data = new Object[lines.size()][2];
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(" ");
            if (parts.length == 2) {
                data[i][0] = parts[0];
                data[i][1] = parts[1];
            }
        }
        return data;
    }

    private static List<String> readFileData(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private String[] columnNames = {"username", "password"};
    private JFrame mainFrame = new JFrame("用户登录信息");
    private void updateTableData() {
        List<String> lines = readFileData("login_info.txt");
        Object[][] data = parseData(lines);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        mainFrame.getContentPane().removeAll(); // 清除旧的内容
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.revalidate(); // 重新布局和绘制
    }

    private void createMainPage() {
        // 创建新的主页面
        
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        // 创建一个定时器，每隔一段时间触发更新操作
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableData();
            }
        });
        timer.start(); // 启动定时
        mainFrame.setVisible(true);

    }
    public static void main(String[] args) {
        ServerBooter.main(null);
        new GUIServer();
    }
}
