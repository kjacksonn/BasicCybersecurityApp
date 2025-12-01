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

````markdown
## 🔍 How the Password Strength Checker Works 

The Password Strength Checker looks at your password in simple steps.  
Each step adds or subtracts points to decide the final score (0–100).  
Here’s how it works, with real code examples from `PasswordStrength.kt`.

---

## 1️⃣ Step 1 — Check what the password contains

The app checks if the password uses:
- lowercase letters (a–z)
- uppercase letters (A–Z)
- numbers (0–9)
- symbols (! @ # ?)
- enough length

```kotlin
val length = pw.length
val hasLower = pw.any { it.isLowerCase() }
val hasUpper = pw.any { it.isUpperCase() }
val hasDigit = pw.any { it.isDigit() }
val hasSymbol = pw.any { !it.isLetterOrDigit() }
````

More variety = more points.

---

## 2️⃣ Step 2 — Give points for good habits

The app rewards stronger password choices:

```kotlin
var complexityBonus = 0.0

if (hasLower && hasUpper) complexityBonus += 5
if (hasDigit) complexityBonus += 5
if (hasSymbol) complexityBonus += 7
if (length >= 12) complexityBonus += 3
```

Longer, mixed-character passwords get higher scores.

---

## 3️⃣ Step 3 — Take away points for weak patterns

The app subtracts points for predictable or simple patterns:

```kotlin
if (hasLongRepeat(pw)) complexityBonus -= 8
if (hasSimpleSequence(pw)) complexityBonus -= 8
if (looksLikeDate(pw)) complexityBonus -= 5
```

This catches:

* repeated letters (aaa, 111)
* easy sequences (abc, 123)
* passwords that look like dates (12252000)

---

## 4️⃣ Step 4 — Detect super common passwords

The app checks if the password contains extremely common patterns:

```kotlin
val lowerPw = pw.lowercase()
val inCommonBase = commonBases.any { lowerPw.contains(it) }
val inKeyboardRun = keyboardRuns.any { lowerPw.contains(it) }

if (inCommonBase || inKeyboardRun) {
    score = minOf(score, 20)
}
```

Examples:

* “password”
* “qwerty”
* “abc123”

If it includes these, the score is capped low.

---

## 5️⃣ Step 5 — Create the final score and label

The final score is built from:

* good points
* minus bad points
* locked between 0 (weakest) and 100 (strongest)

```kotlin
var score = (randomnessPointsFromMath + complexityBonus)
    .toInt()
    .coerceIn(0, 100)
```

The score is then turned into a simple label:

```kotlin
val label = when (score) {
    in 0..19 -> "Very Weak"
    in 20..39 -> "Weak"
    in 40..59 -> "Fair"
    in 60..79 -> "Strong"
    else      -> "Excellent"
}
```

---

## 📝 Summary

* Good password habits **add** points
* Weak patterns **subtract** points
* Common passwords get forced to a low score
* The final score becomes a clear color bar and a label
* The checker gives tips on how to improve
* Everything runs **locally on the device** (nothing saved)


---

## 🧰 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Local password analysis engine**
- **Android Studio (Electric Eel/Flamingo or later)**


