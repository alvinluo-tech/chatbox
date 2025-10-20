import os
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from dotenv import load_dotenv

# 加载环境变量
load_dotenv()

class EmailSender:
    def __init__(self):
        """初始化邮件发送器，从环境变量中获取配置"""
        self.smtp_server = os.getenv("SMTP_SERVER")
        self.smtp_port = int(os.getenv("SMTP_PORT", 587))
        self.email_user = os.getenv("EMAIL_USER")
        self.email_password = os.getenv("EMAIL_PASSWORD")
        
        if not all([self.smtp_server, self.smtp_port, self.email_user, self.email_password]):
            raise ValueError("邮件配置不完整，请检查环境变量")
    
    def send_email(self, to_email, subject, body, is_html=False):
        """
        发送邮件
        
        参数:
            to_email (str): 收件人邮箱
            subject (str): 邮件主题
            body (str): 邮件内容
            is_html (bool): 是否为HTML格式内容
        
        返回:
            bool: 发送成功返回True，否则返回False
        """
        try:
            # 创建邮件
            msg = MIMEMultipart()
            msg['From'] = self.email_user
            msg['To'] = to_email
            msg['Subject'] = subject
            
            # 添加邮件内容
            content_type = 'html' if is_html else 'plain'
            msg.attach(MIMEText(body, content_type, 'utf-8'))
            
            # 连接SMTP服务器并发送
            with smtplib.SMTP(self.smtp_server, self.smtp_port) as server:
                server.ehlo()
                server.starttls()  # 启用TLS加密
                server.ehlo()
                server.login(self.email_user, self.email_password)
                server.send_message(msg)
            
            print(f"邮件已成功发送至 {to_email}")
            return True
            
        except Exception as e:
            print(f"发送邮件失败: {str(e)}")
            return False