# Tiffinwala - An Online Tiffin Service  

## System Requirement Specification  

### **Team**  
- **Direct Customer**  
- **Indirect Customer**  
- **Architect**  
- **Business Analyst**  
- **Quality Assurance Team**  
- **System Analyst**  

### **Objective (Purpose)**  
The online tiffin service **Tiffinwala** is designed to provide a comprehensive platform for vendors, customers, and internal users (staff) via the internet. It empowers vendors, especially housewives seeking side income, to offer their tiffin services online. Consumers can browse available tiffin services and place orders without physically visiting any service center.  

### **Scope**  
- **Daily, weekly, or monthly orders**: Customers can schedule orders as per their needs.  
- **Vendor control**: Vendors can manage their menus and handle orders based on availability.  
- **Live insights**: A customized dashboard provides stakeholders with business statistics and trends.  

---

## **Functional Requirements**  

### **Anonymous User**  
- View tiffin plans and available menus.  

### **Customer**  
- Register and log in to the website.  
- View tiffin plans and available menus.  
- Place or cancel orders (within 5 minutes).  
- Make payments for placed orders.  
- Review order history based on time range (daily, weekly, monthly, yearly).  
- Provide feedback for placed orders.  

### **Vendor**  
- Register and log in to the website.  
- Add, remove, or update tiffin details and plans.  
- Confirm or reject orders based on availability.  
- View sales data by time range (daily, weekly, monthly, yearly).  
- Access customer reviews and feedback.  

### **Internal Staff (Admin)**  
- Log in to the website.  
- Approve vendor registrations and menu changes.  
- Monitor all vendor and customer activities.  
- Oversee all payment transactions.  
- Resolve disputes between vendors and customers.  
- Process vendor payments based on sales performance.  

-------------------------------------------------

## **Non-functional Requirements**  

### **Security**  
- Only registered users can place orders.  
- Authentication and role-based security for all stakeholders.  
- SSL encryption for secure transactions.  
- Automatic logout for inactive users.  
- Encrypted communication and secure channels between servers.  
- Firewalls to prevent phishing and external attacks.  

### **Reliability**  
- Regular data backups and quick recovery mechanisms.  
- Load balancing for peak traffic management.  

### **Maintainability**  
- Commercial database software ensures data persistence.  
- Tools for system monitoring and management.  
- Dedicated environments for production, testing, and development.  

### **Portability**  
- Cross-platform UI (HTML, CSS, JS) accessible on any browser.  
- Deployable on a variety of infrastructures, including cloud platforms like AWS, Azure, or GCP.  

### **Availability**  
- 99.999% uptime, ensuring 24/7 availability.  

### **Accessibility**  
- Customers can place orders after authentication.  
- Vendors manage menus and handle orders.  
- Staff access dashboards for business insights.  

### **Durability**  
- Retention of cart data for 15 minutes during internet issues.  
- Backup and recovery mechanisms for critical data.  

### **Efficiency**  
- High responsiveness during peak seasons.  
- Transactions are processed with isolation to prevent issues.  

### **Modularity**  
- Loosely coupled, reusable modules for:  
  - Login/registration  
  - Menu management  
  - Order cart and processing  
  - Payment handling  
  - Membership and role management  

### **Scalability**  
- Consistent performance under varying loads.  

### **Safety**  
- Secure with firewalls and regular updates of anti-virus software.  
- Incremental data backups ensure minimal data loss.  
- Role-based security for restricted access.  

---

## **Features**  

1. **Customer Portal**: Order tiffins seamlessly.  
2. **Vendor Dashboard**: Manage tiffin services and track sales.  
3. **Admin Panel**: Approve registrations and oversee operations.  
4. **Real-time Insights**: Business statistics through dashboards.  
5. **Secure Transactions**: SSL-enabled payments and encrypted data.  

---

### **License**  
This project is licensed under the MIT License – see the LICENSE file for details.  

### **Contributions**  
Feel free to contribute to Tiffinwala by submitting pull requests, raising issues, or suggesting features.  

---

