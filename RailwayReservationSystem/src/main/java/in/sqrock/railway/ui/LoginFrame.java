package in.sqrock.railway.ui;

import in.sqrock.railway.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username=new JTextField(18); private final JPasswordField password=new JPasswordField(18);
    private final AuthService auth=new AuthService();
    public LoginFrame(){
        setTitle("Railway Reservation System - Login");setSize(430,300);setLocationRelativeTo(null);setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel=new JPanel(new GridBagLayout());panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(8,8,8,8);g.fill=GridBagConstraints.HORIZONTAL;
        JLabel title=new JLabel("RAILWAY RESERVATION",SwingConstants.CENTER);title.setFont(new Font("Arial",Font.BOLD,20));g.gridx=0;g.gridy=0;g.gridwidth=2;panel.add(title,g);g.gridwidth=1;
        g.gridy++;g.gridx=0;panel.add(new JLabel("Username"),g);g.gridx=1;panel.add(username,g);g.gridy++;g.gridx=0;panel.add(new JLabel("Password"),g);g.gridx=1;panel.add(password,g);
        JButton login=new JButton("Login"),register=new JButton("Create account");g.gridy++;g.gridx=0;panel.add(login,g);g.gridx=1;panel.add(register,g);add(panel);
        login.addActionListener(e->doLogin());register.addActionListener(e->doRegister());password.addActionListener(e->doLogin());
    }
    private void doLogin(){try{int id=auth.login(username.getText().trim(),new String(password.getPassword()));if(id<0){JOptionPane.showMessageDialog(this,"Invalid username or password.");return;}new DashboardFrame(id,username.getText().trim()).setVisible(true);dispose();}catch(Exception ex){showError(ex);}}
    private void doRegister(){String u=username.getText().trim(),p=new String(password.getPassword());if(u.isEmpty()||p.length()<6){JOptionPane.showMessageDialog(this,"Enter a username and a password of at least 6 characters.");return;}String name=JOptionPane.showInputDialog(this,"Enter your full name:");if(name==null||name.trim().isEmpty())return;try{auth.register(u,p,name.trim());JOptionPane.showMessageDialog(this,"Account created. You can now log in.");}catch(Exception ex){showError(ex);}}
    private void showError(Exception e){JOptionPane.showMessageDialog(this,e.getMessage()==null?"Something went wrong.":e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
}
