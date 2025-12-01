# 🔐 Basic Cybersecurity App  
A simple, beginner-friendly Android app built with **Kotlin** and **Jetpack Compose** that teaches essential cybersecurity concepts.  
The app includes a **password strength checker**, cybersecurity education pages, safe browsing tips, and phishing awareness — all in one clean, modern UI.

---

## 📱 Features

### 🏠 Home Screen
A clean navigation hub with five learning modules:

1. **Password Strength Checker**
2. **How to Make a Strong Password**
3. **Cybersecurity Poster (Image)**
4. **Phishing Red Flags**
5. **Safe Browsing Tips**

---

## 🔑 Password Strength Checker
✔ Real-time scoring (0–100)  
✔ Visual strength bar  
✔ Entropy-based randomness estimate  
✔ Feedback on how to improve the password  
✔ All processing done **on-device** (nothing is stored)

---

## 🧠 Educational Pages  
The app includes non-technical explanations suitable for students, beginners, and anyone learning cybersecurity:

### 📘 How to Make a Strong Password
- Minimum length guidance  
- Mix of characters  
- Avoiding personal info  
- Password manager tips  

### 🖼 Cybersecurity Poster  
Displays a visual image of typing in 'Cybersecurity' into google. 

### 🚩 Phishing Red Flags
- Identifying fake emails  
- Suspicious links  
- Urgency tactics  
- Grammar and sender tricks  

### 🛡 Safe Browsing Tips
- HTTPS importance  
- Avoiding pop-ups  
- Updating devices  
- Protecting accounts on shared computers  

---
# 🔰 Screenshots

## 🏠 Home Screen
![Home Screen](screenshots/home.png)

## 🔐 Password Strength Checker
![Password Checker](screenshots/password_checker.png)

## 🧠 How to Make a Strong Password
![Strong Password Tips](screenshots/strong_password_tips.png)

## 🖼 Cybersecurity Poster
![Cybersecurity Poster](screenshots/cybersecurity_poster.png)

## 🚩 Phishing Red Flags
![Phishing Tips](screenshots/phishing_tips.png)

## 🛡 Safe Browsing Tips
![Safe Browsing](screenshots/safe_browsing.png)

---

### 🔍 How the Password Strength Checker Works

The password checker scores your password by looking at a few key things:

**1. What characters you used**
- lowercase letters  
- UPPERCASE letters  
- numbers  
- symbols  

More variety = more points.

**2. How long it is**
Longer passwords are harder to guess.

**3. If it uses easy patterns**
The app subtracts points for:
- “123”, “abc”, “qwerty”
- repeated letters (“aaa”, “111”)
- passwords that look like dates (like 12252000)

**4. If it contains common passwords**
Anything like “password”, “welcome”, or “iloveyou” lowers the score a lot.

**5. Final Score**
All the good and bad points are added together and turned into a score:
- 0–19 = Very Weak  
- 20–39 = Weak  
- 40–59 = Fair  
- 60–79 = Strong  
- 80–100 = Excellent  

It also gives simple tips so the user knows exactly how to improve their password.

All checking is done **locally** on your device and nothing is saved.


---

## 🧰 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Local password analysis engine**
- **Android Studio (Electric Eel/Flamingo or later)**

---

## 📂 Project Structure

