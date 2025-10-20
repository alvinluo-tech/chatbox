#!/usr/bin/env python3
"""
测试邮件发送功能
确保邮箱能够正常连接并发送邮件
"""

import argparse
from email_utils import EmailSender

def test_email_connection():
    """测试邮件服务器连接"""
    try:
        # 初始化邮件发送器会自动检查配置
        EmailSender()
        print("✅ 邮件服务器配置正确")
        return True
    except Exception as e:
        print(f"❌ 邮件服务器配置错误: {str(e)}")
        return False

def test_send_email(recipient_email):
    """
    测试发送邮件
    
    参数:
        recipient_email: 接收测试邮件的邮箱地址
    """
    try:
        sender = EmailSender()
        
        # 发送纯文本邮件
        subject = "Chatbox 邮件功能测试"
        body = """
        这是一封测试邮件，用于验证 Chatbox 后端邮件发送功能是否正常工作。
        
        如果您收到这封邮件，说明邮件发送功能工作正常！
        
        祝好,
        Chatbox 团队
        """
        
        success = sender.send_email(recipient_email, subject, body, is_html=False)
        
        if success:
            print(f"✅ 测试邮件已成功发送至 {recipient_email}")
            
            # 发送HTML邮件
            html_subject = "Chatbox HTML邮件功能测试"
            html_body = """
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; }
                    .header { color: #4285f4; font-size: 24px; }
                    .content { margin: 20px 0; }
                    .footer { color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="header">Chatbox HTML邮件测试</div>
                <div class="content">
                    <p>这是一封<strong>HTML格式</strong>的测试邮件，用于验证 Chatbox 后端邮件发送功能是否正常工作。</p>
                    <p>如果您收到这封邮件并且格式正确，说明HTML邮件发送功能工作正常！</p>
                </div>
                <div class="footer">
                    祝好,<br>
                    Chatbox 团队
                </div>
            </body>
            </html>
            """
            
            html_success = sender.send_email(recipient_email, html_subject, html_body, is_html=True)
            
            if html_success:
                print(f"✅ HTML测试邮件已成功发送至 {recipient_email}")
                return True
            else:
                print("❌ HTML测试邮件发送失败")
                return False
        else:
            print("❌ 测试邮件发送失败")
            return False
            
    except Exception as e:
        print(f"❌ 邮件发送测试失败: {str(e)}")
        return False

def main():
    parser = argparse.ArgumentParser(description="测试邮件发送功能")
    parser.add_argument("recipient", help="接收测试邮件的邮箱地址")
    args = parser.parse_args()
    
    print("=== Chatbox 邮件功能测试 ===")
    
    # 测试邮件服务器连接
    if test_email_connection():
        # 测试发送邮件
        test_send_email(args.recipient)
    
    print("=== 测试完成 ===")

if __name__ == "__main__":
    main()