package com.kj.basiccybersecurityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kj.basiccybersecurityapp.core.PasswordStrength
import com.kj.basiccybersecurityapp.ui.theme.BasicCybersecurityAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCybersecurityAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicCyberAppNav()
                }
            }
        }
    }
}

// ---------- Navigation setup ----------

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object PasswordChecker : Screen("password_checker")
    object BetterPasswordSteps : Screen("better_password_steps")
    object CyberImage : Screen("cyber_image")
    object PhishingTips : Screen("phishing_tips")
    object SafeBrowsing : Screen("safe_browsing")
}

@Composable
fun BasicCyberAppNav() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.PasswordChecker.route) {
            PasswordCheckerScreen(navController)
        }
        composable(Screen.BetterPasswordSteps.route) {
            BetterPasswordStepsScreen(navController)
        }
        composable(Screen.CyberImage.route) {
            CyberImageScreen(navController)
        }
        composable(Screen.PhishingTips.route) {
            PhishingTipsScreen(navController)
        }
        composable(Screen.SafeBrowsing.route) {
            SafeBrowsingTipsScreen(navController)
        }
    }
}

// ---------- Shared "Back to Home" button ----------

@Composable
fun HomeNavButton(navController: NavController) {
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Back to Home")
    }
}

// ---------- Home screen with 5 buttons ----------

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Basic Cybersecurity App",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tap a button to learn or test something.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.PasswordChecker.route) }
        ) {
            Text("Password Strength Checker")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.BetterPasswordSteps.route) }
        ) {
            Text("How to Make a Strong Password")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.CyberImage.route) }
        ) {
            Text("Cybersecurity Poster")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.PhishingTips.route) }
        ) {
            Text("Phishing Red Flags")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.SafeBrowsing.route) }
        ) {
            Text("Safe Browsing Tips")
        }
    }
}

// ---------- Screen 1: Password Strength Checker ----------

@Composable
fun PasswordCheckerScreen(navController: NavController) {
    var passwordText by remember { mutableStateOf("") }

    // Use our PasswordStrength object to get score + tips
    val result = remember(passwordText) { PasswordStrength.evaluate(passwordText) }

    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Password Strength Checker",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text("Enter password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(if (showPassword) "Hide" else "Show")
                }
            }
        )

        StrengthBar(score = result.score)

        Text(text = "${result.label} • Score ${result.score}/100")
        Text(
            text = String.format(
                "Estimated randomness: %.1f bits",
                result.entropyBits
            )
        )

        if (result.feedback.isNotEmpty()) {
            Text(
                "Tips to improve:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            result.feedback.take(5).forEach { tip ->
                Text("• $tip")
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = "All checks run on this device. Your password is not saved.",
            style = MaterialTheme.typography.bodySmall
        )

        HomeNavButton(navController)
    }
}

@Composable
fun StrengthBar(score: Int) {
    val pct = (score.coerceIn(0, 100)) / 100f

    Box(
        Modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.LightGray.copy(alpha = 0.4f))
    ) {
        Box(
            Modifier
                .fillMaxWidth(pct)
                .fillMaxHeight()
                .background(
                    when (score) {
                        in 0..19 -> Color(0xFFE53935) // red
                        in 20..39 -> Color(0xFFFDD835) // yellow
                        in 40..59 -> Color(0xFFFFB300) // amber
                        in 60..79 -> Color(0xFF43A047) // green
                        else -> Color(0xFF1E88E5)   // blue
                    }
                )
        )
    }
}

// ---------- Screen 2: Steps to make better passwords ----------

@Composable
fun BetterPasswordStepsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "How to Make a Strong Password",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text("Think of this like rules for a super strong lock:")

        val tips = listOf(
            "Use at least 12–16 characters.",
            "Mix lowercase, UPPERCASE, numbers, and symbols.",
            "Do not use your name, birthday, school, or team.",
            "Avoid easy patterns like 123456, qwerty, or password.",
            "Use different passwords for different accounts.",
            "Use a password manager to remember long passwords for you."
        )

        tips.forEach { tip ->
            Text("• $tip")
        }

        Spacer(Modifier.weight(1f))
        HomeNavButton(navController)
    }
}

// ---------- Screen 3: Cybersecurity image ----------

@Composable
fun CyberImageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cybersecurity Poster",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.cybersecurity),
            contentDescription = "Cybersecurity image",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f))
        HomeNavButton(navController)
    }
}

// ---------- Screen 4: Phishing red flags ----------

@Composable
fun PhishingTipsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Phishing Red Flags",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        val tips = listOf(
            "The message tries to scare you (“Your account will be closed today!”).",
            "The sender email is slightly wrong (like amaz0n-support instead of amazon).",
            "They ask for passwords, codes, or credit card numbers.",
            "Links go to strange websites when you long-press or hover.",
            "The spelling and grammar look unprofessional.",
            "They tell you to act “right now” and not think about it."
        )

        tips.forEach { tip ->
            Text("• $tip")
        }

        Spacer(Modifier.weight(1f))
        HomeNavButton(navController)
    }
}

// ---------- Screen 5: Safe browsing tips ----------

@Composable
fun SafeBrowsingTipsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Safe Browsing Tips",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        val tips = listOf(
            "Only download apps from official stores (Google Play, etc.).",
            "Check that websites start with https:// especially when logging in.",
            "Avoid clicking random pop-ups saying you won a prize.",
            "Keep your phone and apps updated.",
            "Do not use the same password everywhere.",
            "Log out on shared or school computers."
        )

        tips.forEach { tip ->
            Text("• $tip")
        }

        Spacer(Modifier.weight(1f))
        HomeNavButton(navController)
    }
}
